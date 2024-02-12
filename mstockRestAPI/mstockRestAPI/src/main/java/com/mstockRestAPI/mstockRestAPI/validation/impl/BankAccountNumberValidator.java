package com.mstockRestAPI.mstockRestAPI.validation.impl;

import com.mstockRestAPI.mstockRestAPI.exception.SomethingWentWrongException;
import com.mstockRestAPI.mstockRestAPI.utils.Util;
import com.mstockRestAPI.mstockRestAPI.validation.BankAccountNumber;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class BankAccountNumberValidator implements ConstraintValidator<BankAccountNumber, String> {

    @Override
    public void initialize(BankAccountNumber constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
    }

    @Override
    public boolean isValid(String bankAccountNumber, ConstraintValidatorContext constraintValidatorContext) {
        return Util.isValidBankAccountNumber(bankAccountNumber);
    }
}
