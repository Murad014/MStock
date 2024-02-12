package com.mstockRestAPI.mstockRestAPI.entity;

import com.mstockRestAPI.mstockRestAPI.exception.PaymentProcessException;
import com.mstockRestAPI.mstockRestAPI.exception.SomethingWentWrongException;
import com.mstockRestAPI.mstockRestAPI.utils.EntityUtil;
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
import com.mstockRestAPI.mstockRestAPI.enums.PaymentType;

@Entity
@Table(name = "paymentExtraInfo")
@Getter
@Setter
@ToString
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PaymentExtraInfo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(columnDefinition = "DECIMAL(10, 2) default '0.00'")
    private BigDecimal cashPay;

    @Column(columnDefinition = "DECIMAL(10, 2) default '0.00'")
    private BigDecimal cardPay;

    @Column(columnDefinition = "DECIMAL(10, 2) default '0.00'")
    private BigDecimal creditPay;

    @ManyToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name="bankCardAccount_accountNumber")
    private BankCardAccount bankAccountNumber;

    @Enumerated(EnumType.STRING)
    @Column(name = "paymentType", nullable = false)
    private PaymentType paymentType;

    @Column(nullable = false)
    @UpdateTimestamp
    private Timestamp updatedDate;

    @Column(nullable = false)
    @CreationTimestamp
    private Timestamp createdDate;

    @PrePersist
    @PreUpdate
    private void preUpdateAndPersist(){
        EntityUtil.checkEntityPayments(paymentType, cashPay, cardPay, creditPay);
    }


}
