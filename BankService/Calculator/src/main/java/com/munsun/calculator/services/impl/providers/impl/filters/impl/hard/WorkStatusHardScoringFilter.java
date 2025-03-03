package com.munsun.calculator.services.impl.providers.impl.filters.impl.hard;

import com.munsun.calculator.dto.EmploymentDto;
import com.munsun.calculator.dto.ScoringDataDto;
import com.munsun.calculator.services.impl.providers.impl.filters.ScoringHardFilter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class WorkStatusHardScoringFilter implements ScoringHardFilter {
    @Override
    public boolean check(ScoringDataDto scoringDataDto) {
        return scoringDataDto.getEmployment().getEmploymentStatus() != EmploymentDto.EmploymentStatusEnum.UNEMPLOYED;
    }
}
