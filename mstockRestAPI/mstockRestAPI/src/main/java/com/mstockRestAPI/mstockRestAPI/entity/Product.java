package com.mstockRestAPI.mstockRestAPI.entity;

import jakarta.persistence.*;
import lombok.*;

import java.sql.Blob;
import java.sql.Timestamp;
import java.math.BigDecimal;
import java.time.Instant;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
@Entity
@Table(name = "products")
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "productName", length = 100)
    private String productName;

    @Column(name = "description", columnDefinition = "TEXT")
    private String description;

    @Column(name = "wholesale",
            precision = 10,
            scale = 2,
            nullable = false,
            columnDefinition = "DECIMAL(10, 2) DEFAULT 0.00")
    private BigDecimal wholesale;

    @Enumerated(EnumType.STRING)
    @Column(name = "unit", nullable = false)
    private Unit unit;

    @Column(name = "quantity",
            precision = 10,
            scale = 2,
            nullable = false,
            columnDefinition = "DECIMAL(10, 2) DEFAULT 0.00")
    private BigDecimal quantity;

    @Column(name = "currentQuantity",
            precision = 10,
            scale = 2,
            nullable = false,
            columnDefinition = "Description: Exist in stock right now. - DECIMAL(10, 2) DEFAULT 0.00.")
    private BigDecimal currentQuantity;

    @Column(name = "expiredDate")
    private Timestamp expiredDate;

    @Column(name = "categoryId", nullable = false)
    private Long categoryId;

    @Column(name = "companyId")
    private Long companyId;

    @Column(name = "picture")
    private Blob  picture;

    @Column(name = "createdDate")
    @Builder.Default
    private Timestamp createdDate = Timestamp.from(Instant.now());

    @Column(name = "updatedDate")
    @Builder.Default
    private Timestamp updatedDate = Timestamp.from(Instant.now());

    @Column(name = "isActive", nullable = false, columnDefinition = "TINYINT DEFAULT 1")
    private byte isActive;

    public enum Unit {
        KG,
        LITERS,
        PIECES,
        PACKS,
        GRAMS,
        MILLILITERS,
        UNIT
    }
}