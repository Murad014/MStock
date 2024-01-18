package com.mstockRestAPI.mstockRestAPI.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.math.BigDecimal;
import java.sql.Time;
import java.sql.Timestamp;
import java.time.Instant;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "paymentInvoices")
@Getter
@Setter
@ToString
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PaymentInvoice {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Long invoiceId;

    @Column(name = "plusPay", precision = 10, scale = 2, columnDefinition = "DECIMAL(10,2) default 0.00")
    private BigDecimal plusPay;

    @Column(name = "minusPay", precision = 10, scale = 2, columnDefinition = "DECIMAL(10,2) default 0.00")
    private BigDecimal minusPay;

    @Column(nullable = false, columnDefinition = "TIMESTAMP")
    @Builder.Default
    private Timestamp createdDate = Timestamp.valueOf(LocalDateTime.now());

    @Column(columnDefinition = "TIMESTAMP")
    private Timestamp updatedDate;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private PaymentType typePayment;

    @Column(length = 16)
    private String bankCardAccount;

    @Column(nullable = false, columnDefinition = "TINYINT default 1")
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

    public PaymentType paymentType;

}