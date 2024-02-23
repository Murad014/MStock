package com.mstockRestAPI.mstockRestAPI.service;

import com.mstockRestAPI.mstockRestAPI.dto.PaymentCustomerCreditDto;
import com.mstockRestAPI.mstockRestAPI.entity.PaymentCustomerCredit;

public interface PaymentCustomerCreditService {
    PaymentCustomerCreditDto add(Long creditId, PaymentCustomerCreditDto paymentCustomerCredit);
}
