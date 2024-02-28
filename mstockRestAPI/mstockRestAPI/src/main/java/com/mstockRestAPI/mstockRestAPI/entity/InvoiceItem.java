package com.mstockRestAPI.mstockRestAPI.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.sql.Time;
import java.sql.Timestamp;
import java.time.Instant;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "invoiceItems")
@Getter
@Setter
@ToString
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class InvoiceItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name="invoice_id")
    private Invoice invoice;

    @ManyToOne
    @JoinColumn(name = "product_id")
    private Product product;

    @Column(precision = 10, scale = 2, columnDefinition = "DECIMAL(10,2) default 0.00")
    private BigDecimal quantity;

    @Column(precision = 10, scale = 2, columnDefinition = "DECIMAL(10,2) default 0.00")
    private BigDecimal price;

    @Column(name = "totalPrice", precision = 10, scale = 2, columnDefinition = "DECIMAL(10,2) default 0.00")
    private BigDecimal totalPrice;

    @Column(name = "createdDate", updatable = false)
    @CreationTimestamp
    private Timestamp createdDate;

    @Column(name = "updatedDate")
    @UpdateTimestamp
    private Timestamp updatedDate;

    @Column(nullable = false, columnDefinition = "TINYINT default 1")
    @Builder.Default
    private Byte isActive = 1;

    @PrePersist
    @PreUpdate
    public void calc(){
        BigDecimal totalPrice = quantity.multiply(price);
        this.totalPrice = totalPrice.setScale(2, RoundingMode.HALF_UP);
    }


}