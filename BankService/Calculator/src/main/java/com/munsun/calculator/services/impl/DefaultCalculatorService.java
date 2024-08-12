package com.munsun.calculator.services.impl;

import com.munsun.calculator.dto.request.LoanStatementRequestDto;
import com.munsun.calculator.dto.request.ScoringDataDto;
import com.munsun.calculator.dto.response.CreditDto;
import com.munsun.calculator.dto.response.LoanOfferDto;
import com.munsun.calculator.dto.utils.SimpleScoringInfoDto;
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
    public CreditDto calculateCredit(ScoringDataDto scoringDataDto) {
        RateAndOtherServiceDto resultScoring = scoringProvider.fullScoring(scoringDataDto);
        return creditCalculator.calculate(scoringDataDto, resultScoring.newRate(), resultScoring.otherService());
    }

    @Override
    public List<LoanOfferDto> calculateLoan(LoanStatementRequestDto loan) {
        List<SimpleScoringInfoDto> info = scoringProvider.simpleScoring();
        return creditCalculator.generateLoanOffer(loan.amount(), loan.term(), info)
                .stream()
                .sorted((offer1, offer2) -> offer1.rate().subtract(offer2.rate()).intValue())
                .collect(Collectors.toList());
    }
}
