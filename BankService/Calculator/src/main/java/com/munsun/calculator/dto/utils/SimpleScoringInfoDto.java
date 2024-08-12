package com.munsun.calculator.dto.utils;

import java.util.Map;

public record SimpleScoringInfoDto(
    Map<String, Boolean> filters,
    RateAndOtherServiceDto rateAndOtherServiceDto
) {}