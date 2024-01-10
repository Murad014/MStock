package com.mstockRestAPI.mstockRestAPI.validation.impl;

import com.mstockRestAPI.mstockRestAPI.repository.CompanyRepository;
import com.mstockRestAPI.mstockRestAPI.service.CompanyService;
import com.mstockRestAPI.mstockRestAPI.validation.UniqueCompanyName;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import jakarta.validation.Payload;

import java.lang.annotation.Annotation;

public class UniqueCompanyNameValidator implements ConstraintValidator<UniqueCompanyName, String> {
    private final CompanyService companyService;

    public UniqueCompanyNameValidator(CompanyService companyService){
        this.companyService = companyService;
    }

    @Override
    public void initialize(UniqueCompanyName constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
    }

    @Override
    public boolean isValid(String s, ConstraintValidatorContext constraintValidatorContext) {
        boolean existSameCompanyName = companyService.existsByCompanyName(s);
        return !existSameCompanyName;
    }
}
