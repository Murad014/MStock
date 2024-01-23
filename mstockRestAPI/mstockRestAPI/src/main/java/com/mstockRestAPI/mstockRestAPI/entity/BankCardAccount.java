package com.mstockRestAPI.mstockRestAPI.entity;
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
public class BankCardAccount {

    @Id
    @Column(name = "accountNumber", length = 16, nullable = false)
    private String accountNumber;

    @Column(name = "cardHolderName", nullable = false)
    private String cardHolderName;

    @Column(name = "cardType", nullable = false, length = 20)
    private String cardType;

    @Column(name = "balance", columnDefinition = "DECIMAL(10, 2) DEFAULT 0.00")
    @Builder.Default
    private BigDecimal balance = BigDecimal.valueOf(0.00);

    @Column(name = "isActive", columnDefinition = "BOOLEAN DEFAULT false")
    @Builder.Default
    private Byte isActive = 1;

    @Column(name = "createdDate", updatable = false)
    @CreationTimestamp
    private Timestamp createdDate;

    @Column(name = "updatedDate")
    @UpdateTimestamp
    private Timestamp updatedDate;

    @Column(name="currency")
    @Builder.Default
    private String currency = "AZN";

}