package com.farihim.assessment.demo.validator;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.FIELD, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = ValidStringValidator.class)
public @interface ValidString {

    String message() default "Field is required";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

    ValidationType type() default ValidationType.NOT_BLANK;

    enum ValidationType {
        NOT_NULL,
        NOT_BLANK
    }
}
