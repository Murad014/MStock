package com.mstockRestAPI.mstockRestAPI.dto;

import com.mstockRestAPI.mstockRestAPI.entity.SupplierOfProduct;
import com.mstockRestAPI.mstockRestAPI.enums.Currency;
import jakarta.persistence.*;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class InvoiceDto {

    private Long id;

    @Valid
    @NotNull(message = "Cannot be null")
    @NotEmpty(message = "Cannot be empty")
    private String invoiceCode;

    @Valid
    @NotNull(message = "Cannot be null")
    private SupplierOfProduct supplier;

    @Builder.Default
    private LocalDateTime invoiceDate = LocalDateTime.now();

    @Builder.Default
    private BigDecimal initialTotalAmount = BigDecimal.ZERO;

    @Builder.Default
    private Currency currency = Currency.AZN;

    @Builder.Default
    private Byte isActive = 1;
}
