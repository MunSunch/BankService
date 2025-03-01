package com.munsun.calculator.config.annotations;

import com.munsun.calculator.config.validators.DiffPresentAndCurrentValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Target({ FIELD })
@Retention(RUNTIME)
@Constraint(validatedBy = DiffPresentAndCurrentValidator.class)
public @interface DiffPresentAndCurrent {
    String message() default "";
    Class<?>[] groups() default { };

    Class<? extends Payload>[] payload() default { };
}
