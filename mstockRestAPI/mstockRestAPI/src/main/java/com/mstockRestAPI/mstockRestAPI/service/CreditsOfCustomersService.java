package com.mstockRestAPI.mstockRestAPI.service;

import com.mstockRestAPI.mstockRestAPI.dto.CreditOfCustomersDto;
import com.mstockRestAPI.mstockRestAPI.entity.CreditOfCustomers;

public interface CreditsOfCustomersService {

    CreditOfCustomersDto addByCustomerIdCardNumber(CreditOfCustomersDto creditOfCustomersDto,
                                                   String customerIdCardNumber);

    CreditOfCustomersDto addByCustomerId(CreditOfCustomersDto creditOfCustomersDto,
                                                   Long customerId);

    Boolean closeCreditByCustomerIdCardNumberAndCreditId(Long creditId);

    CreditOfCustomersDto fetchById(Long creditId);



}
