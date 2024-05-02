package com.mstockRestAPI.mstockRestAPI.entity;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jdk.jfr.Timespan;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

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
public class SupplierOfProduct extends BaseEntity{

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

    @Column(columnDefinition = "TEXT")
    private String address;

    @Column(columnDefinition = "TEXT default null")
    private String comment;

    @ManyToOne(fetch = FetchType.EAGER, cascade = {
            CascadeType.PERSIST,
            CascadeType.REFRESH,
            CascadeType.DETACH,
            CascadeType.MERGE
    })
    @JoinColumn(name="company_id")
    private Company company;


    @Column(nullable = false, columnDefinition = "TINYINT default 1")
    @Builder.Default
    private Byte isActive = 1;

}
