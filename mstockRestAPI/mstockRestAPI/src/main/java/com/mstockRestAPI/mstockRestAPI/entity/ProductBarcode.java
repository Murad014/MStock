package com.mstockRestAPI.mstockRestAPI.entity;

import jakarta.persistence.Entity;
import lombok.*;

import jakarta.persistence.*;
import lombok.*;
import jakarta.persistence.*;

import java.sql.Blob;
import java.sql.Time;
import java.sql.Timestamp;
import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDateTime;


@Entity
@Table(name = "productBarcodes")
@Getter
@Setter
@ToString
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProductBarcode {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    private String barcode;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id")
    private Product product;


    @Column(nullable = false, columnDefinition = "TIMESTAMP")
    @Builder.Default
    private Timestamp createdDate = Timestamp.valueOf(LocalDateTime.now());

    @Column(columnDefinition = "TIMESTAMP")
    private Timestamp updatedDate;

    @Column(nullable = false)
    @Builder.Default
    private Byte isActive = 1;


}
