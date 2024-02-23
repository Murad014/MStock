package com.mstockRestAPI.mstockRestAPI.dto;


import jakarta.validation.constraints.*;
import lombok.*;

import java.math.BigDecimal;
import java.sql.Timestamp;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CreditOfCustomersDto {
    private Long id;

    @NotNull(message = "Customer must not be null")
    private CustomerDto customer;

    @NotNull(message = "Given amount must not be null")
    @DecimalMin(value = "0.0", inclusive = false, message = "Given amount must be greater than 0")
    private BigDecimal givenAmount;

    @NotNull(message = "Total amount must not be null")
    @DecimalMin(value = "0.0", inclusive = false, message = "Total amount must be greater than 0")
    private BigDecimal totalAmount;

    @NotNull(message = "Percentage fee must not be null")
    @DecimalMin(value = "0.0", inclusive = false, message = "Percentage fee must be greater than 0")
    private BigDecimal percentageFee;

    @NotNull(message = "Commission amount must not be null")
    @DecimalMin(value = "0.0", inclusive = false, message = "Commission amount must be greater than 0")
    private BigDecimal commissionAmount;

    @NotNull(message = "Monthly installment must not be null")
    @DecimalMin(value = "0.0", inclusive = false, message = "Monthly installment must be greater than 0")
    private BigDecimal monthlyInstallment;

    @NotNull(message = "Credit months must not be null")
    @Min(value = 1, message = "Credit months must be greater than 0")
    private Integer creditMonths;

    @NotNull(message = "Fine percentage must not be null")
    @DecimalMin(value = "0.0", inclusive = false, message = "Fine percentage must be greater than 0")
    private BigDecimal finePercentage;

    @NotNull(message = "Due date must not be null")
    private Timestamp dueDate;

    @NotNull(message = "Installment count must not be null")
    @Min(value = 0, message = "Installment count must be greater than or equal to 0")
    private Integer installmentCount;

    @NotNull(message = "Closed must not be null")
    private Boolean closed;

    @NotNull(message = "Is active must not be null")
    private Byte isActive;

}
