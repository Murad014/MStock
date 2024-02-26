package com.mstockRestAPI.mstockRestAPI.tools.creator;

import com.mstockRestAPI.mstockRestAPI.dto.PaymentInvoiceDto;
import com.mstockRestAPI.mstockRestAPI.entity.PaymentExtraInfo;
import com.mstockRestAPI.mstockRestAPI.entity.PaymentInvoice;
import com.mstockRestAPI.mstockRestAPI.enums.PaymentType;
import com.mstockRestAPI.mstockRestAPI.tools.utils.Util;

import java.math.BigDecimal;

public class PaymentInvoiceCreator {

    public static PaymentInvoice entity(){
        return PaymentInvoice.builder()
                .invoice(InvoiceCreator.entity())
                .plusPay(
                        BigDecimal.valueOf(Util.generateRandomPrice(1, 200)))
                .paymentExtraInfo(PaymentExtraInfoCreator.createEntity(
                        BigDecimal.valueOf(23.23), // CashPay
                        BigDecimal.valueOf(24.32), // cardPay
                        null, // creditPay
                        PaymentType.CASH_AND_CARD
                ))
                .bankCardAccount(BankCardAccountCreator.entity())
                .build();
    }

    public static PaymentInvoiceDto dto(){
        return PaymentInvoiceDto.builder()
                .plusPay(
                        BigDecimal.valueOf(Util.generateRandomPrice(1, 200)))
                .paymentExtraInfo(PaymentExtraInfoCreator.createDto(
                        BigDecimal.valueOf(23.23), // CashPay
                        BigDecimal.valueOf(24.32), // cardPay
                        null, // creditPay
                        PaymentType.CASH_AND_CARD
                ))
                .build();
    }




}
