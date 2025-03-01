package com.munsun.calculator.config.validator;

import com.munsun.calculator.config.validators.DiffPresentAndCurrentValidator;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(classes = DiffPresentAndCurrentValidator.class)
public class ValidatorCustomAnnotationsUnitTests {
    @Value("${prescoring.min_age}")
    private int value;

    @Autowired
    private DiffPresentAndCurrentValidator validator;

    @Test
    public void givenInvalidLocalDate_whenIsValid_thenFalse() {
        var invalidLocalDate = LocalDate.now();
        var expected = false;

        var actual = validator.isValid(invalidLocalDate, null);

        assertThat(actual)
                .isEqualTo(expected);
    }

    @Test
    public void givenValidLocalDate_whenIsValid_thenTrue() {
        var invalidLocalDate = LocalDate.now()
                .minusYears(value)
                .minusYears(5);
        var expected = true;

        var actual = validator.isValid(invalidLocalDate, null);

        assertThat(actual)
                .isEqualTo(expected);
    }
}
