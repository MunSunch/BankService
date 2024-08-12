package com.munsun.calculator.services.impl.providers.impl.filters.impl.hard;

import com.munsun.calculator.dto.request.ScoringDataDto;
import com.munsun.calculator.services.impl.providers.impl.filters.ScoringHardFilter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Slf4j
@Component
public class AmountHardScoringFilter implements ScoringHardFilter {
    @Value("${scoring.filters.hard.loan_amount.count_salary}")
    private Integer countSalary;
    @Override
    public boolean check(ScoringDataDto scoringDataDto) {
        BigDecimal twentyFiveSalaries = scoringDataDto.employment().salary()
                .multiply(BigDecimal.valueOf(countSalary));
        return scoringDataDto.amount().compareTo(twentyFiveSalaries) <= 0;
    }
}
