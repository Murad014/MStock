package com.mstockRestAPI.mstockRestAPI.service.impl;

import com.mstockRestAPI.mstockRestAPI.dto.BankDto;
import com.mstockRestAPI.mstockRestAPI.entity.Bank;
import com.mstockRestAPI.mstockRestAPI.payload.converter.Converter;
import com.mstockRestAPI.mstockRestAPI.repository.BankRepository;
import com.mstockRestAPI.mstockRestAPI.service.BankService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BankServiceImpl implements BankService {

    private final BankRepository bankRepository;
    private final Converter converter;

    @Autowired
    public BankServiceImpl(BankRepository bankRepository, Converter converter){
        this.bankRepository = bankRepository;
        this.converter = converter;
    }

    @Override
    public List<BankDto> getAll() {
        List<Bank> bankList = bankRepository.findAll();
        return bankList.stream().map(bank ->
                converter.mapToDto(bank, BankDto.class)
        ).toList();
    }
}
