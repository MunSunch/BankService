package com.munsun.calculator.services.impl.providers.impl;

import com.munsun.calculator.dto.request.ScoringDataDto;
import com.munsun.calculator.exceptions.ScoringException;
import com.munsun.calculator.services.impl.providers.ScoringProvider;
import com.munsun.calculator.services.impl.providers.impl.filters.ScoringHardFilter;
import com.munsun.calculator.services.impl.providers.impl.filters.ScoringSoftFilter;
import com.munsun.calculator.dto.utils.RateAndOtherServiceDto;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.List;

@Component
@RequiredArgsConstructor
public class DefaultScoringProvider implements ScoringProvider {
    private final List<ScoringHardFilter> hardFilters;
    private final List<ScoringSoftFilter> softFilters;
    @Value("${service.rate}")
    private BigDecimal rate;

    @Override
    public RateAndOtherServiceDto fullScoring(ScoringDataDto scoringDataDto) {
        if(!hardScoring(scoringDataDto)) {
            throw new ScoringException();
        }
        return softScoring(scoringDataDto, rate);
    }

    @Override
    public boolean hardScoring(ScoringDataDto scoringDataDto) {
        return hardFilters.stream()
                .allMatch(filter -> filter.check(scoringDataDto));
    }

    @Override
    public RateAndOtherServiceDto softScoring(ScoringDataDto scoringDataDto, BigDecimal rate) {
        var resultList =  softFilters.stream()
                                        .map(filter -> filter.check(scoringDataDto))
                                        .toList();
        BigDecimal diffRate = resultList.stream()
                .map(RateAndOtherServiceDto::newRate)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
        BigDecimal otherService = resultList.stream()
                .map(RateAndOtherServiceDto::otherService)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
        return new RateAndOtherServiceDto(rate.add(diffRate), otherService);
    }
}
