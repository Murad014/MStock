package com.mstockRestAPI.mstockRestAPI.dto;

import com.mstockRestAPI.mstockRestAPI.enums.Currency;
import com.mstockRestAPI.mstockRestAPI.enums.PaymentType;
import lombok.*;

import java.sql.Timestamp;

@Getter
@Setter
@ToString
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ReceiptDto {

    private Long id;
    private PaymentType typePayment;
    private Timestamp createdDate;
    private Timestamp updatedDate;
    private PaymentType paymentType;
    private Currency currency;
    private Byte isActive;
}