package com.munsun.calculator.services.providers.filters.soft;

import com.munsun.calculator.TestUtils;
import com.munsun.calculator.dto.request.ScoringDataDto;
import com.munsun.calculator.dto.utils.RateAndOtherServiceDto;
import com.munsun.calculator.services.impl.providers.impl.filters.impl.soft.WorkPositionSoftScoringFilter;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
public class WorkPositionSoftScoringFilterUnitTests {
    @Autowired
    private WorkPositionSoftScoringFilter softScoringFilter;

    @DisplayName("Test check ScoringDataDto with work position is MIDDLE_MANAGER")
    @Test
    public void givenScoringDataDtoMiddleManagerPosition_whenCheck_thenExpectedEqualsActualRateAndOtherServiceDto() {
        RateAndOtherServiceDto expected = new RateAndOtherServiceDto(new BigDecimal("-2"), BigDecimal.ZERO);
        ScoringDataDto testScoringDataDtoMiddleManager = TestUtils.getScoringDataDtoMiddleManager();

        RateAndOtherServiceDto actual = softScoringFilter.check(testScoringDataDtoMiddleManager);

        assertThat(actual)
                .isEqualTo(expected);
    }

    @DisplayName("Test check ScoringDataDto with work position is TOP_MANAGER")
    @Test
    public void givenScoringDataDtoTopManagerPosition_whenCheck_thenExpectedEqualsActualRateAndOtherServiceDto() {
        RateAndOtherServiceDto expected = new RateAndOtherServiceDto(new BigDecimal("-3"), BigDecimal.ZERO);
        ScoringDataDto testScoringDataDtoTopManager = TestUtils.getScoringDataDtoOfTopManager();

        RateAndOtherServiceDto actual = softScoringFilter.check(testScoringDataDtoTopManager);

        assertThat(actual)
                .isEqualTo(expected);
    }
}
