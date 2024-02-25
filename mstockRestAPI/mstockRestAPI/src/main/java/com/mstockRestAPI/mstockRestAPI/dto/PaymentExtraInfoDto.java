package com.mstockRestAPI.mstockRestAPI.dto;

import com.mstockRestAPI.mstockRestAPI.enums.PaymentType;
import com.mstockRestAPI.mstockRestAPI.validation.BankAccountNumber;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.math.BigDecimal;
import java.sql.Timestamp;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PaymentExtraInfoDto {
    private BigDecimal cashPay;
    private BigDecimal cardPay;
    private BigDecimal creditPay;

    private BankCardAccountDto bankAccountNumber;

    @NotNull
    @NotEmpty
    private PaymentType paymentType;
}
