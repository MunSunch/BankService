package com.munsun.deal.services.clients;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.tomakehurst.wiremock.WireMockServer;
import com.maciejwalkowiak.wiremock.spring.ConfigureWireMock;
import com.maciejwalkowiak.wiremock.spring.EnableWireMock;
import com.maciejwalkowiak.wiremock.spring.InjectWireMock;
import com.munsun.deal.dto.response.ErrorMessageDto;
import com.munsun.deal.utils.TestUtils;
import com.munsun.deal.dto.request.LoanStatementRequestDto;
import com.munsun.deal.dto.request.ScoringDataDto;
import com.munsun.deal.dto.response.CreditDto;
import com.munsun.deal.dto.response.LoanOfferDto;
import com.munsun.deal.services.impl.clients.CalculatorFeignClient;
import feign.FeignException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;

import java.util.List;

import static com.github.tomakehurst.wiremock.client.WireMock.aResponse;
import static com.github.tomakehurst.wiremock.client.WireMock.post;
import static com.munsun.deal.utils.TestUtils.CALC_CREDIT_ENDPOINT_CALCULATOR;
import static com.munsun.deal.utils.TestUtils.LOAN_OFFERS_ENDPOINT_CALCULATOR;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@SpringBootTest
@EnableWireMock(
        @ConfigureWireMock(name="calculator-client", property = "${client.calculator.url}")
)
public class CalculatorFeignClientUnitTests {
    @InjectWireMock("calculator-client")
    private WireMockServer server;
    @Autowired
    private ObjectMapper mapper;
    @Autowired
    private CalculatorFeignClient client;

    @DisplayName("Test send request to get a list of loan offers")
    @Test
    public void givenRequestWithLoanStatementRequest_whenSendRequestToServer_thenReturnResponseListLoanOfferStatus200() throws JsonProcessingException {
        LoanStatementRequestDto loanRequest = TestUtils.getLoanStatementRequestDto();
        List<LoanOfferDto> expectedLoanOffers = TestUtils.getAnnuitentPaymentListLoanOffersDtoAmount10_000Term12();
        server.stubFor(post(LOAN_OFFERS_ENDPOINT_CALCULATOR)
                .willReturn(aResponse()
                    .withHeader("Content-Type", "application/json")
                    .withBody(mapper.writeValueAsString(expectedLoanOffers))));

        List<LoanOfferDto> loanOffers = client.getLoanOffers(loanRequest);

        assertThat(loanOffers)
                .isNotNull().isNotEmpty()
                .hasSize(expectedLoanOffers.size());
    }

    @DisplayName("Test send request to get credit")
    @Test
    public void givenRequestWithScoringDataDto_whenSendRequestToServer_thenReturnResponseWithCreditDtoStatus200() throws JsonProcessingException {
        ScoringDataDto scoringData = TestUtils.getScoringDataDto();
        CreditDto expectedCreditDto = TestUtils.getCreditDto();
        server.stubFor(post(CALC_CREDIT_ENDPOINT_CALCULATOR)
                .willReturn(aResponse()
                        .withHeader("Content-Type", "application/json")
                        .withBody(mapper.writeValueAsString(expectedCreditDto))));

        CreditDto credit = client.getCredit(scoringData);

        assertThat(credit)
                .isNotNull()
                .usingRecursiveComparison()
                .isEqualTo(expectedCreditDto);
    }

    @DisplayName("Test send request with loanStatementDto, prescoring error")
    @Test
    public void givenRequestWithLoanRequestInvalid_whenSendRequest_thenReturnResponseWithErrorMessageStatus400() throws JsonProcessingException {
        LoanStatementRequestDto loanRequest = TestUtils.getLoanStatementRequestDtoInvalidAmount();
        ErrorMessageDto errorMessage = TestUtils.getErrorMessageInvalidAmount();
        server.stubFor(post(LOAN_OFFERS_ENDPOINT_CALCULATOR)
                .willReturn(aResponse()
                        .withStatus(HttpStatus.BAD_REQUEST.value())
                        .withHeader("Content-Type", "application/json")
                        .withBody(mapper.writeValueAsString(errorMessage))));

        try {
            client.getLoanOffers(loanRequest);
        } catch (FeignException e) {
            assertThat(e.contentUTF8())
                    .contains(errorMessage.message());
            assertThat(e.status())
                    .isEqualTo(HttpStatus.BAD_REQUEST.value());
        }
    }

    @DisplayName("Test send request with ScoringDataDto, scoring error")
    @Test
    public void givenRequestWithScoringDataDtoInvalid_whenSendRequest_thenReturnResponseWithErrorMessageStatus500() throws JsonProcessingException {
        LoanStatementRequestDto loanRequest = TestUtils.getLoanStatementRequestDtoInvalidAmount();
        ErrorMessageDto errorMessage = TestUtils.getErrorMessageScoringError();
        server.stubFor(post(LOAN_OFFERS_ENDPOINT_CALCULATOR)
                .willReturn(aResponse()
                        .withStatus(HttpStatus.INTERNAL_SERVER_ERROR.value())
                        .withHeader("Content-Type", "application/json")
                        .withBody(mapper.writeValueAsString(errorMessage))));

        try {
            client.getLoanOffers(loanRequest);
        } catch (FeignException e) {
            assertThat(e.contentUTF8())
                    .contains(errorMessage.message());
            assertThat(e.status())
                    .isEqualTo(HttpStatus.INTERNAL_SERVER_ERROR.value());
        }
    }
}