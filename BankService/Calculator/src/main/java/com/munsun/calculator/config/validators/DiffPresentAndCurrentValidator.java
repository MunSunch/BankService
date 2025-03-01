package com.munsun.calculator.config.validators;

import com.munsun.calculator.config.annotations.DiffPresentAndCurrent;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.time.LocalDate;

@Component
public class DiffPresentAndCurrentValidator implements ConstraintValidator<DiffPresentAndCurrent, LocalDate> {
    @Value("${prescoring.min_age}")
    private int minAge;

    @Override
    public boolean isValid(LocalDate current, ConstraintValidatorContext constraintValidatorContext) {
        var now = LocalDate.now();
        return now.minusYears(minAge).isAfter(current);
    }
}