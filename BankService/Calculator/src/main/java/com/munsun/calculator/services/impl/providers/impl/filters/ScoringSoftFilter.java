package com.munsun.calculator.services.impl.providers.impl.filters;

import com.munsun.calculator.dto.request.ScoringDataDto;
import com.munsun.calculator.dto.utils.RateAndOtherServiceDto;

public interface ScoringSoftFilter {
    RateAndOtherServiceDto check(ScoringDataDto scoringDataDto);
}
