package com.munsun.calculator.services.impl.providers;

import com.munsun.calculator.dto.request.ScoringDataDto;
import com.munsun.calculator.dto.response.CreditDto;
import com.munsun.calculator.dto.response.LoanOfferDto;
import com.munsun.calculator.dto.utils.SimpleScoringInfoDto;

import java.math.BigDecimal;
import java.util.List;

public interface CreditCalculator {
    CreditDto calculate(ScoringDataDto scoringDataDto, BigDecimal newRate, BigDecimal otherService);
    List<LoanOfferDto> generateLoanOffer(BigDecimal amount, Integer term, List<SimpleScoringInfoDto> info);
}
