package com.mstockRestAPI.mstockRestAPI.entity;

import com.mstockRestAPI.mstockRestAPI.enums.Currency;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

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

    @ManyToOne(cascade = {
            CascadeType.PERSIST,
            CascadeType.REFRESH
    })
    @JoinColumn(name="supplierOfProduct_id")
    private SupplierOfProduct supplier;

//    @OneToMany(fetch = FetchType.LAZY)
//    private List<InvoiceItem> invoiceItems;

    @Column(columnDefinition = "TIMESTAMP")
    private LocalDateTime invoiceDate;

    @Column(name = "initialTotalAmount",
            precision = 10,
            scale = 2,
            columnDefinition = "DECIMAL(10,2) default 0.00")
    private BigDecimal initialTotalAmount;

    @Column(name = "createdDate", updatable = false)
    @CreationTimestamp
    private Timestamp createdDate;

    @Column(name = "updatedDate")
    @UpdateTimestamp
    private Timestamp updatedDate;

    @Column(name="currency")
    @Builder.Default
    private Currency currency = Currency.AZN;

    @Column(nullable = false, columnDefinition = "TINYINT default 1")
    private Byte isActive;


}