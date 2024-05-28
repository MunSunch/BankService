package com.munsun.calculator.services.impl.providers.impl.filters;

import com.munsun.calculator.dto.request.ScoringDataDto;

public interface ScoringHardFilter {
    boolean check(ScoringDataDto scoringDataDto);
}
