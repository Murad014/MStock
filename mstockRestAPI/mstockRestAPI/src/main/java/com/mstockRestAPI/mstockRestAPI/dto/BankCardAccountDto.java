package com.mstockRestAPI.mstockRestAPI.dto;
import com.mstockRestAPI.mstockRestAPI.enums.CardType;
import com.mstockRestAPI.mstockRestAPI.enums.Currency;
import com.mstockRestAPI.mstockRestAPI.exception.SomethingWentWrongException;
import com.mstockRestAPI.mstockRestAPI.utils.Util;
import com.mstockRestAPI.mstockRestAPI.validation.BankAccountNumber;
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
@ToString
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BankCardAccountDto {
    @Valid
    @NotEmpty(message = "AccountNumber cannot be empty")
    @NotNull(message = "AccountNumber cannot be null")
    @BankAccountNumber
    private String accountNumber;

    @Valid
    @NotEmpty(message = "CardHolderName cannot be empty")
    @NotNull(message = "CardHolderName cannot be null")
    private String cardHolderName;

    @Valid
    @NotNull(message = "CardType cannot be null")
    private CardType cardType;

    @Builder.Default
    private BigDecimal balance = BigDecimal.valueOf(0.00);
    private Byte isActive = 1;

    @Builder.Default
    private Currency currency = Currency.AZN;



}