package com.mstockRestAPI.mstockRestAPI.service.impl;

import com.mstockRestAPI.mstockRestAPI.dto.BankCardAccountDto;
import com.mstockRestAPI.mstockRestAPI.entity.BankCardAccount;
import com.mstockRestAPI.mstockRestAPI.exception.ResourceNotFoundException;
import com.mstockRestAPI.mstockRestAPI.payload.converter.Converter;
import com.mstockRestAPI.mstockRestAPI.repository.BankCardAccountRepository;
import com.mstockRestAPI.mstockRestAPI.service.BankCardAccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class BankCardAccountServiceImpl implements BankCardAccountService {

    private final BankCardAccountRepository bankCardAccountRepository;
    private final Converter converter;

    @Autowired
    public BankCardAccountServiceImpl(BankCardAccountRepository bankCardAccountRepository,
                                      Converter converter){
        this.bankCardAccountRepository = bankCardAccountRepository;
        this.converter = converter;
    }

    @Override
    public BankCardAccountDto add(BankCardAccountDto bankCardAccountDto) {
        BankCardAccount saved = bankCardAccountRepository.save(
                converter.mapToEntity(bankCardAccountDto, BankCardAccount.class)
        );

        return converter.mapToDto(saved, BankCardAccountDto.class);
    }

    @Override
    public Boolean makeDeActive(String bankAccountNumber) {
        BankCardAccount fromDB = bankCardAccountRepository.findById(bankAccountNumber).orElseThrow(
                () -> new ResourceNotFoundException("BankCardAccount", "id", bankAccountNumber)
        );

        fromDB.setIsActive((byte)0);
        bankCardAccountRepository.save(fromDB);

        return true;
    }

    @Override
    public Boolean existsByAccountNumber(String accountNumber) {
        return bankCardAccountRepository.existsByAccountNumber(accountNumber);
    }


}
