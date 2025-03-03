package com.munsun.calculator.services.providers.filters.soft;

import com.munsun.calculator.dto.ScoringDataDto;
import com.munsun.calculator.utils.TestUtils;
import com.munsun.calculator.services.impl.utils.RateAndOtherServiceDto;
import com.munsun.calculator.services.impl.providers.impl.filters.impl.soft.WorkStatusSoftScoringFilter;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
public class WorkStatusSoftScoringFilterUnitTests {
    @Autowired
    private WorkStatusSoftScoringFilter softFilter;

    @DisplayName("Test check ScoringDataDto with work status is SELF_EMPLOYED")
    @Test
    public void givenScoringDataDtoSelfEmployedWorkStatus_whenCheck_thenExpectedEqualsActualRateAndOtherServiceDto() {
        RateAndOtherServiceDto expected = new RateAndOtherServiceDto(new BigDecimal("1"), BigDecimal.ZERO);
        ScoringDataDto testScoringDataDtoSelfEmployed = TestUtils.getScoringDataDtoMiddleManager();

        RateAndOtherServiceDto actual = softFilter.check(testScoringDataDtoSelfEmployed);

        assertThat(actual)
                .isEqualTo(expected);
    }

    @DisplayName("Test check ScoringDataDto with work status is BUSINESSMAN")
    @Test
    public void givenScoringDataDtoBusinessmanWorkStatus_whenCheck_thenExpectedEqualsActualRateAndOtherServiceDto() {
        RateAndOtherServiceDto expected = new RateAndOtherServiceDto(new BigDecimal("2"), BigDecimal.ZERO);
        ScoringDataDto testScoringDataDtoBusinessman = TestUtils.getScoringDataDtoOfTopManager();

        RateAndOtherServiceDto actual = softFilter.check(testScoringDataDtoBusinessman);

        assertThat(actual)
                .isEqualTo(expected);
    }

    @DisplayName("Test check ScoringDataDto with work status is UNKNOWN")
    @Test
    public void givenScoringDataDtoUnknownWorkStatus_whenCheck_thenExpectedEqualsActualRateAndOtherServiceDto() {
        RateAndOtherServiceDto expected = new RateAndOtherServiceDto(BigDecimal.ZERO, BigDecimal.ZERO);
        ScoringDataDto testScoringDataDtoHomeless = TestUtils.getScoringDataDtoHomeless();

        RateAndOtherServiceDto actual = softFilter.check(testScoringDataDtoHomeless);

        assertThat(actual)
                .isEqualTo(expected);
    }
}