package com.munsun.calculator.services.impl.utils;

import java.math.BigDecimal;

public record RateAndOtherServiceDto(
        BigDecimal newRate,
        BigDecimal otherService
) {}