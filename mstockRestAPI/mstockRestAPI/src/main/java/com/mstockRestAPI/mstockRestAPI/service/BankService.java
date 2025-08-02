package com.mstockRestAPI.mstockRestAPI.service;

import com.mstockRestAPI.mstockRestAPI.dto.BankDto;

import java.util.List;

public interface BankService {
    List<BankDto> getAll();
}
