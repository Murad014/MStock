package com.mstockRestAPI.mstockRestAPI.dto;

import com.mstockRestAPI.mstockRestAPI.entity.Company;
import com.mstockRestAPI.mstockRestAPI.entity.ProductCategory;
import com.mstockRestAPI.mstockRestAPI.entity.ProductPicture;
import com.mstockRestAPI.mstockRestAPI.enums.Unit;
import jakarta.validation.Valid;
import lombok.*;

import jakarta.validation.constraints.*;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class ProductDto {

    private Long id;

    @NotBlank(message = "Product name is required")
    @NotEmpty
    @NotNull
    @Size(max = 100, message = "Product name must be less than or equal to 100 characters")
    private String productName;

    @NotEmpty(message="Product barcode list cannot be empty")
    @Valid
    private List<String> productBarcodeList;

    @NotEmpty(message="Product salePrices list cannot be empty")
    @Valid
    private List<BigDecimal> productSalePrices;

    private List<String> productPictureList;

    private String description;

    @Valid
    @NotNull(message = "Wholesale is required")
    @DecimalMin(value = "0.00", inclusive = false, message = "Wholesale must be greater than 0.00")
    private BigDecimal wholesale;

    @NotNull(message = "Unit is required")
    private Unit unit;

    @Valid
    @NotNull(message = "Quantity is required")
    @DecimalMin(value = "0.00", message = "Quantity must be greater than or equal to 0.00")
    private BigDecimal quantity;

    @Valid
    @NotNull(message = "Current quantity is required")
    @DecimalMin(value = "0.00", message = "Current quantity must be greater than or equal to 0.00")
    private BigDecimal currentQuantity;

    private Timestamp expiredDate;

    @NotNull(message = "cannot be null")
    @Valid
    private Long productCategoryId;

    @NotNull(message = "cannot be null")
    @Valid
    private Long companyId;

    @DecimalMin(value = "0.00", message = "Discount must be greater than or equal to 0.00")
    private BigDecimal discount;

    private Timestamp discountLastDate;

    @Min(value = 0, message = "isActive must be 0 or 1")
    @Max(value = 1, message = "isActive must be 0 or 1")
    @Builder.Default
    private byte isActive = 1;

    public static class ProductDtoBuilder {
        public ProductDtoBuilder productSalePrices(List<String> productSalePrices) {
            // Convert each string price to BigDecimal
            this.productSalePrices = productSalePrices.stream()
                    .map(BigDecimal::new)
                    .collect(Collectors.toList());
            return this;
        }
    }




}