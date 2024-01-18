package com.mstockRestAPI.mstockRestAPI.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.math.BigDecimal;
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

    @Column(nullable = false)
    private Long invoiceId;

    @Column(nullable = false)
    private Long productId;

    @Column(precision = 10, scale = 2)
    private BigDecimal quantity;

    @Column(name = "totalPrice", precision = 10, scale = 2)
    private BigDecimal totalPrice;

    @Column(nullable = false, columnDefinition = "TIMESTAMP")
    @Builder.Default
    private Timestamp createDate = Timestamp.valueOf(LocalDateTime.now());

    @Column(columnDefinition = "TIMESTAMP")
    private Timestamp updatedDate;

    @Column(nullable = false, columnDefinition = "TINYINT default 1")
    @Builder.Default
    private Byte isActive = 1;


}