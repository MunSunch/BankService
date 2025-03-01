package com.munsun.calculator.services.impl.utils;

import java.util.Map;

public record SimpleScoringInfoDto(
        Map<String, Boolean> filters,
        RateAndOtherServiceDto rateAndOtherServiceDto
) {}