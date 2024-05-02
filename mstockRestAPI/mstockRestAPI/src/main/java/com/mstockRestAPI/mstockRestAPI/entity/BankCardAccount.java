package com.mstockRestAPI.mstockRestAPI.entity;
import com.mstockRestAPI.mstockRestAPI.enums.CardType;
import com.mstockRestAPI.mstockRestAPI.enums.Currency;
import com.mstockRestAPI.mstockRestAPI.exception.SomethingWentWrongException;
import com.mstockRestAPI.mstockRestAPI.utils.Util;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jdk.jfr.Timespan;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.math.BigDecimal;
import java.sql.Time;
import java.sql.Timestamp;
import java.time.Instant;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "BankCardAccount")
@Getter
@Setter
@ToString
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BankCardAccount extends BaseEntity {

    @Id
    @Column(name = "accountNumber", nullable = false)
    private String accountNumber;

    @Column(name = "cardHolderName", nullable = false)
    private String cardHolderName;

    @Enumerated(EnumType.STRING)
    @Column(name = "cardType", nullable = false)
    private CardType cardType;

    @Column(name = "balance", columnDefinition = "DECIMAL(10, 2) DEFAULT 0.00")
    @Builder.Default
    private BigDecimal balance = BigDecimal.valueOf(0.00);

    @Column(name = "isActive", columnDefinition = "BOOLEAN DEFAULT false")
    @Builder.Default
    private Byte isActive = 1;

    @Column(name="currency")
    @Builder.Default
    private Currency currency = Currency.AZN;

    @PrePersist
    @PreUpdate
    private void preSave(){
        if(!Util.isValidBankAccountNumber(accountNumber)) {
            throw new SomethingWentWrongException(
                    "Wrong Format of Bank Account Number." +
                            "Bank Card Number just must contain numbers and length must be 16");
        }

        if(accountNumber != null)
            this.accountNumber = Util.makeBankCardNumberBeautiful(accountNumber);
    }

}