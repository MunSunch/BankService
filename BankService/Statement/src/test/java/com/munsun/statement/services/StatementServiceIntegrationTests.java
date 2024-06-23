package com.munsun.statement.services;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.github.tomakehurst.wiremock.WireMockServer;
import com.maciejwalkowiak.wiremock.spring.ConfigureWireMock;
import com.maciejwalkowiak.wiremock.spring.EnableWireMock;
import com.maciejwalkowiak.wiremock.spring.InjectWireMock;
import com.munsun.statement.dto.LoanOfferDto;
import com.munsun.statement.dto.LoanStatementRequestDto;
import com.munsun.statement.exceptions.StatementNotFoundException;
import com.munsun.statement.utils.TestUtils;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;

import java.util.List;
import java.util.Objects;

import static com.github.tomakehurst.wiremock.client.WireMock.aResponse;
import static com.github.tomakehurst.wiremock.client.WireMock.post;
import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

@SpringBootTest
@EnableWireMock(
        @ConfigureWireMock(name="deal-client", property = "${client.deal.url}")
)
public class StatementServiceIntegrationTests {
    @InjectWireMock("deal-client")
    private WireMockServer server;
    @Autowired
    private StatementService service;

    @DisplayName("Test get loan offers")
    @Test
    public void givenConfigureServerReturnListLoanOffersStatus200_whenGetLoanOffers_thenReturnListLoanOfferSize4() throws JsonProcessingException {
        LoanStatementRequestDto loanStatement = TestUtils.getLoanStatementRequestDto();
        List<LoanOfferDto> expectedLoanOfferDto = TestUtils.getAnnuitentPaymentListLoanOffersDtoAmount10_000Term12();
        server.stubFor(post(TestUtils.LOAN_OFFERS_ENDPOINT_DEAL)
                .willReturn(aResponse()
                        .withStatus(HttpStatus.OK.value())
                        .withHeader("Content-Type", "application/json")
                        .withBody(TestUtils.getListLoanOffersJSON())));

        List<LoanOfferDto> actualLoanOfferDto = service.getLoanOffers(loanStatement);

        assertThat(actualLoanOfferDto)
                .isNotEmpty().isNotNull()
                .hasSize(expectedLoanOfferDto.size())
                .allMatch(Objects::nonNull);
    }

    @DisplayName("Test select not exists loan offer")
    @Test
    public void givenConfigureServerReturnStatementNotFoundStatus404_whenSelectLoanOffer_thenThrownStatementNotFoundException() throws JsonProcessingException {
        server.stubFor(post(TestUtils.SELECT_LOAN_OFFER_ENDPOINT_DEAL)
                .willReturn(aResponse()
                        .withStatus(HttpStatus.NOT_FOUND.value())
                        .withHeader("Content-Type", "application/json")
                        .withBody(TestUtils.getErrorMessageWithStatementNotFoundJSON())));

        assertThatThrownBy(() ->
        service.selectLoanOffer(TestUtils.getAnnuitentPaymentLoanOfferDtoAmount10_000Term12()))
                .isInstanceOf(StatementNotFoundException.class);
    }

    @DisplayName("Test select loan offer")
    @Test
    public void givenConfigureServerReturnEmptyBodyStatus200_whenSelectLoanOffer_thenExceptionsNotThrown() {
        server.stubFor(post(TestUtils.SELECT_LOAN_OFFER_ENDPOINT_DEAL)
                .willReturn(aResponse()
                        .withStatus(HttpStatus.OK.value())));

        assertDoesNotThrow(()->
        service.selectLoanOffer(TestUtils.getAnnuitentPaymentLoanOfferDtoAmount10_000Term12()));
    }
}
