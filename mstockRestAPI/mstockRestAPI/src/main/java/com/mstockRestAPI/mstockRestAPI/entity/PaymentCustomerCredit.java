package com.mstockRestAPI.mstockRestAPI.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.math.BigDecimal;
import java.sql.Timestamp;

@Entity
@Table(name = "creditOfCustomers")
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PaymentCustomerCredit {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name="paymentAmount", precision = 10, scale = 2)
    private BigDecimal paymentAmount;

    @ManyToOne(fetch = FetchType.EAGER, cascade = {
            CascadeType.PERSIST,
            CascadeType.DETACH,
            CascadeType.REFRESH,
            CascadeType.MERGE,
    })
    @JoinColumn(name="creditOfCustomers_id")
    private CreditOfCustomers credit;


    @Column(name = "createdDate", updatable = false)
    @CreationTimestamp
    private Timestamp createdDate;

    @Column(name = "updatedDate")
    @UpdateTimestamp
    private Timestamp updatedDate;

    @Column(name="isActive")
    @Builder.Default
    private Byte isActive = 1;


}
