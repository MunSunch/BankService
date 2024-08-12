package com.munsun.calculator.services.providers.filters.hard;

import com.munsun.calculator.TestUtils;
import com.munsun.calculator.dto.request.ScoringDataDto;
import com.munsun.calculator.services.impl.providers.impl.filters.impl.hard.AgeHardScoringFilter;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
public class AgeHardScoringFilterUnitTests {
    @Autowired
    private AgeHardScoringFilter scoringFilter;

    @DisplayName("Test check scoring data with age=9 less min age=20")
    @Test
    public void givenScoringDataDtoWithLessMinAge_whenCheck_thenFilterReturnFalse() {
        ScoringDataDto testScoringData = TestUtils.getScoringDataDtoLessMinAge();
        boolean expected = false;

        boolean actual = scoringFilter.check(testScoringData);

        assertEquals(expected, actual);
    }

    @DisplayName("Test check scoring data with age=9 less min age=65")
    @Test
    public void givenScoringDataDtoWithGreaterMaxAge_whenCheck_thenFilterReturnFalse() {
        ScoringDataDto testScoringData = TestUtils.getScoringDataDtoGreaterMaxAge();
        boolean expected = false;

        boolean actual = scoringFilter.check(testScoringData);

        assertEquals(expected, actual);
    }

    @DisplayName("Test check scoring data with age=36")
    @Test
    public void givenScoringDataDtoWithNormalAge_whenCheck_thenFilterReturnTrue() {
        ScoringDataDto testScoringData = TestUtils.getScoringDataDtoUnemployedWorkStatus();
        boolean expected = true;

        boolean actual = scoringFilter.check(testScoringData);

        assertEquals(expected, actual);
    }
}
