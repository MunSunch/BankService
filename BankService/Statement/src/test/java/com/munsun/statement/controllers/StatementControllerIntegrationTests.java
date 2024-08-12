package com.munsun.statement.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.tomakehurst.wiremock.WireMockServer;
import com.maciejwalkowiak.wiremock.spring.ConfigureWireMock;
import com.maciejwalkowiak.wiremock.spring.EnableWireMock;
import com.maciejwalkowiak.wiremock.spring.InjectWireMock;
import com.munsun.statement.dto.LoanOfferDto;
import com.munsun.statement.dto.LoanStatementRequestDto;
import com.munsun.statement.utils.TestUtils;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;
import java.util.Objects;
import java.util.stream.Stream;

import static com.github.tomakehurst.wiremock.client.WireMock.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@EnableWireMock(
        @ConfigureWireMock(name="deal-client", property = "${client.deal.url}")
)
@AutoConfigureMockMvc
public class StatementControllerIntegrationTests {
    @InjectWireMock("deal-client")
    private WireMockServer server;
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper mapper;

    @DisplayName("Test get loan offers with unknown error deal-mc")
    @Test
    public void givenConfigServerReturnUnknownExceptionStatus500_whenGetLoanOffers_thenReturnErrorMessageWithDescription() throws Exception {
        server.stubFor(post(TestUtils.LOAN_OFFERS_ENDPOINT_DEAL)
                .willReturn(aResponse()
                        .withStatus(HttpStatus.INTERNAL_SERVER_ERROR.value())
                        .withHeader("Content-Type", "application/json")
                        .withBody(TestUtils.getErrorMessageWithUnknownExceptionJSON())));

        mockMvc.perform(org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post(TestUtils.LOAN_OFFERS_ENDPOINT_STATEMENT)
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(TestUtils.getLoanStatementRequestDto())))
                .andExpect(status().isInternalServerError())
                .andExpect(jsonPath("$.message", containing("Server's error")).exists());
    }

    @DisplayName("Test prescoring")
    @ParameterizedTest
    @MethodSource("prescoringDataDtoInvalidDataProvider")
    public void givenInvalidLoanStatement_sendRequestToGetLoanOffers_thenReturnResponseWithPrescoringException(LoanStatementRequestDto loanStatement) throws Exception {
        mockMvc.perform(org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post(TestUtils.LOAN_OFFERS_ENDPOINT_STATEMENT)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(loanStatement)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message", containing("prescoring")).exists());
    }

    private static Stream<Arguments> prescoringDataDtoInvalidDataProvider() {
        return Stream.of(
                Arguments.of(TestUtils.getLoanStatementRequestDtoInvalidAmount()),
                Arguments.of(TestUtils.getLoanStatementRequestDtoInvalidTerm()),
                Arguments.of(TestUtils.getLoanStatementRequestDtoInvalidFirstName()),
                Arguments.of(TestUtils.getLoanStatementRequestDtoInvalidLastName()),
                Arguments.of(TestUtils.getLoanStatementRequestDtoInvalidMiddleName()),
                Arguments.of(TestUtils.getLoanStatementRequestDtoInvalidEmail()),
                Arguments.of(TestUtils.getLoanStatementRequestDtoInvalidBirthdate()),
                Arguments.of(TestUtils.getLoanStatementRequestDtoInvalidPassportSeries()),
                Arguments.of(TestUtils.getLoanStatementRequestDtoInvalidPassportNumber())
        );
    }

    @DisplayName("Test get loan offers")
    @Test
    public void givenConfigServerReturnLoanOffers_whenSendRequestToGetLoanOffers_thenReturnResponseWithListLoanOffers() throws Exception {
        server.stubFor(post(TestUtils.LOAN_OFFERS_ENDPOINT_DEAL)
                .willReturn(aResponse()
                        .withStatus(HttpStatus.OK.value())
                        .withHeader("Content-Type", "application/json")
                        .withBody(TestUtils.getListLoanOffersJSON())));

        var response = mockMvc.perform(org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post(TestUtils.LOAN_OFFERS_ENDPOINT_STATEMENT)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(TestUtils.getLoanStatementRequestDto())))
                .andExpect(status().isOk())
                .andReturn();
        List<LoanOfferDto> loanOffers = mapper.readValue(response.getResponse().getContentAsString(), mapper.getTypeFactory().constructCollectionType(List.class, LoanOfferDto.class));

        assertThat(loanOffers)
                .isNotNull().isNotEmpty()
                .hasSize(4)
                .allMatch(Objects::nonNull);
    }

    @DisplayName("Test select not exists loanOffer")
    @Test
    public void givenConfigServerReturnStatementNotFoundStatus404_whenRequestToSelectLoanOffer_thenResponseErrorMessageWithStatementNotFoundStatus404() throws Exception {
        server.stubFor(post(TestUtils.SELECT_LOAN_OFFER_ENDPOINT_DEAL)
                .willReturn(aResponse()
                        .withStatus(HttpStatus.NOT_FOUND.value())
                        .withHeader("Content-Type", "application/json")
                        .withBody(TestUtils.getErrorMessageWithStatementNotFoundJSON())));

        mockMvc.perform(org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post(TestUtils.SELECT_LOAN_OFFER_ENDPOINT_STATEMENT)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(TestUtils.getAnnuitentPaymentLoanOfferDtoAmount10_000Term12())))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.message", containing("Statement not found")).exists());
    }

    @DisplayName("Test select loanOffer")
    @Test
    public void givenConfigServerReturnStatus200_whenRequestToSelectLoanOffer_thenResponseStatus200() throws Exception {
        server.stubFor(post(TestUtils.SELECT_LOAN_OFFER_ENDPOINT_DEAL)
                .willReturn(aResponse()
                        .withStatus(HttpStatus.OK.value())
                        .withHeader("Content-Type", "application/json")));

        mockMvc.perform(org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post(TestUtils.SELECT_LOAN_OFFER_ENDPOINT_STATEMENT)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(TestUtils.getAnnuitentPaymentLoanOfferDtoAmount10_000Term12())))
                .andExpect(status().isOk());
    }

    @DisplayName("Test deal-mc unknown error, select loan offer")
    @Test
    public void givenConfigServerReturnUnknownErrorStatus500_whenRequestToSelectLoanOffer_thenResponseErrorMessageStatus500() throws Exception {
        server.stubFor(post(TestUtils.SELECT_LOAN_OFFER_ENDPOINT_DEAL)
                .willReturn(aResponse()
                        .withStatus(HttpStatus.INTERNAL_SERVER_ERROR.value())
                        .withHeader("Content-Type", "application/json")
                        .withBody(TestUtils.getErrorMessageWithUnknownExceptionJSON())));

        mockMvc.perform(org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post(TestUtils.SELECT_LOAN_OFFER_ENDPOINT_STATEMENT)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(TestUtils.getAnnuitentPaymentLoanOfferDtoAmount10_000Term12())))
                .andExpect(status().isInternalServerError())
                .andExpect(jsonPath("$.message", containing("Server's error")).exists());
    }
}