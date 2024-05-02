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
public class PaymentInvoice extends BaseEntity{

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

    @OneToOne(cascade = CascadeType.PERSIST, fetch = FetchType.EAGER)
    @JoinColumn(name = "paymentExtraInfo_id", referencedColumnName = "id", nullable = false)
    private PaymentExtraInfo paymentExtraInfo;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name="bankCardAccount_accountNumber")
    private BankCardAccount bankCardAccount;

    @Column(nullable = false, columnDefinition = "TINYINT default 1")
    @Builder.Default
    private Byte isActive = 1;


}