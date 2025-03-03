package com.munsun.calculator.services.impl.providers.impl.filters.impl.soft;

import com.munsun.calculator.dto.ScoringDataDto;
import com.munsun.calculator.services.impl.utils.RateAndOtherServiceDto;
import com.munsun.calculator.services.impl.providers.impl.filters.ScoringLoanFilter;
import com.munsun.calculator.services.impl.providers.impl.filters.ScoringSoftFilter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
public class InsuranceSoftScoringFilter implements ScoringSoftFilter, ScoringLoanFilter {
    @Value("${service.insurance.cost}")
    private BigDecimal costInsurance;
    @Value("${scoring.filters.soft.insurance.change_rate}")
    private BigDecimal changeRateValue;

    @Override
    public RateAndOtherServiceDto check(ScoringDataDto scoringDataDto) {
        if(scoringDataDto.getIsInsuranceEnabled()) {
            return new RateAndOtherServiceDto(changeRateValue, costInsurance);
        }
        return new RateAndOtherServiceDto(BigDecimal.ZERO, BigDecimal.ZERO);
    }

    @Override
    public RateAndOtherServiceDto check(boolean status) {
        if(status) {
            return new RateAndOtherServiceDto(changeRateValue, costInsurance);
        }
        return new RateAndOtherServiceDto(BigDecimal.ZERO, BigDecimal.ZERO);
    }
}
