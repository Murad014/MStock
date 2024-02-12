package com.mstockRestAPI.mstockRestAPI.entity;


import com.mstockRestAPI.mstockRestAPI.enums.Currency;
import com.mstockRestAPI.mstockRestAPI.enums.PaymentType;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.List;


@Entity
@Table(name = "SaleReceipts")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SaleReceipt {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "number", unique = true)
    private String number;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "customer_id")
    private Customer customer;

    @OneToOne(cascade = CascadeType.PERSIST, fetch = FetchType.EAGER)
    @JoinColumn(name = "paymentExtraInfo_id", referencedColumnName = "id", nullable = false)
    private PaymentExtraInfo paymentExtraInfo;

    @Column(columnDefinition = "TEXT")
    private String comment;

    @Column(name = "createdDate", updatable = false)
    @CreationTimestamp
    private Timestamp createdDate;

    @Column(name = "updatedDate")
    @UpdateTimestamp
    private Timestamp updatedDate;

    @OneToMany(mappedBy = "receipt",
            fetch = FetchType.EAGER)
    private List<ProductMovements> productSaleList;

    @Column(name="currency")
    @Builder.Default
    private Currency currency = Currency.AZN;

    @Column(nullable = false, columnDefinition = "TINYINT default 1")
    @Builder.Default
    private Byte isActive = 1;


}