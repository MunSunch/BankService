package com.munsun.calculator.services.impl.providers.impl.filters.impl.soft;

import com.munsun.calculator.dto.ScoringDataDto;
import com.munsun.calculator.services.impl.utils.RateAndOtherServiceDto;
import com.munsun.calculator.services.impl.providers.impl.filters.ScoringLoanFilter;
import com.munsun.calculator.services.impl.providers.impl.filters.ScoringSoftFilter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
public class SalaryClientSoftScoringFilter implements ScoringSoftFilter, ScoringLoanFilter {
    @Value("${scoring.filters.soft.salary_client.change_rate}")
    private BigDecimal changeRateValue;

    @Override
    public RateAndOtherServiceDto check(ScoringDataDto scoringDataDto) {
        if(scoringDataDto.getIsSalaryClient()) {
            return new RateAndOtherServiceDto(changeRateValue, BigDecimal.ZERO);
        }
        return new RateAndOtherServiceDto(BigDecimal.ZERO, BigDecimal.ZERO);
    }

    @Override
    public RateAndOtherServiceDto check(boolean status) {
        if(status) {
            return new RateAndOtherServiceDto(changeRateValue, BigDecimal.ZERO);
        }
        return new RateAndOtherServiceDto(BigDecimal.ZERO, BigDecimal.ZERO);
    }
}
