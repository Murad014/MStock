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
@Table(name = "invoices")
@Getter
@Setter
@ToString
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Invoice {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false, length = 100)
    private String invoiceCode;


    @ManyToOne
    @JoinColumn(name="supplierOfProduct_id")
    private SupplierOfProduct supplier;

    @Column(columnDefinition = "TIMESTAMP")
    private LocalDateTime invoiceDate;

    @Column(name = "initialTotalAmount", precision = 10, scale = 2, columnDefinition = "DECIMAL(10,2) default 0.00")
    private BigDecimal initialTotalAmount;

    @Column(nullable = false, columnDefinition = "TIMESTAMP")
    @Builder.Default
    private Timestamp createdDate = Timestamp.valueOf(LocalDateTime.now());

    @Column(columnDefinition = "TIMESTAMP")
    private Timestamp updatedDate;

    @Column(nullable = false, columnDefinition = "TINYINT default 1")
    private Byte isActive;



}