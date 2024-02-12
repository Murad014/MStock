package com.mstockRestAPI.mstockRestAPI.utils;

import com.mstockRestAPI.mstockRestAPI.enums.PaymentType;
import com.mstockRestAPI.mstockRestAPI.exception.PaymentProcessException;

import java.math.BigDecimal;

public class EntityUtil {
    public static void checkEntityPayments(PaymentType paymentType, BigDecimal cashPay,
                                           BigDecimal cardPay,
                                           BigDecimal creditPay){
        if(
                paymentType.equals(PaymentType.CASH_AND_CARD) &&
                        (cashPay == null || cashPay.compareTo(BigDecimal.ZERO) <= 0 ||
                                cardPay == null || cardPay.compareTo(BigDecimal.ZERO) <= 0 )) {

            throw new PaymentProcessException("PaymentExtraInfo", paymentType.toString(),
                    "If payment type is CASH_AND_CARD then CASH and CARD fields must be greater than zero.");
        }

        else if(
                paymentType.equals(PaymentType.CREDIT_AND_CARD) &&
                        (creditPay == null || creditPay.compareTo(BigDecimal.ZERO) <= 0 ||
                                cardPay == null || cardPay.compareTo(BigDecimal.ZERO) <= 0 )) {

            throw new PaymentProcessException("PaymentExtraInfo", paymentType.toString(),
                    "If payment type is CREDIT_AND_CARD then CREDIT and CARD fields must be greater than zero.");
        }

        else if(
                paymentType.equals(PaymentType.CREDIT_AND_CASH) &&
                        (creditPay == null || creditPay.compareTo(BigDecimal.ZERO) <= 0 ||
                                cashPay == null || cashPay.compareTo(BigDecimal.ZERO) <= 0 )) {

            throw new PaymentProcessException("PaymentExtraInfo", paymentType.toString(),
                    "If payment type is CREDIT_AND_CASH then CREDIT and CASH fields must be greater than zero.");
        }
    }


}
