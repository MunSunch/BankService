package com.munsun.calculator.services.impl.providers.impl.filters.impl.hard;

import com.munsun.calculator.dto.ScoringDataDto;
import com.munsun.calculator.services.impl.providers.impl.filters.ScoringHardFilter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

@Slf4j
@Component
public class AgeHardScoringFilter implements ScoringHardFilter {
    @Value("${scoring.filters.hard.age.min}")
    private Integer minAge;
    @Value("${scoring.filters.hard.age.max}")
    private Integer maxAge;

    @Override
    public boolean check(ScoringDataDto scoringDataDto) {
        LocalDate now = LocalDate.now();
        long age = ChronoUnit.YEARS.between(scoringDataDto.getBirthdate(), now);
        return age>=minAge && age<=maxAge;
    }

}
