package com.mstockRestAPI.mstockRestAPI.entity;


import com.mstockRestAPI.mstockRestAPI.enums.Currency;
import com.mstockRestAPI.mstockRestAPI.enums.PaymentType;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;


@Entity
@Table(name = "receipts")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Receipt {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "number", unique = true)
    private String number;


    @Enumerated(EnumType.STRING)
    @Column(name = "typePayment", nullable = false)
    private PaymentType paymentType;


    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "customer_id")
    private Customer customer;

    @Column(columnDefinition = "TEXT")
    private String comment;

    @Column(name = "createdDate", updatable = false)
    @CreationTimestamp
    private Timestamp createdDate;

    @Column(name = "updatedDate")
    @UpdateTimestamp
    private Timestamp updatedDate;

    @OneToMany(mappedBy = "receipt",
            cascade = CascadeType.ALL,
            fetch = FetchType.LAZY)
    private List<ProductSale> productSaleList;

    @Column(name="currency")
    @Builder.Default
    private Currency currency = Currency.AZN;

    @Column(nullable = false, columnDefinition = "TINYINT default 1")
    @Builder.Default
    private Byte isActive = 1;


}