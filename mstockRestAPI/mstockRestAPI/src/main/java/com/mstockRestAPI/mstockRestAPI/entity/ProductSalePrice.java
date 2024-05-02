package com.mstockRestAPI.mstockRestAPI.entity;
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
@Table(name = "productSalePrices")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProductSalePrice extends BaseEntity{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, columnDefinition = "DECIMAL(10, 2) default 0.00")
    private BigDecimal sellingPrice;

    @Column(nullable = false)
    @Builder.Default
    private Byte isActive = 1;

    public String toString(){
        return String.valueOf(this.getSellingPrice());
    }


    //    @ManyToOne(fetch = FetchType.LAZY)
    //    @JoinColumn(name = "product_id")
    //    private Product product;


}