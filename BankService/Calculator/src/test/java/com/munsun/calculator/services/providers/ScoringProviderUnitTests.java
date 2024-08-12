package com.munsun.calculator.services.providers;

import com.munsun.calculator.TestUtils;
import com.munsun.calculator.dto.request.ScoringDataDto;
import com.munsun.calculator.dto.utils.SimpleScoringInfoDto;
import com.munsun.calculator.exceptions.ScoringException;
import com.munsun.calculator.services.impl.providers.ScoringProvider;
import com.munsun.calculator.dto.utils.RateAndOtherServiceDto;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrowsExactly;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@SpringBootTest
public class ScoringProviderUnitTests {
    @Autowired
    private ScoringProvider scoringProvider;

    @DisplayName("Test success hard scoring ScoringDataDto")
    @Test
    public void givenScoringData_whenScoring_thenResultScoringPositive() {
        ScoringDataDto scoringDataDto = TestUtils.getScoringDataDto();

        boolean resultScoring = scoringProvider.hardScoring(scoringDataDto);

        assertThat(resultScoring)
                .isTrue();
    }

    @DisplayName("Test failed hard scoring ScoringDataDto with age less min age")
    @Test
    public void givenScoringDataWithAgeLessMinAge_whenScoring_thenResultScoringFalse() {
        ScoringDataDto scoringDataDto = TestUtils.getScoringDataDtoLessMinAge();

        boolean resultScoring = scoringProvider.hardScoring(scoringDataDto);

        assertThat(resultScoring)
                .isFalse();
    }

    @DisplayName("Test failed hard scoring ScoringDataDto with age greater max age")
    @Test
    public void givenScoringDataWithAgeGreaterMaxAge_whenScoring_thenResultScoringFalse() {
        ScoringDataDto scoringDataDto = TestUtils.getScoringDataDtoGreaterMaxAge();

        boolean resultScoring = scoringProvider.hardScoring(scoringDataDto);

        assertThat(resultScoring)
                .isFalse();
    }

    @DisplayName("Test failed hard scoring ScoringDataDto because work status=unemployed")
    @Test
    public void givenScoringDataWithGreaterMaxAge_whenScoring_thenResultScoringFalse() {
        ScoringDataDto scoringDataDto = TestUtils.getScoringDataDtoUnemployedWorkStatus();

        boolean resultScoring = scoringProvider.hardScoring(scoringDataDto);

        assertThat(resultScoring)
                .isFalse();
    }

    @DisplayName("Test failed hard scoring ScoringDataDto because total work experience<18")
    @Test
    public void givenScoringDataWithLessTotalWorkExperience_whenScoring_thenResultScoringFalse() {
        ScoringDataDto scoringDataDto = TestUtils.getScoringDataDtoLessTotalWorkExperience();

        boolean resultScoring = scoringProvider.hardScoring(scoringDataDto);

        assertThat(resultScoring)
                .isFalse();
    }

    @DisplayName("Test failed hard scoring ScoringDataDto because current work experience<3")
    @Test
    public void givenScoringDataWithLessCurrentWorkExperience_whenScoring_thenResultScoringFalse() {
        ScoringDataDto scoringDataDto = TestUtils.getScoringDataDtoLessCurrentWorkExperience();

        boolean resultScoring = scoringProvider.hardScoring(scoringDataDto);

        assertThat(resultScoring)
                .isFalse();
    }

    @DisplayName("Test failed hard scoring ScoringDataDto because amount more than 25 salaries")
    @Test
    public void givenScoringDataWithAmountGreater25Salaries_whenScoring_thenResultScoringFalse() {
        ScoringDataDto scoringDataDto = TestUtils.getScoringDataDtoLessAmount();

        boolean resultScoring = scoringProvider.hardScoring(scoringDataDto);

        assertThat(resultScoring)
                .isFalse();
    }

