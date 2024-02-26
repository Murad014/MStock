package com.mstockRestAPI.mstockRestAPI.service;

import com.mstockRestAPI.mstockRestAPI.dto.BankCardAccountDto;
import com.mstockRestAPI.mstockRestAPI.entity.BankCardAccount;

public interface BankCardAccountService {
    BankCardAccountDto add(BankCardAccountDto bankCardAccountDto);
    Boolean makeDeActive(String bankAccountNumber);

    Boolean existsByAccountNumber(String accountNumber);
}