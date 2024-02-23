package com.mstockRestAPI.mstockRestAPI.validation.impl;

import com.mstockRestAPI.mstockRestAPI.exception.SomethingWentWrongException;
import com.mstockRestAPI.mstockRestAPI.repository.BankCardAccountRepository;
import com.mstockRestAPI.mstockRestAPI.utils.Util;
import com.mstockRestAPI.mstockRestAPI.validation.BankAccountNumber;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.springframework.beans.factory.annotation.Autowired;

public class BankAccountNumberValidator implements ConstraintValidator<BankAccountNumber, String> {
    private final BankCardAccountRepository bankCardAccountRepository;

    @Autowired
    public BankAccountNumberValidator(
            BankCardAccountRepository bankCardAccountRepository
    ){
        this.bankCardAccountRepository = bankCardAccountRepository;
    }

    @Override
    public void initialize(BankAccountNumber constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
    }

    @Override
    public boolean isValid(String bankAccountNumber, ConstraintValidatorContext constraintValidatorContext) {
        String beautifulAccountNumber = Util.makeBankCardNumberBeautiful(bankAccountNumber);
        boolean exist = bankCardAccountRepository.existsByAccountNumber(beautifulAccountNumber);

        return Util.isValidBankAccountNumber(bankAccountNumber) && !exist;
    }
}
