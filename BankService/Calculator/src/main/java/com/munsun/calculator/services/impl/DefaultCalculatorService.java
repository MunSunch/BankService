package com.munsun.calculator.services.impl;

import com.munsun.calculator.dto.request.LoanStatementRequestDto;
import com.munsun.calculator.dto.request.ScoringDataDto;
import com.munsun.calculator.dto.request.enums.TypePayments;
import com.munsun.calculator.dto.response.CreditDto;
import com.munsun.calculator.dto.response.LoanOfferDto;
import com.munsun.calculator.dto.utils.SimpleScoringInfoDto;
import com.munsun.calculator.exceptions.ScoringException;
import com.munsun.calculator.services.CalculatorService;
import com.munsun.calculator.services.impl.providers.CreditCalculator;
import com.munsun.calculator.services.impl.providers.ScoringProvider;
import com.munsun.calculator.dto.utils.RateAndOtherServiceDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

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
                    creditCalculator.generateLoanOfferWithAnnuietyPayments(loanStatementRequestDto.amount(), loanStatementRequestDto.term(), info);
            case DIFFERENTIAL ->
                    creditCalculator.generateLoanOfferWithDifferentPayments(loanStatementRequestDto.amount(), loanStatementRequestDto.term(), info);
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
