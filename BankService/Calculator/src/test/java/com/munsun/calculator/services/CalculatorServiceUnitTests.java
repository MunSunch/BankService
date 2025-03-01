package com.munsun.calculator.services;

import com.munsun.calculator.dto.TypePayments;
import com.munsun.calculator.services.impl.providers.CreditCalculator;
import com.munsun.calculator.services.impl.providers.ScoringProvider;
import com.munsun.calculator.services.impl.utils.RateAndOtherServiceDto;
import com.munsun.calculator.utils.TestUtils;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InOrder;
import org.mockito.internal.InOrderImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.math.BigDecimal;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@SpringBootTest
public class CalculatorServiceUnitTests {
    @MockBean
    private CreditCalculator calculator;
    @MockBean
    private ScoringProvider scoringProvider;
    @Autowired
    private CalculatorService service;

    @DisplayName("Test calling methods in the correct order with annuity payments")
    @Test
    public void givenCreditCalculatorAnnuietyPaymentsAndScoringProvider_whenCallCalculateCredit_thenCallMethodsMocksInCorrectOrder() {
        when(scoringProvider.fullScoring(any()))
                .thenReturn(new RateAndOtherServiceDto(new BigDecimal("11"), BigDecimal.ZERO));

        service.calculateCredit(TypePayments.ANNUITY, TestUtils.getScoringDataDto());

        InOrder order = new InOrderImpl(List.of(scoringProvider, calculator));
            order.verify(scoringProvider, atLeastOnce()).fullScoring(any());
            order.verify(calculator, atLeastOnce()).calculateWithAnnuietyPayments(any(), any(), any());
    }

    @DisplayName("Test calling methods in the correct order with different payments")
    @Test
    public void givenCreditCalculatorDifferentPaymentsAndScoringProvider_whenCallCalculateCredit_thenCallMethodsMocksInCorrectOrder() {
        when(scoringProvider.fullScoring(any()))
                .thenReturn(new RateAndOtherServiceDto(new BigDecimal("11"), BigDecimal.ZERO));

        service.calculateCredit(TypePayments.DIFFERENTIAL, TestUtils.getScoringDataDto());

        InOrder order = new InOrderImpl(List.of(scoringProvider, calculator));
            order.verify(scoringProvider, atLeastOnce()).fullScoring(any());
            order.verify(calculator, atLeastOnce()).calculateWithDifferentPayments(any(), any(), any());
    }

    @DisplayName("Test generate credit with annuiety payments")
    @Test
    public void givenGenerateLoanOfferReturnLoanOfferDto_whenCallCalculateLoanAnnuietyPayments_thenCallGenerateLoanOfferFourTimes() {
        service.calculateLoan(TypePayments.ANNUITY, TestUtils.getLoanStatementRequestDto());

        verify(scoringProvider, atLeastOnce()).simpleScoring();
        verify(calculator, atLeastOnce()).generateLoanOfferWithAnnuietyPayments(any(), anyInt(), any());
    }

    @DisplayName("Test generate credit with different payments")
    @Test
    public void givenGenerateLoanOfferReturnLoanOfferDto_whenCallCalculateLoanDifferentPayments_thenCallGenerateLoanOfferFourTimes() {
        service.calculateLoan(TypePayments.DIFFERENTIAL, TestUtils.getLoanStatementRequestDto());

        verify(scoringProvider, atLeastOnce()).simpleScoring();
        verify(calculator, atLeastOnce()).generateLoanOfferWithDifferentPayments(any(), anyInt(), any());
    }
}