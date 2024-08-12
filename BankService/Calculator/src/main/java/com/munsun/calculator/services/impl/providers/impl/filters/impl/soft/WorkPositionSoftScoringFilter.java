package com.munsun.calculator.services.impl.providers.impl.filters.impl.soft;

import com.munsun.calculator.dto.request.ScoringDataDto;
import com.munsun.calculator.dto.utils.RateAndOtherServiceDto;
import com.munsun.calculator.services.impl.providers.impl.filters.ScoringSoftFilter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
public class WorkPositionSoftScoringFilter implements ScoringSoftFilter {
    @Value("${scoring.filters.soft.work_position.middle_manager.change_rate}")
    private BigDecimal changeRateValueMiddleManager;
    @Value("${scoring.filters.soft.work_position.top_manager.change_rate}")
    private BigDecimal changeRateValueTopManager;

    @Override
    public RateAndOtherServiceDto check(ScoringDataDto scoringDataDto) {
        switch(scoringDataDto.employment().position()) {
            case MID_MANAGER -> {
                return new RateAndOtherServiceDto(changeRateValueMiddleManager, BigDecimal.ZERO);
            }
            case TOP_MANAGER -> {
                return new RateAndOtherServiceDto(changeRateValueTopManager, BigDecimal.ZERO);
            }
            default -> {
                return new RateAndOtherServiceDto(BigDecimal.ZERO, BigDecimal.ZERO);
            }
        }
    }
}
