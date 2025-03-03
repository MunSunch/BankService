package com.munsun.calculator.controllers;

import com.munsun.calculator.dto.CreditDto;
import com.munsun.calculator.dto.LoanOfferDto;
import com.munsun.calculator.dto.TypePayments;
import com.munsun.calculator.utils.TestUtils;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.reactive.server.WebTestClient;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class CalculatorControllerIT {
    @Autowired
    private WebTestClient webTestClient;

    @Test
    public void givenLoanStatementRequestDto_whenCalculatePossibleLoanTermsWithAnnuityPayments_thenOk200() throws Exception {
        var result = webTestClient
                    .post().uri(uriBuilder -> uriBuilder
                                .path("/v1/calculator/offers")
                                .queryParam("typePayment", TypePayments.ANNUITY.name())
                                .build())
                    .contentType(MediaType.APPLICATION_JSON)
                    .bodyValue(TestUtils.getLoanStatementRequestDto())
                .exchange()
                    .expectStatus().isOk()
                    .expectHeader().contentType(MediaType.APPLICATION_JSON)
                .expectBodyList(LoanOfferDto.class).hasSize(4).returnResult();

        assertThat(result.getResponseBody())
                .usingRecursiveComparison()
                .isNotNull();
    }

    @Test
    public void givenLoanStatementRequestDto_whenCalculatePossibleLoanTermsWithDifferentPayments_thenOk200() throws Exception {
        webTestClient
                .post().uri(uriBuilder -> uriBuilder
                        .path("/v1/calculator/offers")
                        .queryParam("typePayment", TypePayments.DIFFERENTIAL.name())
                        .build())
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(TestUtils.getLoanStatementRequestDto())
                .exchange()
                .expectStatus().isOk()
                .expectHeader().contentType(MediaType.APPLICATION_JSON)
                .expectBodyList(LoanOfferDto.class).hasSize(4);
    }

    @Test
    public void givenScoringDataDto_whenFullCalculateLoanParametersAndScoringWithAnnuityPayments_thenReturnCreditDtoHttpStatus200() throws Exception {
        webTestClient
                    .post().uri(uriBuilder -> uriBuilder
                                .path("/v1/calculator/calc")
                                .queryParam("typePayment", TypePayments.ANNUITY.name())
                                .build())
                    .contentType(MediaType.APPLICATION_JSON)
                    .bodyValue(TestUtils.getScoringDataDto())
                .exchange()
                    .expectStatus().isOk()
                    .expectBody(CreditDto.class);
    }

    @Test
    public void givenScoringDataDto_whenFullCalculateLoanParametersAndScoringWithDifferentPayments_thenReturnCreditDtoHttpStatus200() throws Exception {
        webTestClient
                .post().uri(uriBuilder -> uriBuilder
                        .path("/v1/calculator/calc")
                        .queryParam("typePayment", TypePayments.DIFFERENTIAL.name())
                        .build())
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(TestUtils.getScoringDataDto())
                .exchange()
                .expectStatus().isOk()
                .expectBody(CreditDto.class);
    }
}
