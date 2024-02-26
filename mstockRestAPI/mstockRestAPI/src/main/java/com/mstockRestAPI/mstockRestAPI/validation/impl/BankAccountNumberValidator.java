package com.mstockRestAPI.mstockRestAPI.validation.impl;

import com.mstockRestAPI.mstockRestAPI.exception.SomethingWentWrongException;
import com.mstockRestAPI.mstockRestAPI.repository.BankCardAccountRepository;
import com.mstockRestAPI.mstockRestAPI.service.BankCardAccountService;
import com.mstockRestAPI.mstockRestAPI.utils.Util;
import com.mstockRestAPI.mstockRestAPI.validation.BankAccountNumber;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.springframework.beans.factory.annotation.Autowired;

public class BankAccountNumberValidator implements ConstraintValidator<BankAccountNumber, String> {
    private final BankCardAccountService bankCardAccountService;


    public BankAccountNumberValidator(
            BankCardAccountService bankCardAccountService
    ){
        this.bankCardAccountService = bankCardAccountService;
    }

    @Override
    public void initialize(BankAccountNumber constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
    }

    @Override
    public boolean isValid(String bankAccountNumber, ConstraintValidatorContext constraintValidatorContext) {
        String beautifulAccountNumber = Util.makeBankCardNumberBeautiful(bankAccountNumber);
        boolean exist = bankCardAccountService.existsByAccountNumber(beautifulAccountNumber);

        return Util.isValidBankAccountNumber(bankAccountNumber) && !exist;
    }
}
