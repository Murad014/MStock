package com.mstockRestAPI.mstockRestAPI.entity;
import com.mstockRestAPI.mstockRestAPI.enums.Currency;
import com.mstockRestAPI.mstockRestAPI.enums.PaymentType;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.time.LocalDateTime;

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

    @Column(name = "createdDate", updatable = false)
    @CreationTimestamp
    private Timestamp createdDate;

    @Column(name = "updatedDate")
    @UpdateTimestamp
    private Timestamp updatedDate;

    @Enumerated(EnumType.STRING)
    @Column(name = "typePayment", nullable = false)
    private PaymentType paymentType;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "bankCardAccount_accountNumber")
    private BankCardAccount bankCardAccount;

    @Column(name="currency")
    @Builder.Default
    private Currency currency = Currency.AZN;


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