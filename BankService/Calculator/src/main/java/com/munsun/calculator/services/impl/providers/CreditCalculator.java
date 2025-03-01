package com.munsun.calculator.services.impl.providers;

import com.munsun.calculator.dto.CreditDto;
import com.munsun.calculator.dto.LoanOfferDto;
import com.munsun.calculator.dto.ScoringDataDto;
import com.munsun.calculator.services.impl.utils.SimpleScoringInfoDto;

import java.math.BigDecimal;
import java.util.List;

public interface CreditCalculator {
    CreditDto calculateWithAnnuietyPayments(ScoringDataDto scoringDataDto, BigDecimal newRate, BigDecimal otherService);
    List<LoanOfferDto> generateLoanOfferWithAnnuietyPayments(BigDecimal amount, Integer term, List<SimpleScoringInfoDto> info);

    CreditDto calculateWithDifferentPayments(ScoringDataDto scoringDataDto, BigDecimal newRate, BigDecimal otherService);
    List<LoanOfferDto> generateLoanOfferWithDifferentPayments(BigDecimal amount, Integer term, List<SimpleScoringInfoDto> info);
}
