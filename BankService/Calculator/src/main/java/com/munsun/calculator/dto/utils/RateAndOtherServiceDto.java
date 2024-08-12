package com.munsun.calculator.dto.utils;

import java.math.BigDecimal;

public record RateAndOtherServiceDto(
        BigDecimal newRate,
        BigDecimal otherService
) {}