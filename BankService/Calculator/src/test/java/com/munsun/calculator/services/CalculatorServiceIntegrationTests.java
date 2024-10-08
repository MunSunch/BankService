package com.munsun.calculator.services;

import com.munsun.calculator.TestUtils;
import com.munsun.calculator.dto.request.LoanStatementRequestDto;
import com.munsun.calculator.dto.request.ScoringDataDto;
import com.munsun.calculator.dto.response.CreditDto;
import com.munsun.calculator.dto.response.LoanOfferDto;
import com.munsun.calculator.exceptions.ScoringException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrowsExactly;

@SpringBootTest
@ActiveProfiles("default")
public class CalculatorServiceIntegrationTests {
    @Autowired
    private CalculatorService service;

    @DisplayName("Test calculate credit")
    @Test
    public void givenScoringDataDtoAndExpectedCredit_whenCalculateLoan_thenActualCreditEqualsExpectedCredit() {
        ScoringDataDto testScoringDataDto = TestUtils.getScoringDataDto();
        CreditDto expectedCreditDto = TestUtils.getCreditDto();

        CreditDto actualCreditDto = service.calculateCredit(testScoringDataDto);

        assertThat(actualCreditDto)
                .extracting(CreditDto::monthlyPayment, CreditDto::psk, CreditDto::rate, CreditDto::amount, CreditDto::term)
                .containsExactly(expectedCreditDto.monthlyPayment(), expectedCreditDto.psk(), expectedCreditDto.rate(), expectedCreditDto.amount(), expectedCreditDto.term());
    }

    @DisplayName("Test thrown scoring exception")
    @ParameterizedTest
    @MethodSource("scoringDataDtoInvalidDataProvider")
    public void givenScoringDataDtoInvalidData_whenCalculateCredit_thenThrownScoringException(ScoringDataDto scoringDataDto) {
        assertThrowsExactly(ScoringException.class, () -> {
            CreditDto actualCredit = service.calculateCredit(scoringDataDto);
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

    @DisplayName("Test calculate loan offers")
    @Test
    public void givenLoanStatementRequestDto_whenCalculateLoan_thenListLoanOffersSize4() {
        LoanStatementRequestDto testLoanOffer = TestUtils.getLoanStatementRequestDto();
        List<LoanOfferDto> expectedOffers = TestUtils.getAnnuitentPaymentListLoanOffersDtoAmount10_000Term12();

        List<LoanOfferDto> actualOffers = service.calculateLoan(testLoanOffer);

        assertThat(actualOffers)
                .isNotNull()
                .isNotEmpty()
                .hasSize(expectedOffers.size())
                .usingRecursiveFieldByFieldElementComparatorIgnoringFields("statementId")
                .isEqualTo(expectedOffers);
    }
}