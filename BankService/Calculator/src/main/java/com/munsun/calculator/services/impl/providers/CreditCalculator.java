package com.munsun.calculator.services.impl.providers;

import com.munsun.calculator.dto.request.ScoringDataDto;
import com.munsun.calculator.dto.response.CreditDto;
import com.munsun.calculator.dto.response.LoanOfferDto;

import java.math.BigDecimal;

public interface CreditCalculator {
    CreditDto calculate(ScoringDataDto scoringDataDto, BigDecimal newRate, BigDecimal otherService);
    LoanOfferDto generateLoanOffer(BigDecimal amount, Integer term, boolean b, boolean b1);
}
