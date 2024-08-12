package com.munsun.calculator.services.providers.filters.soft;

import com.munsun.calculator.TestUtils;
import com.munsun.calculator.dto.request.ScoringDataDto;
import com.munsun.calculator.dto.utils.RateAndOtherServiceDto;
import com.munsun.calculator.services.impl.providers.impl.filters.impl.soft.MaritalStatusSoftScoringFilter;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
public class MaritalStatusSoftScoringFilterUnitTests {
    @Autowired
    private MaritalStatusSoftScoringFilter softScoringFilter;

    @DisplayName("Test change rate marital status is married")
    @Test
    public void givenScoringDataWithMarriedStatus_whenCheck_thenReturnDtoWithChangeRateAndInsuranceZero() {
        ScoringDataDto testScoringDataDtoMarried = TestUtils.getScoringDataDtoMiddleManager();
        RateAndOtherServiceDto expected = new RateAndOtherServiceDto(new BigDecimal("-3"), BigDecimal.ZERO);

        RateAndOtherServiceDto actual = softScoringFilter.check(testScoringDataDtoMarried);

        assertThat(actual)
                .isEqualTo(expected);
    }

    @DisplayName("Test change rate marital status is single")
    @Test
    public void givenScoringDataWithSingleStatus_whenCheck_thenReturnDtoWithChangeRateAndInsuranceZero() {
        ScoringDataDto testScoringDataDtoSingle = TestUtils.getScoringDataDtoOfTopManager();
        RateAndOtherServiceDto expected = new RateAndOtherServiceDto(new BigDecimal("1"), BigDecimal.ZERO);

        RateAndOtherServiceDto actual = softScoringFilter.check(testScoringDataDtoSingle);

        assertThat(actual)
                .isEqualTo(expected);
    }
}
