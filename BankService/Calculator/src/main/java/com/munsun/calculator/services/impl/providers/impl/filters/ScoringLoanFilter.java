package com.munsun.calculator.services.impl.providers.impl.filters;

import com.munsun.calculator.services.impl.utils.RateAndOtherServiceDto;

public interface ScoringLoanFilter {
    RateAndOtherServiceDto check(boolean status);
}
