package com.mstockRestAPI.mstockRestAPI.dto;

import com.mstockRestAPI.mstockRestAPI.entity.BankCardAccount;
import com.mstockRestAPI.mstockRestAPI.entity.Invoice;
import com.mstockRestAPI.mstockRestAPI.entity.PaymentExtraInfo;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.math.BigDecimal;
import java.sql.Timestamp;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PaymentInvoiceDto {
    private Long id;

    @NotNull(message = "cannot be null")
    @Size(min = 1)
    private Long invoiceId;
    private String bankCardAccountId;

    private BigDecimal plusPay;
    private BigDecimal minusPay;
    @NotNull(message = "cannot be null")
    private PaymentExtraInfoDto paymentExtraInfo;

    @Builder.Default
    private Byte isActive = 1;
}
