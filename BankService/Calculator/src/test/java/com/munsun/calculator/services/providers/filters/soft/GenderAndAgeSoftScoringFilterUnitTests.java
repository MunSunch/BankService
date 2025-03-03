package com.munsun.calculator.services.providers.filters.soft;

import com.munsun.calculator.dto.ScoringDataDto;
import com.munsun.calculator.utils.TestUtils;
import com.munsun.calculator.services.impl.utils.RateAndOtherServiceDto;
import com.munsun.calculator.services.impl.providers.impl.filters.impl.soft.GenderAndAgeSoftScoringFilter;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
public class GenderAndAgeSoftScoringFilterUnitTests {
    @Autowired
    private GenderAndAgeSoftScoringFilter softScoringFilter;

    @DisplayName("Negative test change rate gender is male, age is 26")
    @Test
    public void givenScoringDataDtoMaleGenderAge18_whenCheck_thenReturnInsuranceZeroAndChangeRateZero() {
        ScoringDataDto testScoringDataDto = TestUtils.getScoringDataDto();
        RateAndOtherServiceDto expected = new RateAndOtherServiceDto(BigDecimal.ZERO, BigDecimal.ZERO);

        RateAndOtherServiceDto actual = softScoringFilter.check(testScoringDataDto);

        assertThat(actual)
                .isEqualTo(expected);
    }

    @DisplayName("Negative test change rate gender is male, age is 124")
    @Test
    public void givenScoringDataDtoMaleGenderAge124_whenCheck_thenReturnInsuranceZeroAndChangeRateZero() {
        ScoringDataDto testScoringDataDto = TestUtils.getScoringDataDtoGreaterMaxAge();
        RateAndOtherServiceDto expected = new RateAndOtherServiceDto(BigDecimal.ZERO, BigDecimal.ZERO);

        RateAndOtherServiceDto actual = softScoringFilter.check(testScoringDataDto);

        assertThat(actual)
                .isEqualTo(expected);
    }

    @DisplayName("Positive test change rate gender is male, age is 34")
    @Test
    public void givenScoringDataDtoMaleGenderAge34_whenCheck_thenReturnInsuranceZeroAndChangeRate() {
        ScoringDataDto testScoringDataDto = TestUtils.getScoringDataDtoOfTopManager();
        RateAndOtherServiceDto expected = new RateAndOtherServiceDto(new BigDecimal("-3"), BigDecimal.ZERO);

        RateAndOtherServiceDto actual = softScoringFilter.check(testScoringDataDto);

        assertThat(actual)
                .isEqualTo(expected);
    }

    @DisplayName("Positive test change rate gender is female, age is 40")
    @Test
    public void givenScoringDataDtoFemaleGenderAge40_whenCheck_thenReturnInsuranceZeroAndChangeRate() {
        ScoringDataDto testScoringDataDto = TestUtils.getScoringDataDtoMilf();
        RateAndOtherServiceDto expected = new RateAndOtherServiceDto(new BigDecimal("-3"), BigDecimal.ZERO);

        RateAndOtherServiceDto actual = softScoringFilter.check(testScoringDataDto);

        assertThat(actual)
                .isEqualTo(expected);
    }

    @DisplayName("Negative test change rate gender is female, age is 84")
    @Test
    public void givenScoringDataDtoFemaleGenderAge84_whenCheck_thenReturnInsuranceZeroAndChangeRateZero() {
        ScoringDataDto testScoringDataDto = TestUtils.getScoringDataDtoGreaterMaxAgeFemale();
        RateAndOtherServiceDto expected = new RateAndOtherServiceDto(BigDecimal.ZERO, BigDecimal.ZERO);

        RateAndOtherServiceDto actual = softScoringFilter.check(testScoringDataDto);

        assertThat(actual)
                .isEqualTo(expected);
    }

    @DisplayName("Negative test change rate gender is female, age is 20")
    @Test
    public void givenScoringDataDtoFemaleGenderAge20_whenCheck_thenReturnInsuranceZeroAndChangeRateZero() {
        ScoringDataDto testScoringDataDto = TestUtils.getScoringDataDtoLessMinAgeFemale();
        RateAndOtherServiceDto expected = new RateAndOtherServiceDto(BigDecimal.ZERO, BigDecimal.ZERO);

        RateAndOtherServiceDto actual = softScoringFilter.check(testScoringDataDto);

        assertThat(actual)
                .isEqualTo(expected);
    }

    @DisplayName("Positive test change rate gender is not binary")
    @Test
    public void givenScoringDataDtoNotBinaryGender_whenCheck_thenReturnInsuranceZeroAndChangeRate() {
        ScoringDataDto testScoringDataDto = TestUtils.getScoringDataDtoNotBinaryGender();
        RateAndOtherServiceDto expected = new RateAndOtherServiceDto(new BigDecimal("7"), BigDecimal.ZERO);

        RateAndOtherServiceDto actual = softScoringFilter.check(testScoringDataDto);

        assertThat(actual)
                .isEqualTo(expected);
    }
}
