package com.mstockRestAPI.mstockRestAPI.entity;

import com.mstockRestAPI.mstockRestAPI.enums.PaymentType;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.time.LocalDateTime;

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

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "invoice_id")
    private Invoice invoice;

    @Column(name = "plusPay", precision = 10, scale = 2, columnDefinition = "DECIMAL(10,2) default 0.00")
    private BigDecimal plusPay;

    @Column(name = "minusPay", precision = 10, scale = 2, columnDefinition = "DECIMAL(10,2) default 0.00")
    private BigDecimal minusPay;

    @Column(name = "cashPay", columnDefinition = "DECIMAL(10, 2) DEFAULT 0.00")
    private BigDecimal cashPay;

    @Column(name = "cardPay", columnDefinition = "DECIMAL(10, 2) DEFAULT 0.00")
    private BigDecimal cardPay;

    @Column(name = "createdDate", updatable = false)
    @CreationTimestamp
    private Timestamp createdDate;

    @Column(name = "updatedDate")
    @UpdateTimestamp
    private Timestamp updatedDate;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private PaymentType typePayment;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name="bankCardAccount_accountNumber")
    private BankCardAccount bankCardAccount;

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
        if(paymentType == PaymentType.CARD && cardPay.compareTo(BigDecimal.ZERO) == 0)
            throw new IllegalStateException("If payment type equals Card then card payment cannot be 0.");

        if(paymentType == PaymentType.CASH_AND_CARD &&
                (cardPay.compareTo(BigDecimal.ZERO) == 0 || cashPay.compareTo(BigDecimal.ZERO) == 0))
            throw new IllegalStateException("If payment type cash and card then these fields cannot be 0.");
    }

    public PaymentType paymentType;

}