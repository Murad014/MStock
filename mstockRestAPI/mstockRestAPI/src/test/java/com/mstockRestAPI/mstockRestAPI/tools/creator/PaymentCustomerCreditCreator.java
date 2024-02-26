package com.mstockRestAPI.mstockRestAPI.tools.creator;

import com.mstockRestAPI.mstockRestAPI.dto.PaymentCustomerCreditDto;
import com.mstockRestAPI.mstockRestAPI.entity.CreditOfCustomers;
import com.mstockRestAPI.mstockRestAPI.entity.PaymentCustomerCredit;
import com.mstockRestAPI.mstockRestAPI.enums.PaymentType;
import com.mstockRestAPI.mstockRestAPI.tools.utils.Util;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Stream;

public class PaymentCustomerCreditCreator {

    public static PaymentCustomerCredit entity(){

        return PaymentCustomerCredit.builder()
                .paymentAmount(
                        BigDecimal.valueOf(Util.generateRandomPrice(1, 100))
                )
                .credit(CreditOfCustomersCreator.entity())
                .paymentExtraInfo(PaymentExtraInfoCreator.createEntity(
                        BigDecimal.TEN,
                        BigDecimal.ONE,
                        null,
                        PaymentType.CASH_AND_CARD
                ))
                .isActive((byte)1)
                .build();
    }

    public static List<PaymentCustomerCredit> entityList(){
        return Stream.generate(PaymentCustomerCreditCreator::entity)
                .limit(3)
                .toList();
    }

    public static PaymentCustomerCreditDto dto(){
        return PaymentCustomerCreditDto.builder()
                .paymentAmount(
                        BigDecimal.valueOf(Util.generateRandomPrice(1, 100))
                )
                .credit(CreditOfCustomersCreator.dto())
                .isActive((byte)1)
                .build();
    }

    public static List<PaymentCustomerCreditDto> dtoList(){
        return Stream.generate(PaymentCustomerCreditCreator::dto)
                .limit(3)
                .toList();
    }


}
