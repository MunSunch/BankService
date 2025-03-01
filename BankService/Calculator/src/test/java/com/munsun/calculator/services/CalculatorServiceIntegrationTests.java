package com.munsun.calculator.services;

import com.munsun.calculator.dto.*;
import com.munsun.calculator.exceptions.ScoringException;
import com.munsun.calculator.utils.TestUtils;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrowsExactly;

@SpringBootTest
public class CalculatorServiceIntegrationTests {
    @Autowired
    private CalculatorService service;

    @DisplayName("Test calculate credit with annuity payments")
    @Test
    public void givenScoringDataDtoAndExpectedCredit_whenCalculateCreditWithAnnuityPayment_thenReturnCredit() {
        ScoringDataDto testScoringDataDto = TestUtils.getScoringDataDto();
        CreditDto expectedCreditDto = TestUtils.getCreditDto();

        CreditDto actualCreditDto = service.calculateCredit(TypePayments.ANNUITY, testScoringDataDto);

        assertThat(actualCreditDto)
                .extracting(CreditDto::getMonthlyPayment, CreditDto::getPsk, CreditDto::getRate, CreditDto::getAmount, CreditDto::getTerm)
                .containsExactly(expectedCreditDto.getMonthlyPayment(), expectedCreditDto.getPsk(), expectedCreditDto.getRate(), expectedCreditDto.getAmount(), expectedCreditDto.getTerm());
    }

    @DisplayName("Test calculate loans with annuity payments")
    @Test
    public void givenLoanStatementRequestDto_whenCalculateLoanWithDifferentialPayment_thenReturnLoans() {
        var expectedCreditDto = TestUtils.getLoanStatementRequestDto();

        var actualLoans = service.calculateLoan(TypePayments.DIFFERENTIAL, expectedCreditDto);

        assertThat(actualLoans)
                .isNotEmpty().isNotNull()
                .hasSize(4);
    }

    @DisplayName("Test calculate loans with differential payments")
    @Test
    public void givenLoanStatementRequestDto_whenCalculateCreditWithDifferentialPayment_thenReturnCredit() {
        ScoringDataDto testScoringDataDto = TestUtils.getScoringDataDto();

        CreditDto actualCreditDto = service.calculateCredit(TypePayments.DIFFERENTIAL, testScoringDataDto);

        assertThat(actualCreditDto)
                .isNotNull();
    }

    @DisplayName("Test calculate credit with differential payments")
    @Test
    public void givenLoanStatementRequestDto_whenCalculateLoanWithDifferentialPayment_thenReturn() {
        var expectedCreditDto = TestUtils.getLoanStatementRequestDto();

        var actualLoans = service.calculateLoan(TypePayments.DIFFERENTIAL, expectedCreditDto);

        assertThat(actualLoans)
                .isNotEmpty().isNotNull()
                .hasSize(4);
    }

    @DisplayName("Test thrown scoring exception")
    @ParameterizedTest
    @MethodSource("scoringDataDtoInvalidDataProvider")
    public void givenScoringDataDtoInvalidData_whenCalculateCredit_thenThrownScoringException(ScoringDataDto scoringDataDto) {
        assertThrowsExactly(ScoringException.class, () -> {
            CreditDto actualCredit = service.calculateCredit(TypePayments.ANNUITY, scoringDataDto);
        });
    }

    private static Stream<Arguments> scoringDataDtoInvalidDataProvider() {
        return Stream.of(
                Arguments.of(TestUtils.getScoringDataDtoLessAmount()),
                Arguments.of(TestUtils.getScoringDataDtoLessCurrentWorkExperience()),
                Arguments.of(TestUtils.getScoringDataDtoLessMinAge()),
                Arguments.of(TestUtils.getScoringDataDtoUnemployedWorkStatus()),
                Arguments.of(TestUtils.getScoringDataDtoLessTotalWorkExperience()),
                Arguments.of(TestUtils.getScoringDataDtoGreaterMaxAgeFemale()),
                Arguments.of(TestUtils.getScoringDataDtoHomeless())
        );
    }
}