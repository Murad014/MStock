package com.mstockRestAPI.mstockRestAPI.entity;
import com.mstockRestAPI.mstockRestAPI.enums.PaymentType;
import com.mstockRestAPI.mstockRestAPI.enums.SaleStatus;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.math.BigDecimal;
import java.sql.Time;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "productMovements")
@Getter
@Setter
@ToString
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProductMovements extends BaseEntity{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @JoinColumn(nullable = false, name="product_id")
    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.REFRESH)
    private Product product;

    @Column(precision = 10, scale = 2)
    private BigDecimal quantity;

    @Column(precision = 10, scale = 2)
    private BigDecimal salePrice;

    @Column(precision = 10, scale = 2)
    private BigDecimal sumPrice; // is quantity * salePrice

    @Column(name = "discountPercent", precision = 10, scale = 2)
    private BigDecimal discountPercent;

    @Column(name = "discountDecimal", precision = 10, scale = 2)
    private BigDecimal discountDecimal;

    @Column(precision = 10, scale = 2, columnDefinition = "DECIMAL(10,2) default 0.00")
    private BigDecimal bonus;

    @Column(columnDefinition = "TEXT")
    private String comment;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
    @JoinColumn(name="saleReceipt_number")
    private SaleReceipt receipt;

    @Column(name="saleStatus")
    private SaleStatus saleStatus;

    @Column(nullable = false, columnDefinition = "TINYINT default 1")
    @Builder.Default
    private Byte isActive = 1;

    @PrePersist
    @PreUpdate
    private void calcSumPrice() {
        sumPrice = salePrice.multiply(quantity);
    }


}