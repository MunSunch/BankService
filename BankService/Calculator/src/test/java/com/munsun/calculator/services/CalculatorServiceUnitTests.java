package com.munsun.calculator.services;

import com.munsun.calculator.utils.TestUtils;
import com.munsun.calculator.dto.request.enums.TypePayments;
import com.munsun.calculator.services.impl.providers.CreditCalculator;
import com.munsun.calculator.services.impl.providers.ScoringProvider;
import com.munsun.calculator.dto.utils.RateAndOtherServiceDto;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InOrder;
import org.mockito.internal.InOrderImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.math.BigDecimal;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertThrowsExactly;
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

    @DisplayName("Test calling methods in the correct order")
    @Test
    public void givenCreditCalculatorAndScoringProvider_whenCallCalculateCredit_thenCallMethodsMocksInCorrectOrder() {
        when(scoringProvider.fullScoring(any()))
                .thenReturn(new RateAndOtherServiceDto(new BigDecimal("11"), BigDecimal.ZERO));

        service.calculateCredit(TypePayments.ANNUITY, TestUtils.getScoringDataDto());

        InOrder order = new InOrderImpl(List.of(scoringProvider, calculator));
            order.verify(scoringProvider, atLeastOnce()).fullScoring(any());
            order.verify(calculator, atLeastOnce()).calculateWithAnnuietyPayments(any(), any(), any());
    }

    @DisplayName("Test calling calculator.generateLoanOffer(...) exactly four times")
    @Test
    public void givenGenerateLoanOfferReturnLoanOfferDto_whenCallCalculateLoan_thenCallGenerateLoanOfferFourTimes() {
        service.calculateLoan(TypePayments.ANNUITY, TestUtils.getLoanStatementRequestDto());

        verify(scoringProvider, atLeastOnce()).simpleScoring();
        verify(calculator, atLeastOnce()).generateLoanOfferWithAnnuietyPayments(any(), anyInt(), any());
    }
}