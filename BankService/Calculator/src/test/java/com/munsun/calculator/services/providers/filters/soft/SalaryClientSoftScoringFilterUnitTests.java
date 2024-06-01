package com.munsun.calculator.services.providers.filters.soft;

import com.munsun.calculator.TestUtils;
import com.munsun.calculator.dto.request.ScoringDataDto;
import com.munsun.calculator.dto.utils.RateAndOtherServiceDto;
import com.munsun.calculator.services.impl.providers.impl.filters.ScoringLoanFilter;
import com.munsun.calculator.services.impl.providers.impl.filters.impl.soft.SalaryClientSoftScoringFilter;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
public class SalaryClientSoftScoringFilterUnitTests {
    @Autowired
    private SalaryClientSoftScoringFilter softScoringFilter;

    @DisplayName("Test change rate of salary client")
    @Test
    public void givenScoringDataDtoWithSalaryClientStatus_whenCheck_thenChangeRateAndInsuranceZero() {
        ScoringDataDto testScoringData = TestUtils.getScoringDataDtoMiddleManagerWithInsurance();
        RateAndOtherServiceDto expected = new RateAndOtherServiceDto(new BigDecimal("-2"), BigDecimal.ZERO);

        RateAndOtherServiceDto actual = softScoringFilter.check(testScoringData);

        assertThat(actual)
                .isEqualTo(expected);
    }

    @DisplayName("Test change rate of not salary client")
    @Test
    public void givenScoringDataDtoWithoutSalaryClientStatus_whenCheck_thenRateZeroAndInsuranceZero() {
        ScoringDataDto testScoringData = TestUtils.getScoringDataDtoMiddleManager();
        RateAndOtherServiceDto expected = new RateAndOtherServiceDto(BigDecimal.ZERO, BigDecimal.ZERO);

        RateAndOtherServiceDto actual = softScoringFilter.check(testScoringData);

        assertThat(actual)
                .isEqualTo(expected);
    }

    @DisplayName("Test check loanStatement for loan offer, value=true")
    @Test
    public void givenBooleanValueTrue_whenCheck_thenReturnInsuranceZeroAndChangeRate() {
        RateAndOtherServiceDto expected = new RateAndOtherServiceDto(new BigDecimal("-2"), BigDecimal.ZERO);

        var actual = ((ScoringLoanFilter)softScoringFilter).check(true);

        assertThat(actual)
                .isEqualTo(expected);
    }

    @DisplayName("Test check loanStatement for loan offer, value=false")
    @Test
    public void givenBooleanValueFalse_whenCheck_thenReturnInsuranceZeroAndChangeRateZero() {
        RateAndOtherServiceDto expected = new RateAndOtherServiceDto(BigDecimal.ZERO, BigDecimal.ZERO);

        var actual = ((ScoringLoanFilter)softScoringFilter).check(false);

        assertThat(actual)
                .isEqualTo(expected);
    }
}
