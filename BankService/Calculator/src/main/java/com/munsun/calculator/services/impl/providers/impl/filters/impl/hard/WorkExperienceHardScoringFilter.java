package com.munsun.calculator.services.impl.providers.impl.filters.impl.hard;

import com.munsun.calculator.dto.request.ScoringDataDto;
import com.munsun.calculator.services.impl.providers.impl.filters.ScoringHardFilter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class WorkExperienceHardScoringFilter implements ScoringHardFilter {
    @Value("${scoring.filters.hard.work.experience.total}")
    private Integer workExperienceTotal;

    @Value("${scoring.filters.hard.work.experience.current}")
    private Integer workExperienceCurrent;

    @Override
    public boolean check(ScoringDataDto scoringDataDto) {
        return scoringDataDto.employment().workExperienceTotal() >= workExperienceTotal
                && scoringDataDto.employment().workExperienceCurrent() >= workExperienceCurrent;
    }

}
