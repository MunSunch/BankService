package com.munsun.calculator.services.impl.providers.impl.filters.impl.soft;

import com.munsun.calculator.dto.request.ScoringDataDto;
import com.munsun.calculator.dto.request.enums.Gender;
import com.munsun.calculator.dto.utils.RateAndOtherServiceDto;
import com.munsun.calculator.services.impl.providers.impl.filters.ScoringSoftFilter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

@Component
public class GenderAndAgeSoftScoringFilter implements ScoringSoftFilter {
    @Value("${scoring.filters.soft.gender_age.age_female.min}")
    private Integer minAgeFemale;
    @Value("${scoring.filters.soft.gender_age.age_female.max}")
    private Integer maxAgeFemale;
    @Value("${scoring.filters.soft.gender_age.age_male.min}")
    private Integer minAgeMale;
    @Value("${scoring.filters.soft.gender_age.age_male.max}")
    private Integer maxAgeMale;

    @Value("${scoring.filters.soft.gender_age.age_female.change_rate}")
    private BigDecimal changeRateFemaleValue;
    @Value("${scoring.filters.soft.gender_age.not_binary.change_rate}")
    private BigDecimal changeRateNotBinaryValue;

    @Override
    public RateAndOtherServiceDto check(ScoringDataDto scoringDataDto) {
        Gender gender = scoringDataDto.gender();
        long age = ChronoUnit.YEARS.between(scoringDataDto.birthdate(), LocalDate.now());
        if(gender==Gender.FEMALE && (age>=minAgeFemale && age<=maxAgeFemale)
            || gender==Gender.MALE && (age>=minAgeMale && age<=maxAgeMale))
        {
            return new RateAndOtherServiceDto(changeRateFemaleValue, BigDecimal.ZERO);
        } else if (gender == Gender.NON_BINARY) {
            return new RateAndOtherServiceDto(changeRateNotBinaryValue, BigDecimal.ZERO);
        }
        return new RateAndOtherServiceDto(BigDecimal.ZERO, BigDecimal.ZERO);
    }
}
