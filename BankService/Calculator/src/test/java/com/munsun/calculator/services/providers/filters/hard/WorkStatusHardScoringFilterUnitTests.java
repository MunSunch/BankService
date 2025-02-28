package com.munsun.calculator.services.providers.filters.hard;

import com.munsun.calculator.utils.TestUtils;
import com.munsun.calculator.dto.request.ScoringDataDto;
import com.munsun.calculator.services.impl.providers.impl.filters.impl.hard.WorkStatusHardScoringFilter;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
public class WorkStatusHardScoringFilterUnitTests {
    @Autowired
    private WorkStatusHardScoringFilter scoringFilter;

    @DisplayName("Test check scoring data with work status=SELF_EMPLOYED")
    @Test
    public void givenScoringDataDtoWithSelfEmployedWorkStatus_whenCheck_thenReturnTrue() {
        ScoringDataDto testScoringData = TestUtils.getScoringDataDto();
        boolean expected = true;

        boolean actual = scoringFilter.check(testScoringData);

        assertEquals(expected, actual);
    }

    @DisplayName("Test check scoring data with work status=UNEMPLOYED")
    @Test
    public void givenScoringDataDtoWithUnemployedWorkStatus_whenCheck_thenReturnFalse() {
        ScoringDataDto testScoringData = TestUtils.getScoringDataDtoUnemployedWorkStatus();
        boolean expected = false;

        boolean actual = scoringFilter.check(testScoringData);

        assertEquals(expected, actual);
    }
}
