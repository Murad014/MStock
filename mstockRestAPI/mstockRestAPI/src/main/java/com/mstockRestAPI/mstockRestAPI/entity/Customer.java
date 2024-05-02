package com.mstockRestAPI.mstockRestAPI.entity;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jdk.jfr.Timespan;
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
@Table(name = "customers")
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Customer extends BaseEntity{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name="name")
    private String name;

    @Column(name="surname")
    private String surname;

    @Column(name="idCardNumber", unique = true, nullable = false)
    private String idCardNumber;

    @Column(name="email")
    private String email;

    @Column(name="phone")
    private String phone;

    @Column(name="comment", columnDefinition =  "TEXT")
    private String comment;

    @Column(name="bonusRate", columnDefinition = "DECIMAL(10, 2) DEFAULT 0.00")
    private BigDecimal bonusRate;

    @OneToMany(fetch = FetchType.EAGER, cascade = {
            CascadeType.PERSIST,
            CascadeType.REFRESH,
    })
    @JoinColumn(name = "CreditOfCustomers_id")
    private List<CreditOfCustomers> credits;

    @Column(columnDefinition = "TINYINT DEFAULT 1")
    @Builder.Default
    private Byte isActive = 1;


}