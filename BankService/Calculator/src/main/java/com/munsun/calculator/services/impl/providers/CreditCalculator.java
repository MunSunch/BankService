package com.munsun.calculator.services.impl.providers;

import com.munsun.calculator.dto.request.ScoringDataDto;
import com.munsun.calculator.dto.response.CreditDto;
import com.munsun.calculator.dto.response.LoanOfferDto;
import com.munsun.calculator.dto.utils.SimpleScoringInfoDto;

import java.math.BigDecimal;
import java.util.List;

public interface CreditCalculator {
    CreditDto calculateWithAnnuietyPayments(ScoringDataDto scoringDataDto, BigDecimal newRate, BigDecimal otherService);
    List<LoanOfferDto> generateLoanOfferWithAnnuietyPayments(BigDecimal amount, Integer term, List<SimpleScoringInfoDto> info);

    CreditDto calculateWithDifferentPayments(ScoringDataDto scoringDataDto, BigDecimal newRate, BigDecimal otherService);
    List<LoanOfferDto> generateLoanOfferWithDifferentPayments(BigDecimal amount, Integer term, List<SimpleScoringInfoDto> info);
}
