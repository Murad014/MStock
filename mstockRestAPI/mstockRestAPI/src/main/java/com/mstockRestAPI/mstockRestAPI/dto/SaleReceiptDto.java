package com.mstockRestAPI.mstockRestAPI.dto;

import com.mstockRestAPI.mstockRestAPI.enums.Currency;
import com.mstockRestAPI.mstockRestAPI.enums.PaymentType;
import lombok.*;

import java.util.List;

@Getter
@Setter
@ToString
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SaleReceiptDto {

    private Long id;

    private PaymentType paymentType;

    @Builder.Default
    private Currency currency = Currency.AZN;

    private String comment;

    private List<ProductSaleDto> productSaleList;

    @Builder.Default
    private Byte isActive = 1;
}