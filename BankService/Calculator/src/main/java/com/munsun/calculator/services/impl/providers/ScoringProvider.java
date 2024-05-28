package com.munsun.calculator.services.impl.providers;

import com.munsun.calculator.dto.request.ScoringDataDto;
import com.munsun.calculator.dto.utils.RateAndOtherServiceDto;

import java.math.BigDecimal;

public interface ScoringProvider {
    RateAndOtherServiceDto fullScoring(ScoringDataDto scoringDataDto);
    boolean hardScoring(ScoringDataDto scoringDataDto);

    RateAndOtherServiceDto softScoring(ScoringDataDto scoringDataDto, BigDecimal rate);
}
