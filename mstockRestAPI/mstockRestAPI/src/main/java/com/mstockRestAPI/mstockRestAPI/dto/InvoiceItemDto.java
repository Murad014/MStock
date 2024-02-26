package com.mstockRestAPI.mstockRestAPI.dto;


import com.mstockRestAPI.mstockRestAPI.entity.Invoice;
import com.mstockRestAPI.mstockRestAPI.entity.Product;
import jakarta.persistence.*;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.math.BigDecimal;
import java.sql.Timestamp;

@Getter
@Setter
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class InvoiceItemDto {

    private Long id;

    @Valid
    @NotNull(message = "Cannot be null")
    @NotEmpty(message = "Cannot be empty")
    private Long invoiceId;

    @Valid
    @NotNull(message = "Cannot be null")
    @NotNull(message = "Cannot be empty")
    private Long productId;

    @Builder.Default
    private BigDecimal quantity = BigDecimal.ZERO;

    @Builder.Default
    private BigDecimal price = BigDecimal.ZERO;

    @Builder.Default
    private Byte isActive = 1;
}