    @DisplayName("Test soft scoring ScoringDataDto a divorced man of 35 years old who holds the position of a top manager and has his own business")
    @Test
    public void givenScoringDataTopManagerAndOriginalRate_whenScoring_thenResultChangeOriginalRateAndZeroOtherService() {
        ScoringDataDto scoringDataDto = TestUtils.getScoringDataDtoOfTopManager();
        BigDecimal rate = new BigDecimal("20");
        BigDecimal expectedRate = new BigDecimal("17");

        RateAndOtherServiceDto result = scoringProvider.softScoring(scoringDataDto, rate);

        assertThat(result.newRate())
                .isEqualTo(expectedRate);
        assertThat(result.otherService())
                .isEqualTo(BigDecimal.ZERO);
    }

    @DisplayName("Test soft scoring ScoringDataDto married woman of 20 years, holding the position of manager, self-employed")
    @Test
    public void givenScoringDataMiddleManagerAndOriginalRate_whenScoring_thenResultChangeOriginalRateAndZeroOtherService() {
        ScoringDataDto scoringDataDto = TestUtils.getScoringDataDtoMiddleManager();
        BigDecimal rate = new BigDecimal("20");
        BigDecimal expectedRate = new BigDecimal("16"); //-3-2+1

        RateAndOtherServiceDto result = scoringProvider.softScoring(scoringDataDto, rate);

        assertThat(result.newRate())
                .isEqualTo(expectedRate);
        assertThat(result.otherService())
                .isEqualTo(BigDecimal.ZERO);
    }

    @DisplayName("Test soft scoring ScoringDataDto married woman of 20 years, holding the position of manager, self-employed, salaries client, insurance enable")
    @Test
    public void givenScoringDataSalariesClientWithInsurance_whenScoring_thenResultChangeRateAndOtherService() {
        ScoringDataDto scoringDataDto = TestUtils.getScoringDataDtoMiddleManagerWithInsurance();
        BigDecimal rate = new BigDecimal("20");
        BigDecimal expectedRate = new BigDecimal("13"); //-3-2+1-1-2

        RateAndOtherServiceDto result = scoringProvider.softScoring(scoringDataDto, rate);

        assertThat(result.newRate())
                .isEqualTo(expectedRate);
        assertThat(result.otherService())
                .isNotEqualTo(BigDecimal.ZERO);
    }

    @DisplayName("Test full scoring ScoringDataDto")
    @Test
    public void givenScoringDataAndOriginalRate_whenScoring_thenResultChangeOriginalRateAndZeroOtherService() {
        ScoringDataDto scoringDataDto = TestUtils.getScoringDataDto();
        BigDecimal rate = new BigDecimal("20");
        BigDecimal expectedRate = new BigDecimal("20"); //-3-2+1

        RateAndOtherServiceDto result = scoringProvider.softScoring(scoringDataDto, rate);

        assertThat(result.newRate())
                .isEqualTo(expectedRate);
        assertThat(result.otherService())
                .isEqualTo(BigDecimal.ZERO);
    }

    @DisplayName("Test thrown ScoringException when called scoring()")
    @Test
    public void givenScoringReturnFalse_whenCalculateCredit_thenThrownScoringException() {
        assertThrowsExactly(ScoringException.class, () -> {
            scoringProvider.fullScoring(TestUtils.getScoringDataDtoLessAmount());
            scoringProvider.fullScoring(TestUtils.getScoringDataDtoLessMinAge());
            scoringProvider.fullScoring(TestUtils.getScoringDataDtoGreaterMaxAge());
            scoringProvider.fullScoring(TestUtils.getScoringDataDtoLessCurrentWorkExperience());
            scoringProvider.fullScoring(TestUtils.getScoringDataDtoLessTotalWorkExperience());
            scoringProvider.fullScoring(TestUtils.getScoringDataDtoUnemployedWorkStatus());
        });
    }

    @DisplayName("Test simple scoring for loan offer")
    @Test
    public void givenScoringInfoWithSalaryAndInsurance_whenSimpleScoring_thenReturnListSimpleSoringInfoSize4() {
        List<SimpleScoringInfoDto> expected = TestUtils.getSimpleScoringInfoDto();

        var actual = scoringProvider.simpleScoring();

        assertThat(actual)
                .isNotNull()
                .hasSize(expected.size())
                .hasSameElementsAs(expected);
    }
}