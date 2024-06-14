package com.munsun.deal.services;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.tomakehurst.wiremock.WireMockServer;
import com.maciejwalkowiak.wiremock.spring.ConfigureWireMock;
import com.maciejwalkowiak.wiremock.spring.EnableWireMock;
import com.maciejwalkowiak.wiremock.spring.InjectWireMock;
import com.munsun.deal.dto.response.ErrorMessageDto;
import com.munsun.deal.exceptions.StatementNotFoundException;
import com.munsun.deal.models.enums.ApplicationStatus;
import com.munsun.deal.models.json.StatusHistory;
import com.munsun.deal.repositories.ClientRepository;
import com.munsun.deal.repositories.CreditRepository;
import com.munsun.deal.utils.PostgresContainer;
import com.munsun.deal.utils.TestUtils;
import com.munsun.deal.dto.request.LoanStatementRequestDto;
import com.munsun.deal.dto.response.LoanOfferDto;
import com.munsun.deal.models.Statement;
import com.munsun.deal.repositories.StatementRepository;
import feign.FeignException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static com.github.tomakehurst.wiremock.client.WireMock.aResponse;
import static com.github.tomakehurst.wiremock.client.WireMock.post;
import static com.munsun.deal.utils.TestUtils.LOAN_OFFERS_ENDPOINT;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertTrue;

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
    @Autowired
    private ClientRepository clientRepository;
    @Autowired
    private CreditRepository creditRepository;
    @Autowired
    private DealService service;

    @DisplayName("Test get loan offers")
    @Test
    public void givenLoanStatementRequestDto_whenGetLoanOffers_thenReturnListLoanOffersSize4() throws JsonProcessingException {
        LoanStatementRequestDto loanStatement = TestUtils.getLoanStatementRequestDto();
        calculatorServer.stubFor(post(LOAN_OFFERS_ENDPOINT)
                .willReturn(aResponse()
                        .withHeader("Content-Type", "application/json")
                        .withBody(mapper.writeValueAsString(TestUtils.getAnnuitentPaymentListLoanOffersDtoAmount10_000Term12()))));

        List<LoanOfferDto> offers = service.getLoanOffers(loanStatement);
        Optional<Statement> savedStatement = statementRepository.findById(offers.get(0).statementId());

        assertThat(savedStatement)
                .isPresent().get()
                .extracting(Statement::getStatus)
                .isEqualTo(ApplicationStatus.PREAPPROVAL);
        assertThat(savedStatement.get().getClient())
                .isNotNull();
        assertThat(offers)
                .isNotNull().isNotEmpty()
                .allMatch(offer -> offer.statementId() != null);
    }

    @DisplayName("Test get loan offers, prescoring error")
    @Test
    public void givenInvalidLoanStatementRequestDto_whenGetLoanOffers_thenReturnPrescoringException() throws JsonProcessingException {
        LoanStatementRequestDto loanStatement = TestUtils.getLoanStatementRequestDtoInvalidAmount();
        ErrorMessageDto errorMessage = TestUtils.getErrorMessageInvalidAmount();
        calculatorServer.stubFor(post(LOAN_OFFERS_ENDPOINT)
                .willReturn(aResponse()
                        .withStatus(HttpStatus.BAD_REQUEST.value())
                        .withHeader("Content-Type", "application/json")
                        .withBody(mapper.writeValueAsString(errorMessage))));

        try {
            service.getLoanOffers(loanStatement);
        } catch (FeignException e) {
            assertThat(e.status())
                    .isEqualTo(HttpStatus.BAD_REQUEST.value());
            assertThat(e.contentUTF8())
                    .contains("prescoring");
        }
    }

    @DisplayName("Test select loan offer")
    @Test
    public void givenStatementPersistent_whenSelectLoanOffer_thenSaveAppliedOfferAndChangeStatusStatement() {
        UUID uuidStatement = statementRepository.save(new Statement()).getStatementId();
        LoanOfferDto loanOffer = TestUtils.getLoanOffer(uuidStatement);

        service.selectLoanOffer(loanOffer);

        Optional<Statement> savedStatement = statementRepository.findById(loanOffer.statementId());
        assertThat(savedStatement)
                .isPresent().get()
                .extracting(Statement::getAppliedOffer, Statement::getStatus)
                .containsExactly(loanOffer, ApplicationStatus.APPROVED);
        assertThat(savedStatement)
                .get()
                .extracting(Statement::getStatusHistory)
                .isNotNull();
    }

    @DisplayName("Test select not exists loan offer")
    @Test
    public void givenNotExistsLoanOffer_whenSelectLoanOffer_thenThrownStatementNotFoundException() {
        LoanOfferDto loanOffer = TestUtils.getAnnuitentPaymentLoanOfferDtoAmount10_000Term12();

        assertThatThrownBy(() -> service.selectLoanOffer(loanOffer))
                .isInstanceOf(StatementNotFoundException.class)
                .hasMessageContaining(loanOffer.statementId().toString());
    }
}