package com.munsun.deal.services;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.tomakehurst.wiremock.WireMockServer;
import com.maciejwalkowiak.wiremock.spring.ConfigureWireMock;
import com.maciejwalkowiak.wiremock.spring.EnableWireMock;
import com.maciejwalkowiak.wiremock.spring.InjectWireMock;
import com.munsun.deal.dto.*;
import com.munsun.deal.exceptions.PrescoringException;
import com.munsun.deal.exceptions.StatementNotFoundException;
import com.munsun.deal.kafka.producers.DealProducer;
import com.munsun.deal.models.Credit;
import com.munsun.deal.models.enums.ApplicationStatus;
import com.munsun.deal.models.enums.CreditStatus;
import com.munsun.deal.repositories.ClientRepository;
import com.munsun.deal.repositories.CreditRepository;
import com.munsun.deal.utils.PostgresContainer;
import com.munsun.deal.utils.TestUtils;
import com.munsun.deal.models.Statement;
import com.munsun.deal.repositories.StatementRepository;
import feign.FeignException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.transaction.annotation.Transactional;

import java.lang.reflect.Type;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static com.github.tomakehurst.wiremock.client.WireMock.aResponse;
import static com.github.tomakehurst.wiremock.client.WireMock.post;
import static com.munsun.deal.utils.TestUtils.CALC_CREDIT_ENDPOINT_CALCULATOR;
import static com.munsun.deal.utils.TestUtils.LOAN_OFFERS_ENDPOINT_CALCULATOR;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertTrue;

@Transactional
@SpringBootTest
@EnableWireMock(
        @ConfigureWireMock(name="calculator-client", property = "${client.calculator.url}")
)
public class DealServiceIntegrationsTests extends PostgresContainer {
    @InjectWireMock("calculator-client")
    private WireMockServer calculatorServer;
    @Autowired
    private ObjectMapper mapper;
    @Autowired
    private StatementRepository statementRepository;
    @MockBean
    private DealProducer dealProducer;
    @Autowired
    private DealService service;

    @DisplayName("Test get loan offers")
    @Test
    public void givenLoanStatementRequestDto_whenGetLoanOffers_thenReturnListLoanOffersSize4() throws JsonProcessingException {
        LoanStatementRequestDto loanStatement = TestUtils.getLoanStatementRequestDto();
        calculatorServer.stubFor(post(LOAN_OFFERS_ENDPOINT_CALCULATOR+"?typePayments=ANNUITY")
                .willReturn(aResponse()
                        .withHeader("Content-Type", "application/json")
                        .withBody(mapper.writeValueAsString(TestUtils.getAnnuitentPaymentListLoanOffersDtoAmount10_000Term12()))));

        List<LoanOfferDto> offers = service.getLoanOffers(TypePayments.ANNUITY, loanStatement);
        Optional<Statement> savedStatement = statementRepository.findById(offers.get(0).getStatementId());

        assertThat(savedStatement)
                .isPresent().get()
                .extracting(Statement::getStatus)
                .isEqualTo(ApplicationStatus.PREAPPROVAL);
        assertThat(savedStatement.get().getClient())
                .isNotNull();
        assertThat(offers)
                .isNotNull().isNotEmpty()
                .allMatch(offer -> offer.getStatementId() != null);
    }

    @DisplayName("Test get loan offers, prescoring error")
    @Test
    public void givenInvalidLoanStatementRequestDto_whenGetLoanOffers_thenReturnPrescoringException() throws JsonProcessingException {
        LoanStatementRequestDto loanStatement = TestUtils.getLoanStatementRequestDtoInvalidAmount();
        ErrorMessageDto errorMessage = TestUtils.getErrorMessageInvalidAmount();
        calculatorServer.stubFor(post(LOAN_OFFERS_ENDPOINT_CALCULATOR+"?typePayments=ANNUITY")
                .willReturn(aResponse()
                        .withStatus(HttpStatus.BAD_REQUEST.value())
                        .withHeader("Content-Type", "application/json")
                        .withBody(mapper.writeValueAsString(errorMessage))));

        assertThatThrownBy(() -> service.getLoanOffers(TypePayments.ANNUITY, loanStatement))
                .isInstanceOf(PrescoringException.class);
    }

    @DisplayName("Test select not exists loan offer")
    @Test
    public void givenNotExistsLoanOffer_whenSelectLoanOffer_thenThrownStatementNotFoundException() {
        LoanOfferDto loanOffer = TestUtils.getAnnuitentPaymentLoanOfferDtoAmount10_000Term12();

        assertThatThrownBy(() -> service.selectLoanOffer(TypePayments.ANNUITY, loanOffer))
                .isInstanceOf(StatementNotFoundException.class)
                .hasMessageContaining(loanOffer.getStatementId().toString());
    }

    @DisplayName("Test get credit not exists statementId")
    @Test
    public void givenNotExistsStatementId_whenCalculateCredit_thenThrowStatementNotFoundException() {
        UUID uuid = UUID.randomUUID();
        assertThatThrownBy(
                () -> service.calculateCredit(uuid.toString(), TestUtils.getFinishRegistrationRequestDto()))
                .isInstanceOf(StatementNotFoundException.class)
                .hasMessageContaining(uuid.toString());
    }
}