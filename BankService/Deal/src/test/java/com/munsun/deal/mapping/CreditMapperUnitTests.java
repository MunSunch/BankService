package com.munsun.deal.mapping;

import com.munsun.deal.utils.TestUtils;
import com.munsun.deal.dto.response.CreditDto;
import com.munsun.deal.models.Credit;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
public class CreditMapperUnitTests {
    @Autowired
    private CreditMapper creditMapper;

    @DisplayName("Test map CreditDto to Credit")
    @Test
    public void givenTestCreditDto_whenMapCreditDtoToCredit_thenEqualsTrueExpectedAndActualCredit() {
        CreditDto creditDto = TestUtils.getCreditDto();
        Credit expectedCredit = TestUtils.getCredit();

        Credit actualCredit = creditMapper.toCredit(creditDto);

        assertThat(actualCredit)
                .usingRecursiveComparison()
                .ignoringFields("creditId")
                .isEqualTo(expectedCredit);
    }
}
