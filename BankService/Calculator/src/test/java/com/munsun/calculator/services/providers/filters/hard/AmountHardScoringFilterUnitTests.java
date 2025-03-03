package com.munsun.calculator.services.providers.filters.hard;

import com.munsun.calculator.dto.ScoringDataDto;
import com.munsun.calculator.utils.TestUtils;
import com.munsun.calculator.services.impl.providers.impl.filters.impl.hard.AmountHardScoringFilter;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
public class AmountHardScoringFilterUnitTests {
    @Autowired
    private AmountHardScoringFilter scoringFilter;

    @DisplayName("Test check scoring data with salary=10_000 and amount=10_000_000")
    @Test
    public void givenScoringDataDtoBigDifferenceBetweenSalaryAndAmount_whenCheck_thenReturnFalse() {
        ScoringDataDto testScoringData = TestUtils.getScoringDataDtoLessAmount();
        boolean expected = false;

        boolean actual = scoringFilter.check(testScoringData);

        assertEquals(expected, actual);
    }

    @DisplayName("Test check scoring data with salary=41_000 and amount=10_000")
    @Test
    public void givenScoringDataDtoSmallDifferenceBetweenSalaryAndAmount_whenCheck_thenReturnTrue() {
        ScoringDataDto testScoringData = TestUtils.getScoringDataDto();
        boolean expected = true;

        boolean actual = scoringFilter.check(testScoringData);

        assertEquals(expected, actual);
    }
}
