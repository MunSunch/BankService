package com.munsun.deal.dto.request.annotations;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Target({ FIELD })
@Retention(RUNTIME)
@Constraint(validatedBy = DiffPresentAndCurrentValidator.class)
public @interface DiffPresentAndCurrent {
    String message() default "";
    int years();
    Class<?>[] groups() default { };

    Class<? extends Payload>[] payload() default { };
}