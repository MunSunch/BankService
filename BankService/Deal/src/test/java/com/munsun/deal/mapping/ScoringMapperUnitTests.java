package com.munsun.deal.mapping;

import com.munsun.deal.utils.TestUtils;
import com.munsun.deal.dto.FinishRegistrationRequestDto;
import com.munsun.deal.dto.ScoringDataDto;
import com.munsun.deal.models.Statement;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(classes = {ScoringMapperImpl.class})
public class ScoringMapperUnitTests {
    @Autowired
    private ScoringMapper mapper;

    @DisplayName("Test map Statement and FinishRegistrationDto to ScoringDataDto")
    @Test
    public void givenStatementAndFinishRegistrationRequestDto_whenMapToScoringDataDto_thenEqualsTrueExpectedAndActualScoringDataDto() {
        Statement statement = TestUtils.getStatementPersistent();
        FinishRegistrationRequestDto finishRegistration = TestUtils.getFinishRegistrationRequestDto();
        ScoringDataDto expectedScoringDataDto = TestUtils.getScoringDataDto();

        ScoringDataDto actualScoringDataDto = mapper.toScoringDataDto(statement, finishRegistration);

        assertThat(actualScoringDataDto)
                .usingRecursiveComparison()
                .isEqualTo(expectedScoringDataDto);
    }
}