package com.mstockRestAPI.mstockRestAPI.entity;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jdk.jfr.Timespan;
import lombok.*;

import java.math.BigDecimal;
import java.sql.Time;
import java.sql.Timestamp;
import java.time.Instant;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "customers")
@Getter
@Setter
@ToString
@Builder
public class Customer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name="name")
    private String name;

    @Column(name="surname")
    private String surname;

    @Column(name="email")
    private String email;

    @Column(name="phone")
    private String phone;

    @Column(name="comment", columnDefinition =  "TEXT")
    private String comment;

    @Column(name="createdDate", nullable = false)
    @Builder.Default
    private Timestamp createdDate = Timestamp.valueOf(LocalDateTime.now());

    @Column(name="bonusRate", columnDefinition = "DECIMAL(10, 2) DEFAULT 0.00")
    private BigDecimal bonusRate;

    @Column(nullable = false)
    private Timestamp updatedDate;

    @Column(columnDefinition = "TINYINT DEFAULT 1")
    @Builder.Default
    private Byte isActive = 1;
}