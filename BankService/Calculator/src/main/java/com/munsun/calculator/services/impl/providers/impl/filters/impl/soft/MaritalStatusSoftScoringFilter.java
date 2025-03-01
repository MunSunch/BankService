package com.munsun.calculator.services.impl.providers.impl.filters.impl.soft;

import com.munsun.calculator.dto.ScoringDataDto;
import com.munsun.calculator.services.impl.utils.RateAndOtherServiceDto;
import com.munsun.calculator.services.impl.providers.impl.filters.ScoringSoftFilter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
public class MaritalStatusSoftScoringFilter implements ScoringSoftFilter {
    @Value("${scoring.filters.soft.marital_status.single.change_rate}")
    private BigDecimal changeRateValueSingleStatus;
    @Value("${scoring.filters.soft.marital_status.married.change_rate}")
    private BigDecimal changeRateValueMarriedStatus;

    @Override
    public RateAndOtherServiceDto check(ScoringDataDto scoringDataDto) {
        switch(scoringDataDto.getMaritalStatus()) {
            case SINGLE -> {
                return new RateAndOtherServiceDto(changeRateValueSingleStatus, BigDecimal.ZERO);
            }
            case MARRIED -> {
                return new RateAndOtherServiceDto(changeRateValueMarriedStatus, BigDecimal.ZERO);
            }
            default -> {
                return new RateAndOtherServiceDto(BigDecimal.ZERO, BigDecimal.ZERO);
            }
        }
    }
}
