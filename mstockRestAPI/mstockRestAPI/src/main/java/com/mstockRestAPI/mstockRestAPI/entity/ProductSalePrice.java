package com.mstockRestAPI.mstockRestAPI.entity;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.sql.Time;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;


@Entity
@Table(name = "productSalePrices")
@Getter
@Setter
@ToString
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProductSalePrice {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, columnDefinition = "DECIMAL(10,2) default 0.00")
    private BigDecimal sellingPrice;

    @Column(nullable = false, columnDefinition = "TIMESTAMP")
    @Builder.Default
    private Timestamp createdDate = Timestamp.valueOf(LocalDateTime.now());

    @Column(nullable = false, columnDefinition = "TIMESTAMP")
    private Timestamp updatedDate;

    @Column(nullable = false)
    @Builder.Default
    private Byte isActive = 1;


    //    @ManyToOne(fetch = FetchType.LAZY)
    //    @JoinColumn(name = "product_id")
    //    private Product product;


}