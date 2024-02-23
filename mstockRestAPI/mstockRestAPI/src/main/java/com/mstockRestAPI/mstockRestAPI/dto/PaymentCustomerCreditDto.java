package com.mstockRestAPI.mstockRestAPI.dto;


import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.sql.Timestamp;

@Getter
@Setter
@ToString
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PaymentCustomerCreditDto {
    private Long id;

    @NotNull(message = "PaymentAmount cannot be null")
    @NotEmpty(message = "PaymentAmount cannot be empty")
    private BigDecimal paymentAmount;

    @NotNull(message = "Credit cannot be null")
    @NotEmpty(message = "Credit cannot be empty")
    private CreditOfCustomersDto credit;

    @Builder.Default
    private Byte isActive = 1;
}
