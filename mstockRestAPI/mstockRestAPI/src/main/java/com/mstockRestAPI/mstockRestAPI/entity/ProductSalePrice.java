package com.mstockRestAPI.mstockRestAPI.entity;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;


@Entity
@Table(name = "productSalePrices")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProductSalePrice {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, columnDefinition = "DECIMAL(10,2) default 0.00")
    private BigDecimal sellingPrice;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id")
    private Product product;

    @Column(nullable = false, columnDefinition = "TIMESTAMP")
    private LocalDateTime createdDate;

    @Column(nullable = false, columnDefinition = "TIMESTAMP")
    private LocalDateTime updatedDate;

    @Column(nullable = false)
    private Boolean isActive = true;



}