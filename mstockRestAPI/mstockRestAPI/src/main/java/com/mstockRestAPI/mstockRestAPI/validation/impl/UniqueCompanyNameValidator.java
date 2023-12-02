package com.mstockRestAPI.mstockRestAPI.validation.impl;

import com.mstockRestAPI.mstockRestAPI.validation.UniqueCompanyName;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import jakarta.validation.Payload;

import java.lang.annotation.Annotation;

public class UniqueCompanyNameValidator implements ConstraintValidator<UniqueCompanyName, String> {

    @Override
    public void initialize(UniqueCompanyName constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
    }

    @Override
    public boolean isValid(String s, ConstraintValidatorContext constraintValidatorContext) {
        return false;
    }
}
