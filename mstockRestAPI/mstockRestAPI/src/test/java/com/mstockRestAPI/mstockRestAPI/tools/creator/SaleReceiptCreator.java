package com.mstockRestAPI.mstockRestAPI.tools.creator;

import com.mstockRestAPI.mstockRestAPI.dto.SaleReceiptDto;
import com.mstockRestAPI.mstockRestAPI.entity.PaymentExtraInfo;
import com.mstockRestAPI.mstockRestAPI.entity.SaleReceipt;
import com.mstockRestAPI.mstockRestAPI.enums.Currency;
import com.mstockRestAPI.mstockRestAPI.enums.PaymentType;
import com.mstockRestAPI.mstockRestAPI.tools.utils.Util;
import net.bytebuddy.utility.RandomString;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Stream;

public class SaleReceiptCreator {
    private static final int NUMBER_OF_RECEIPT = 10;
    private static final int PRODUCT_RANDOM_DESCRIPTION_LENGTH = 100;
    private static final String staticReceiptNumber = "14123123";

    public static SaleReceipt entity(){
        byte isActive = (byte) (Math.random() * 2);
        PaymentExtraInfo paymentExtraInfo = PaymentExtraInfoCreator.createEntity(
                BigDecimal.valueOf(23.23), // CashPay
                BigDecimal.valueOf(24.32), // cardPay
                null, // creditPay
                PaymentType.CASH_AND_CARD
        );

        return SaleReceipt.builder()
                .customer(CustomerCreator.entity())
                .number(staticReceiptNumber)
                .paymentExtraInfo(paymentExtraInfo)
                .currency(Util.chooseRandomEnum(Currency.class))
                .comment(RandomString.make(PRODUCT_RANDOM_DESCRIPTION_LENGTH))
                .isActive(isActive)
                .build();
    }

    public static SaleReceiptDto dto(){
        byte isActive = (byte) (Math.random() * 2);
        PaymentExtraInfo paymentExtraInfo = PaymentExtraInfoCreator.createEntity(
                BigDecimal.valueOf(23.23), // CashPay
                BigDecimal.valueOf(24.32), // cardPay
                null, // creditPay
                PaymentType.CASH_AND_CARD
        );

        return SaleReceiptDto.builder()
                .customer(CustomerCreator.entity())
                .number(staticReceiptNumber)
                .paymentExtraInfo(paymentExtraInfo)
                .currency(Util.chooseRandomEnum(Currency.class))
                .comment(RandomString.make(PRODUCT_RANDOM_DESCRIPTION_LENGTH))
                .isActive(isActive)
                .build();
    }

    public static List<SaleReceipt> entityList(){
        return Stream.generate(SaleReceiptCreator:: entity)
                .limit(NUMBER_OF_RECEIPT)
                .toList();
    }



}
