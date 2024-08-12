package com.munsun.calculator.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.munsun.calculator.TestUtils;
import com.munsun.calculator.dto.request.LoanStatementRequestDto;
import com.munsun.calculator.dto.request.ScoringDataDto;
import com.munsun.calculator.dto.response.CreditDto;
import com.munsun.calculator.dto.response.ErrorMessageDto;
import com.munsun.calculator.dto.response.LoanOfferDto;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;
import java.util.Objects;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class CalculatorControllerIntegrationTests {
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper mapper;

    @ParameterizedTest
    @MethodSource("scoringDataDtoInvalidDataProvider")
    public void givenScoringDataDtoInvalidData_whenSendRequest_thenResponseErrorMessageScoringStatus500(ScoringDataDto scoringDataDto) throws Exception {
        mockMvc.perform(post("/v1/calculator/calc")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(scoringDataDto)))
                .andExpect(status().isInternalServerError())
                .andExpect(jsonPath("$.message").value("Scoring:Loan was refused"));
    }

    private static Stream<Arguments> scoringDataDtoInvalidDataProvider() {
        return Stream.of(
                Arguments.of(TestUtils.getScoringDataDtoHomeless()),
                Arguments.of(TestUtils.getScoringDataDtoLessAmount()),
                Arguments.of(TestUtils.getScoringDataDtoLessMinAge()),
                Arguments.of(TestUtils.getScoringDataDtoUnemployedWorkStatus()),
                Arguments.of(TestUtils.getScoringDataDtoGreaterMaxAge()),
                Arguments.of(TestUtils.getScoringDataDtoGreaterMaxAgeFemale()),
                Arguments.of(TestUtils.getScoringDataDtoLessTotalWorkExperience()),
                Arguments.of(TestUtils.getScoringDataDtoLessCurrentWorkExperience()),
                Arguments.of(TestUtils.getScoringDataDtoLessMinAgeFemale())
        );
    }

    @Test
    public void givenLoanStatementRequestDto_whenSendRequest_thenResponseWithListOffersStatus200() throws Exception {
        LoanStatementRequestDto loanStatementRequestDto = TestUtils.getLoanStatementRequestDto();
        int expectedSize = 4;

        var response = mockMvc.perform(post("/v1/calculator/offers")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(loanStatementRequestDto)))
                    .andExpect(status().isOk())
                    .andReturn();
        List offers = mapper.readValue(response.getResponse().getContentAsString(), List.class);

        assertThat(offers)
                .isNotNull()
                .isNotEmpty()
                .hasSize(expectedSize)
                .allMatch(Objects::nonNull);
    }

    @Test
    public void givenScoringDataDtoAndExpectedCreditDto_whenSendRequest_thenResponseWithCreditStatus200() throws Exception {
        ScoringDataDto scoringDataDto = TestUtils.getScoringDataDto();
        CreditDto expectedCreditDto = TestUtils.getCreditDto();

        var response = mockMvc.perform(post("/v1/calculator/calc")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(scoringDataDto)))
                .andExpect(status().isOk())
                .andReturn();
        CreditDto actualCreditDto = mapper.readValue(response.getResponse().getContentAsString(), CreditDto.class);

        assertThat(actualCreditDto)
                .extracting(CreditDto::monthlyPayment, CreditDto::psk, CreditDto::rate, CreditDto::amount, CreditDto::term)
                .containsExactly(expectedCreditDto.monthlyPayment(), expectedCreditDto.psk(), expectedCreditDto.rate(), expectedCreditDto.amount(), expectedCreditDto.term());
    }
}