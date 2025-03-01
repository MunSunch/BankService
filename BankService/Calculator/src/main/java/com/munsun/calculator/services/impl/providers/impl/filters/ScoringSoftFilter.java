package com.munsun.calculator.services.impl.providers.impl.filters;

import com.munsun.calculator.dto.ScoringDataDto;
import com.munsun.calculator.services.impl.utils.RateAndOtherServiceDto;

public interface ScoringSoftFilter {
    RateAndOtherServiceDto check(ScoringDataDto scoringDataDto);
}
