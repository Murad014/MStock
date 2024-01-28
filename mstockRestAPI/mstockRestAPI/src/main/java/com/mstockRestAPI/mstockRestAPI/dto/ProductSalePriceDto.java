package com.mstockRestAPI.mstockRestAPI.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import com.mstockRestAPI.mstockRestAPI.entity.Company;
import com.mstockRestAPI.mstockRestAPI.entity.ProductCategory;
import com.mstockRestAPI.mstockRestAPI.enums.Unit;
import lombok.*;
import jakarta.validation.constraints.*;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.List;

@Getter
@Setter
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class ProductSalePriceDto {
    private Long id;

    @NotNull(message = "Selling price cannot be null")
    private BigDecimal sellingPrice;

    @NotNull(message = "isActive cannot be null")
    @Builder.Default
    private Byte isActive = 1;

}