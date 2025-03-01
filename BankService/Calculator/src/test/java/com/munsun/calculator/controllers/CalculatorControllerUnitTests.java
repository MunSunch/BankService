package com.munsun.calculator.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.munsun.calculator.dto.LoanStatementRequestDto;
import com.munsun.calculator.dto.TypePayments;
import com.munsun.calculator.services.CalculatorService;
import com.munsun.calculator.utils.TestUtils;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.stream.Stream;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest
public class CalculatorControllerUnitTests {
    @MockBean
    private CalculatorService service;
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;

    @ParameterizedTest
    @MethodSource("argsProviderFactory")
    public void givenInvalidLoanStatementRequestDto_whenCalculatePossibleLoanTermsWithAnnuityPayments_thenBadRequest400(LoanStatementRequestDto loan) throws Exception {
        mockMvc.perform(
                post("/v1/calculator/offers")
                        .param("typePayment", TypePayments.ANNUITY.name())
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(objectMapper.writeValueAsString(loan))
        ).andExpect(status().isBadRequest());
    }

    @ParameterizedTest
    @MethodSource("argsProviderFactory")
    public void givenInvalidLoanStatementRequestDto_whenCalculatePossibleLoanTermsWithDifferentPayments_thenBadRequest400(LoanStatementRequestDto loan) throws Exception {
        mockMvc.perform(
                post("/v1/calculator/offers")
                        .param("typePayment", TypePayments.DIFFERENTIAL.name())
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(objectMapper.writeValueAsString(loan))
        ).andExpect(status().isBadRequest());
    }

    static Stream<LoanStatementRequestDto> argsProviderFactory() {
        return Stream.of(
                TestUtils.getLoanStatementRequestDtoInvalidAmount(),
                TestUtils.getLoanStatementRequestDtoInvalidTerm(),
                TestUtils.getLoanStatementRequestDtoInvalidBirthdate(),
                TestUtils.getLoanStatementRequestDtoInvalidEmail(),
                TestUtils.getLoanStatementRequestDtoInvalidPassportSeries(),
                TestUtils.getLoanStatementRequestDtoInvalidPassportNumber(),
                TestUtils.getLoanStatementRequestDtoInvalidFirstName(),
                TestUtils.getLoanStatementRequestDtoInvalidLastName(),
                TestUtils.getLoanStatementRequestDtoInvalidMiddleName(),
                TestUtils.getLoanStatementRequestDtoInvalidTerm()
        );
    }
}
