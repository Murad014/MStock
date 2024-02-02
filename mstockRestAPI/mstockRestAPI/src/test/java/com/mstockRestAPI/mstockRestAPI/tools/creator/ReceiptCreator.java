package com.mstockRestAPI.mstockRestAPI.tools.creator;

import com.mstockRestAPI.mstockRestAPI.entity.Receipt;
import com.mstockRestAPI.mstockRestAPI.enums.Currency;
import com.mstockRestAPI.mstockRestAPI.enums.PaymentType;
import com.mstockRestAPI.mstockRestAPI.enums.Unit;
import com.mstockRestAPI.mstockRestAPI.tools.utils.Util;
import net.bytebuddy.utility.RandomString;

import java.util.List;
import java.util.Random;
import java.util.stream.Stream;

public class ReceiptCreator {
    private static final int NUMBER_OF_RECEIPT = 10;
    private static final int PRODUCT_RANDOM_DESCRIPTION_LENGTH = 100;
    private static final String staticReceiptNumber = "14123123";

    public static Receipt entity(){
        byte isActive = (byte) (Math.random() * 2);

        return Receipt.builder()
                .paymentType(Util.chooseRandomEnum(PaymentType.class))
                .customer(CustomerCreator.entity())
                .number(staticReceiptNumber)
                .currency(Util.chooseRandomEnum(Currency.class))
                .comment(RandomString.make(PRODUCT_RANDOM_DESCRIPTION_LENGTH))
                .isActive(isActive)
                .build();
    }

    public static List<Receipt> entityList(){
        return Stream.generate(ReceiptCreator:: entity)
                .limit(NUMBER_OF_RECEIPT)
                .toList();
    }



}
