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
@Table(name = "supplieriesOfProdcuts")
@Getter
@Setter
@ToString
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SupplierOfProduct {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 100)
    private String name;

    @Column(length = 100)
    private String surname;

    @Column(unique = true, length = 100)
    private String email;

    @Column(unique = true, length = 100)
    private String phone;

    @Column(columnDefinition = "TEXT")
    private String description;

    @Column(unique = true, columnDefinition = "TEXT default null")
    private String address;

    @Column(nullable = false)
    private Long companyId;

    @Column(columnDefinition = "TIMESTAMP")
    @Builder.Default
    private Timestamp createdDate = Timestamp.valueOf(LocalDateTime.now());

    @Column(columnDefinition = "TIMESTAMP")
    private LocalDateTime updatedDate;

    @Column(nullable = false, columnDefinition = "TINYINT default 1")
    @Builder.Default
    private Byte isActive = 1;



}
