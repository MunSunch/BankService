package com.munsun.calculator.services.providers.filters.hard;

import com.munsun.calculator.TestUtils;
import com.munsun.calculator.dto.request.ScoringDataDto;
import com.munsun.calculator.services.impl.providers.impl.filters.impl.hard.WorkExperienceHardScoringFilter;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
public class WorkExperienceHardScoringFilterUnitTests {
    @Autowired
    private WorkExperienceHardScoringFilter scoringFilter;

    @DisplayName("Test check scoring data with total work experience=6")
    @Test
    public void givenScoringDataDtoWithLessTotalWorkExp_whenCheck_thenReturnFalse() {
        ScoringDataDto testScoringData = TestUtils.getScoringDataDtoLessTotalWorkExperience();
        boolean expected = false;

        boolean actual = scoringFilter.check(testScoringData);

        assertEquals(expected, actual);
    }

    @DisplayName("Test check scoring data with current work experience=3")
    @Test
    public void givenScoringDataDtoWithLessCurrentWorkExp_whenCheck_thenReturnFalse() {
        ScoringDataDto testScoringData = TestUtils.getScoringDataDtoLessCurrentWorkExperience();
        boolean expected = false;

        boolean actual = scoringFilter.check(testScoringData);

        assertEquals(expected, actual);
    }

    @DisplayName("Test check scoring data with current work experience=4, total experience=19")
    @Test
    public void givenScoringDataDtoWith_whenCheck_thenReturnTrue() {
        ScoringDataDto testScoringData = TestUtils.getScoringDataDto();
        boolean expected = true;

        boolean actual = scoringFilter.check(testScoringData);

        assertEquals(expected, actual);
    }
}
