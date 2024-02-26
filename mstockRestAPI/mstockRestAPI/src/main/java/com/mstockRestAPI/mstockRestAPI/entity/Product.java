package com.mstockRestAPI.mstockRestAPI.entity;

import com.mstockRestAPI.mstockRestAPI.enums.Unit;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.sql.Blob;
import java.sql.Timestamp;
import java.math.BigDecimal;
import java.time.Instant;
import java.util.List;
import java.util.Set;

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

    @OneToMany(// mappedBy = "product",
            cascade = CascadeType.ALL,
            fetch = FetchType.EAGER,
            orphanRemoval = true)
    @Column(unique = true)
    private List<ProductBarcode> productBarcodeList;

    @OneToMany(
            cascade = CascadeType.ALL,
            fetch = FetchType.EAGER
//            orphanRemoval = true
    )
    @Column(unique = true)
    private List<ProductPicture> productPictureList;

    @OneToMany(// mappedBy = "product",
            cascade = CascadeType.ALL,
            fetch = FetchType.EAGER,
            orphanRemoval = true
    )
    private List<ProductSalePrice> productSalePrices;

    @Column(name = "description")
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
            columnDefinition = "DECIMAL(10, 2) DEFAULT 0.00")
    private BigDecimal currentQuantity;

    @Column(name = "expiredDate")
    private Timestamp expiredDate;

    @ManyToOne
    @JoinColumn(name = "productCategory_id")
    private ProductCategory category;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "company_id")
    private Company company;

    @Column(name = "discount",
            precision = 10,
            scale = 2,
            columnDefinition = "DECIMAL(10, 2) DEFAULT 0.00")
    private BigDecimal discount;

    @Column(name="discountLastDate")
    @Builder.Default
    private Timestamp discountLastDate = null;

    @Column(name = "createdDate", updatable = false)
    @CreationTimestamp
    private Timestamp createdDate;

    @Column(name = "updatedDate")
    @UpdateTimestamp
    private Timestamp updatedDate;


    @Column(name = "isActive", nullable = false, columnDefinition = "TINYINT DEFAULT 1")
    @Builder.Default
    private byte isActive = 1;


}