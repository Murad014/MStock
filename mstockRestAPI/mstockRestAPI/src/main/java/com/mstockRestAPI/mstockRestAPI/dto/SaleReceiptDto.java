package com.mstockRestAPI.mstockRestAPI.dto;

import com.mstockRestAPI.mstockRestAPI.entity.Customer;
import com.mstockRestAPI.mstockRestAPI.entity.PaymentExtraInfo;
import com.mstockRestAPI.mstockRestAPI.entity.ProductMovements;
import com.mstockRestAPI.mstockRestAPI.enums.Currency;
import com.mstockRestAPI.mstockRestAPI.enums.PaymentType;
import jakarta.persistence.*;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.sql.Timestamp;
import java.util.List;

@Getter
@Setter
@ToString
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SaleReceiptDto {
    private Long id;

    @NotNull(message = "Receipt number cannot be null")
    @NotEmpty(message = "Receipt number cannot be empty")
    @Valid
    private String number;
    private Customer customer;
    private PaymentExtraInfo paymentExtraInfo;
    private String comment;
    private List<ProductMovements> productSaleList;

    @Builder.Default
    private Currency currency = Currency.AZN;

    @Builder.Default
    private Byte isActive = 1;
}