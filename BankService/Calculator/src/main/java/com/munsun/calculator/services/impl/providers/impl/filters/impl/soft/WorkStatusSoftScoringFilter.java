package com.munsun.calculator.services.impl.providers.impl.filters.impl.soft;

import com.munsun.calculator.dto.request.ScoringDataDto;
import com.munsun.calculator.dto.utils.RateAndOtherServiceDto;
import com.munsun.calculator.services.impl.providers.impl.filters.ScoringSoftFilter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
public class WorkStatusSoftScoringFilter implements ScoringSoftFilter {
    @Value("${scoring.filters.soft.work_status.self_employed.change_rate}")
    private BigDecimal changeRateValueSelfEmployed;
    @Value("${scoring.filters.soft.work_status.businessman.change_rate}")
    private BigDecimal changeRateValueBusinessman;
    @Override
    public RateAndOtherServiceDto check(ScoringDataDto scoringDataDto) {
        switch(scoringDataDto.employment().employmentStatus()) {
            case SELF_EMPLOYED -> {
                return new RateAndOtherServiceDto(changeRateValueSelfEmployed, BigDecimal.ZERO);
            }
            case BUSINESSMAN -> {
                return new RateAndOtherServiceDto(changeRateValueBusinessman, BigDecimal.ZERO);
            }
            default -> {
                return new RateAndOtherServiceDto(BigDecimal.ZERO, BigDecimal.ZERO);
            }
        }
    }
}
