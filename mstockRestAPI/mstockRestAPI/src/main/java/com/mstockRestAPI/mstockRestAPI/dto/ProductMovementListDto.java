package com.mstockRestAPI.mstockRestAPI.dto;

import com.mstockRestAPI.mstockRestAPI.enums.Currency;
import com.mstockRestAPI.mstockRestAPI.enums.PaymentType;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import java.util.*;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProductMovementListDto {

    @NotNull(message = "Payment type cannot be null")
    @NotEmpty(message = "Payment type cannot be empty")
    @Valid
    private PaymentType paymentType;

    private PaymentExtraInfoDto paymentExtraInfo;

    private CustomerDto customer;

    private String receiptComment;

    @Builder.Default
    private Currency currency = Currency.AZN;

    @NotNull(message = "Product list cannot be null")
    @NotEmpty(message = "Product list cannot be empty")
    @Valid
    private List<ProductMovementDto> productsList;



}
