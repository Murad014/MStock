package com.mstockRestAPI.mstockRestAPI.entity;


import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jdk.jfr.Timespan;
import lombok.*;

import java.sql.Time;
import java.sql.Timestamp;
import java.time.Instant;
import java.time.LocalDateTime;
import java.util.List;


@Entity
@Table(name = "receipts")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Receipt {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(name = "typePayment", nullable = false)
    private PaymentType typePayment;

    @Column(columnDefinition = "TIMESTAMP")
    private Timestamp updatedDate;

    @Column
    private Long customerId;

    @Column(nullable = false, columnDefinition = "TIMESTAMP")
    @Builder.Default
    private Timestamp createdDate = Timestamp.valueOf(LocalDateTime.now());

    private PaymentType paymentType;

    @Column(nullable = false, columnDefinition = "TINYINT default 1")
    @Builder.Default
    private Byte isActive = 1;


}