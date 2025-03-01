package com.munsun.calculator.services.impl;

import com.munsun.calculator.dto.*;
import com.munsun.calculator.services.impl.utils.SimpleScoringInfoDto;
import com.munsun.calculator.services.CalculatorService;
import com.munsun.calculator.services.impl.providers.CreditCalculator;
import com.munsun.calculator.services.impl.providers.ScoringProvider;
import com.munsun.calculator.services.impl.utils.RateAndOtherServiceDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@RequiredArgsConstructor
public class DefaultCalculatorService implements CalculatorService {
    private final ScoringProvider scoringProvider;
    private final CreditCalculator creditCalculator;

    @Override
    public List<LoanOfferDto> calculateLoan(TypePayments typePayment, LoanStatementRequestDto loanStatementRequestDto) {
        List<SimpleScoringInfoDto> info = scoringProvider.simpleScoring();
        return switch (typePayment) {
            case ANNUITY ->
                    creditCalculator.generateLoanOfferWithAnnuietyPayments(loanStatementRequestDto.getAmount(), loanStatementRequestDto.getTerm(), info);
            case DIFFERENTIAL ->
                    creditCalculator.generateLoanOfferWithDifferentPayments(loanStatementRequestDto.getAmount(), loanStatementRequestDto.getTerm(), info);
        };
    }

    @Override
    public CreditDto calculateCredit(TypePayments typePayment, ScoringDataDto scoringDataDto) {
        RateAndOtherServiceDto resultScoring = scoringProvider.fullScoring(scoringDataDto);
        return switch (typePayment) {
            case ANNUITY ->
                    creditCalculator.calculateWithAnnuietyPayments(scoringDataDto, resultScoring.newRate(), resultScoring.otherService());
            case DIFFERENTIAL ->
                    creditCalculator.calculateWithDifferentPayments(scoringDataDto, resultScoring.newRate(), resultScoring.otherService());
        };
    }
}
