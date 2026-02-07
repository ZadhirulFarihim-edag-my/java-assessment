package com.farihim.assessment.demo.validator;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.util.Optional;

public class ValidStringValidator implements ConstraintValidator<ValidString, String> {

    private ValidString.ValidationType validationType;

    @Override
    public void initialize(ValidString constraintAnnotation) {
        this.validationType = constraintAnnotation.type();
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        return switch (validationType) {
            case NOT_NULL -> Optional.ofNullable(value).isPresent();
            case NOT_BLANK -> Optional.ofNullable(value)
                    .filter(v -> !v.isBlank())
                    .isPresent();
        };
    }
}