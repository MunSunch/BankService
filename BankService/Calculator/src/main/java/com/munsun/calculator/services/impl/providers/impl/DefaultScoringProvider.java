package com.munsun.calculator.services.impl.providers.impl;

import com.munsun.calculator.dto.request.ScoringDataDto;
import com.munsun.calculator.dto.utils.SimpleScoringInfoDto;
import com.munsun.calculator.exceptions.ScoringException;
import com.munsun.calculator.services.impl.providers.ScoringProvider;
import com.munsun.calculator.services.impl.providers.impl.filters.ScoringHardFilter;
import com.munsun.calculator.services.impl.providers.impl.filters.ScoringLoanFilter;
import com.munsun.calculator.services.impl.providers.impl.filters.ScoringSoftFilter;
import com.munsun.calculator.dto.utils.RateAndOtherServiceDto;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class DefaultScoringProvider implements ScoringProvider {
    private final List<ScoringHardFilter> hardFilters;
    private final List<ScoringSoftFilter> softFilters;
    private final List<ScoringLoanFilter> loanFilters;
    @Value("${service.rate}")
    private BigDecimal rate;

    @Override
    public List<SimpleScoringInfoDto> simpleScoring() {
        int size = loanFilters.size();
        List<SimpleScoringInfoDto> result = new ArrayList<>();
        for (int mask=0; mask<(1<<size); ++mask) {
            List<RateAndOtherServiceDto> temp = new ArrayList<>();
            Map<String, Boolean> filters = new HashMap<>();
            for (int i = 0; i < size; i++) {
                ScoringLoanFilter filter = loanFilters.get(i);
                if((mask >> i & 1) == 1) {
                    filters.put(getName(filter), true);
                    temp.add(filter.check(true));
                } else {
                    filters.put(getName(filter), false);
                    temp.add(filter.check(false));
                }
            }
            RateAndOtherServiceDto diff = temp.stream()
                    .reduce((x,y) -> new RateAndOtherServiceDto(x.newRate().add(y.newRate()),
                            x.otherService().add(y.otherService())))
                    .get();
            result.add(new SimpleScoringInfoDto(filters, new RateAndOtherServiceDto(rate.add(diff.newRate()), diff.otherService())));
        }
        return result;
    }

    private String getName(ScoringLoanFilter filter) {
        String temp = filter.getClass().getSimpleName();
        return temp.substring(0,temp.indexOf("$"));
    }

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
