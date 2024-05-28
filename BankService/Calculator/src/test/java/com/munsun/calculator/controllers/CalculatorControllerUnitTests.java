package com.munsun.calculator.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.munsun.calculator.TestUtils;
import com.munsun.calculator.dto.request.LoanStatementRequestDto;
import com.munsun.calculator.dto.response.ErrorMessageDto;
import com.munsun.calculator.exceptions.ScoringException;
import com.munsun.calculator.services.CalculatorService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultMatcher;

import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest
public class CalculatorControllerUnitTests {
    @MockBean
    private CalculatorService calculatorService;
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper mapper;

    @ParameterizedTest
    @MethodSource("prescoringDataDtoInvalidDataProvider")
    public void givenLoanStatementRequestInvalidData_whenSendRequest_thenResponseErrorMessagePrescoringStatus400AndMessageStartWordPrescoring(LoanStatementRequestDto loanStatementRequestDto) throws Exception {
        var response = mockMvc.perform(post("/v1/calculator/offers")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(loanStatementRequestDto)))
                .andExpect(status().isBadRequest())
                .andReturn();
        ErrorMessageDto result = mapper.readValue(response.getResponse().getContentAsString(), ErrorMessageDto.class);

        assertThat(result.message().contains("prescoring"))
                .isTrue();
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

    @Test
    public void givenServiceThrownScoringException_whenSendRequest_thenResponseErrorMessageScoringStatus500() throws Exception {
        when(calculatorService.calculateCredit(any()))
                .thenThrow(new ScoringException());

        var response = mockMvc.perform(post("/v1/calculator/calc")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(TestUtils.getScoringDataDto())))
                .andExpect(status().isInternalServerError())
                .andExpect(jsonPath("$.message").value("Scoring:Loan was refused"));
    }
}
