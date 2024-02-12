package com.mstockRestAPI.mstockRestAPI.dto;
import com.mstockRestAPI.mstockRestAPI.enums.SaleStatus;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.math.BigDecimal;

@Getter
@Setter
@ToString
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProductSaleDto {

    private Long id;

    @NotNull(message = "Sale cannot be without product")
    @Valid
    private ProductDto product;

    @Valid
    @NotNull(message = "Quantity cannot be null")
    private BigDecimal quantity;

    @Valid
    @NotNull(message = "Every sale must generate receipt.")
    private SaleReceiptDto receipt;

    private BigDecimal salePrice;

    private BigDecimal discountPercent;

    private BigDecimal discountDecimal;

    private BigDecimal bonus;

    private String comment;

    @Valid
    @NotNull(message = "Cannot be null")
    @NotEmpty(message = "Cannot be empty")
    private SaleStatus saleStatus;

    @Builder.Default
    private Byte isActive = 1;
}