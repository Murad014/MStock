package com.mstockRestAPI.mstockRestAPI.dto;


import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.Valid;
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
    @Valid
    private BigDecimal paymentAmount;

    @NotNull(message = "Credit cannot be null")
    @Valid
    private CreditOfCustomersDto credit;

//    @NotNull(message = "")

    @Builder.Default
    private Byte isActive = 1;
}
