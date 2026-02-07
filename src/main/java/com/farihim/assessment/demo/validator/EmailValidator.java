package com.farihim.assessment.demo.validator;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.util.Optional;
import java.util.regex.Pattern;

public class EmailValidator implements ConstraintValidator<ValidEmail, String> {

    private static final Pattern EMAIL_PATTERN = Pattern.compile(
            "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$"
    );

    @Override
    public boolean isValid(String email, ConstraintValidatorContext context) {
        return Optional.ofNullable(email)
                .filter(e -> !e.isBlank())
                .map(e -> EMAIL_PATTERN.matcher(e).matches())
                .orElse(false);
    }
}