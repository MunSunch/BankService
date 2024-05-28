package com.munsun.calculator.services.impl.providers.impl.filters.impl.soft;

import com.munsun.calculator.dto.request.ScoringDataDto;
import com.munsun.calculator.dto.utils.RateAndOtherServiceDto;
import com.munsun.calculator.services.impl.providers.impl.filters.ScoringSoftFilter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
public class InsuranceSoftScoringFilter implements ScoringSoftFilter {
    @Value("${service.insurance.cost}")
    private BigDecimal costInsurance;
    @Value("${scoring.filters.soft.insurance.change_rate}")
    private BigDecimal changeRateValue;

    @Override
    public RateAndOtherServiceDto check(ScoringDataDto scoringDataDto) {
        if(scoringDataDto.isInsuranceEnabled()) {
            return new RateAndOtherServiceDto(changeRateValue, costInsurance);
        }
        return new RateAndOtherServiceDto(BigDecimal.ZERO, BigDecimal.ZERO);
    }
}
