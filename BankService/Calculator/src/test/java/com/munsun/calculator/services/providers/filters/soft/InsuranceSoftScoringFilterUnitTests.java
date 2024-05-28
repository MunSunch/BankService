package com.munsun.calculator.services.providers.filters.soft;

import com.munsun.calculator.TestUtils;
import com.munsun.calculator.dto.request.ScoringDataDto;
import com.munsun.calculator.dto.utils.RateAndOtherServiceDto;
import com.munsun.calculator.services.impl.providers.impl.filters.impl.soft.InsuranceSoftScoringFilter;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
public class InsuranceSoftScoringFilterUnitTests {
    @Autowired
    private InsuranceSoftScoringFilter softScoringFilter;

    @DisplayName("Test accrual of insurance on credit")
    @Test
    public void givenScoringDataDtoWithInsuranceEnabled_whenCheck_thenFilterReturnInsuranceAndChangeRate() {
        ScoringDataDto testScoringDataDtoWithInsurance = TestUtils.getScoringDataDtoMiddleManagerWithInsurance();
        RateAndOtherServiceDto expected = new RateAndOtherServiceDto(new BigDecimal("-1"), new BigDecimal("10000"));

        RateAndOtherServiceDto actual = softScoringFilter.check(testScoringDataDtoWithInsurance);

        assertThat(actual)
                .isEqualTo(expected);
    }

    @DisplayName("Negative test accrual of insurance on credit")
    @Test
    public void givenScoringDataDtoWithoutInsuranceEnabled_whenCheck_thenFilterReturnInsuranceZeroAndOriginalRate() {
        ScoringDataDto testScoringDataDtoWithoutInsurance = TestUtils.getScoringDataDtoMiddleManager();
        RateAndOtherServiceDto expected = new RateAndOtherServiceDto(BigDecimal.ZERO, BigDecimal.ZERO);

        RateAndOtherServiceDto actual = softScoringFilter.check(testScoringDataDtoWithoutInsurance);

        assertThat(actual)
                .isEqualTo(expected);
    }
}
