package com.mstockRestAPI.mstockRestAPI.tools.creator;

import com.mstockRestAPI.mstockRestAPI.dto.BankCardAccountDto;
import com.mstockRestAPI.mstockRestAPI.dto.PaymentExtraInfoDto;
import com.mstockRestAPI.mstockRestAPI.entity.BankCardAccount;
import com.mstockRestAPI.mstockRestAPI.entity.PaymentExtraInfo;
import com.mstockRestAPI.mstockRestAPI.enums.PaymentType;

import java.math.BigDecimal;

public class PaymentExtraInfoCreator {

    public static PaymentExtraInfo createEntity(BigDecimal cashPay,
                                          BigDecimal cardPay,
                                          BigDecimal creditPay,
                                          PaymentType paymentType){

        BankCardAccount bankCardAccount = BankCardAccountCreator.entity();

        return PaymentExtraInfo.builder()
                .paymentType(paymentType)
                .cashPay(cashPay)
                .cardPay(cardPay)
                .bankAccountNumber(bankCardAccount)
                .creditPay(creditPay)
                .build();
    }

    public static PaymentExtraInfoDto createDto(BigDecimal cashPay,
                                                BigDecimal cardPay,
                                                BigDecimal creditPay,
                                                PaymentType paymentType){

        BankCardAccountDto bankCardAccount = BankCardAccountCreator.dto();

        return PaymentExtraInfoDto.builder()
                .paymentType(paymentType)
                .cashPay(cashPay)
                .cardPay(cardPay)
                .bankAccountNumber(bankCardAccount)
                .creditPay(creditPay)
                .build();
    }



}
