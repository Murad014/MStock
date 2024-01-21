package com.mstockRestAPI.mstockRestAPI.entity;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jdk.jfr.Timespan;
import lombok.*;
import org.springframework.data.repository.cdi.Eager;

import java.math.BigDecimal;
import java.sql.Time;
import java.sql.Timestamp;
import java.time.Instant;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "paymentCustomer")
@Getter
@Setter
@ToString
@Builder
public class PaymentCustomer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "customer_id")
    private Customer customer;

    @Column(name = "plusPay", columnDefinition = "DECIMAL(10, 2) DEFAULT 0.00")
    private BigDecimal plusPay;

    @Column(name = "minusPay", columnDefinition = "DECIMAL(10, 2) DEFAULT 0.00")
    private BigDecimal minusPay;

    @Column(name = "createdDate", nullable = false)
    @Builder.Default
    private Timestamp createdDate = Timestamp.valueOf(LocalDateTime.now());

    @Column(name = "updatedDate", nullable = false)
    private Timestamp updatedDate;

    @Enumerated(EnumType.STRING)
    @Column(name = "typePayment", nullable = false)
    private PaymentType paymentType;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "bankCardAccount_accountNumber")
    private BankCardAccount bankCardAccount;

    @Column(name = "isActive", columnDefinition = "TINYINT DEFAULT 1")
    @Builder.Default
    private Byte isActive = 1;

    @PrePersist
    @PreUpdate
    private void validatePayments() {
        if (plusPay != null && minusPay != null && plusPay.compareTo(BigDecimal.ZERO) == 0
                && minusPay.compareTo(BigDecimal.ZERO) == 0) {
            throw new IllegalStateException("Plus pay and minus pay cannot both be 0.");
        }
    }


}