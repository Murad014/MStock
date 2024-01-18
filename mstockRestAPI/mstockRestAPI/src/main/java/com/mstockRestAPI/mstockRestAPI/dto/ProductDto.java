package com.mstockRestAPI.mstockRestAPI.dto;

import com.mstockRestAPI.mstockRestAPI.entity.Product;
import lombok.*;

import jakarta.validation.constraints.*;

import java.math.BigDecimal;
import java.sql.Timestamp;

@Getter
@Setter
@ToString
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProductDto {

    private Long id;

    @NotBlank(message = "Product name cannot be blank")
    @Size(max = 100, message = "Product name must be less than or equal to 100 characters")
    private String productName;

    private String description;

    @NotNull(message = "Wholesale price cannot be null")
    @DecimalMin(value = "0.00", inclusive = false, message = "Wholesale price must be greater than 0.00")
    private BigDecimal wholesale;


    @NotBlank(message = "Unit cannot be blank")
    @Size(max = 10, message = "Unit must be less than or equal to 10 characters")
    private Product.Unit unit;

    @NotNull(message = "Quantity cannot be null")
    @DecimalMin(value = "0.00", inclusive = false, message = "Quantity must be greater than 0.00")
    private BigDecimal quantity;

    @NotNull(message = "Current quantity cannot be null")
    @DecimalMin(value = "0.00", inclusive = false, message = "Current quantity must be greater than 0.00")
    private BigDecimal currentQuantity;

    private Timestamp expiredDate;

    @NotNull(message = "Category ID cannot be null")
    private Long categoryId;

    private Long companyId;

    private byte[] picture;

    private Timestamp updatedDate;

    @NotNull(message = "isActive cannot be null")
    private byte isActive;

}
