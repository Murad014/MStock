package com.mstockRestAPI.mstockRestAPI.entity;

import jakarta.persistence.Entity;
import lombok.*;

import jakarta.persistence.*;
import lombok.*;
import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

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

    @Column(name = "createdDate", updatable = false)
    @CreationTimestamp
    private Timestamp createdDate;

    @Column(name = "updatedDate")
    @UpdateTimestamp
    private Timestamp updatedDate;

    @Column(nullable = false)
    @Builder.Default
    private Byte isActive = 1;


}
