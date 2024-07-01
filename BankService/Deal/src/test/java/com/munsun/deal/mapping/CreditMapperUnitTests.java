package com.munsun.deal.mapping;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.munsun.deal.utils.TestUtils;
import com.munsun.deal.dto.response.CreditDto;
import com.munsun.deal.models.Credit;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;

public class CreditMapperUnitTests {
    private CreditMapper creditMapper = new CreditMapperImpl();

    @DisplayName("Test map CreditDto to Credit")
    @Test
    public void givenTestCreditDto_whenMapCreditDtoToCredit_thenEqualsTrueExpectedAndActualCredit() throws JsonProcessingException {
        CreditDto creditDto = TestUtils.getCreditDto();
        Credit expectedCredit = TestUtils.getCredit();

        Credit actualCredit = creditMapper.toCredit(creditDto);

        assertThat(actualCredit)
                .usingRecursiveComparison()
                .ignoringFields("creditId")
                .isEqualTo(expectedCredit);
    }

    @DisplayName("Test map Credit to CreditDto")
    @Test
    public void givenCreditPersistent_whenMapToCreditDto_thenReturnCreditDtoNotNull() {
        Credit creditPersistent = TestUtils.getCredit();

        CreditDto creditDto = creditMapper.toCreditDto(creditPersistent);

        assertThat(creditDto)
                .isNotNull();
    }
}
