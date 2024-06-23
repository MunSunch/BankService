package com.munsun.calculator.dto.request.annotations;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.time.LocalDate;

public class DiffPresentAndCurrentValidator implements ConstraintValidator<DiffPresentAndCurrent, LocalDate> {
    private static final int DIFFERENCE_VALUE=18;
    @Override
    public boolean isValid(LocalDate current, ConstraintValidatorContext constraintValidatorContext) {
        LocalDate now = LocalDate.now();
        return current.plusYears(DIFFERENCE_VALUE).isBefore(now);
    }
}
