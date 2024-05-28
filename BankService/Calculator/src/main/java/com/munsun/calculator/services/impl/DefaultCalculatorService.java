package com.munsun.calculator.services.impl;

import com.munsun.calculator.dto.request.LoanStatementRequestDto;
import com.munsun.calculator.dto.request.ScoringDataDto;
import com.munsun.calculator.dto.response.CreditDto;
import com.munsun.calculator.dto.response.LoanOfferDto;
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
        return List.of(creditCalculator.generateLoanOffer(loan.amount(), loan.term(), true, true),
                       creditCalculator.generateLoanOffer(loan.amount(), loan.term(), true, false),
                       creditCalculator.generateLoanOffer(loan.amount(), loan.term(), false, true),
                       creditCalculator.generateLoanOffer(loan.amount(), loan.term(), false, false))
                .stream().sorted((firstOfferLoan, secondOfferLoan) -> firstOfferLoan.rate().compareTo(secondOfferLoan.rate()))
                .collect(Collectors.toList());
    }
}
