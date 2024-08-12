package com.munsun.calculator.services.impl.providers.impl.filters;

import com.munsun.calculator.dto.utils.RateAndOtherServiceDto;

public interface ScoringLoanFilter {
    RateAndOtherServiceDto check(boolean status);
}
