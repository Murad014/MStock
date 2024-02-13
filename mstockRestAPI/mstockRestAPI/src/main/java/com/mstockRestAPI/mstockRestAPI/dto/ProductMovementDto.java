package com.mstockRestAPI.mstockRestAPI.dto;

import com.mstockRestAPI.mstockRestAPI.entity.Product;
import com.mstockRestAPI.mstockRestAPI.enums.SaleStatus;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.math.BigDecimal;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProductMovementDto {
    private Long id;

    @Valid
    @NotNull(message = "Sale cannot be without product")
    private Product product;
    private BigDecimal quantity;

    @NotNull
    @NotEmpty
    private BigDecimal salePrice;

    @NotNull
    @NotEmpty
    private BigDecimal sumPrice; // is quantity * salePrice

    private BigDecimal discountPercent;
    private BigDecimal discountDecimal;
    private BigDecimal bonus;
    private String comment;

    @Valid
    @NotNull(message = "Every sale must generate receipt.")
    private SaleReceiptDto receipt;

    @NotNull
    private SaleStatus saleStatus;

    @Builder.Default
    private Byte isActive = 1;
}
