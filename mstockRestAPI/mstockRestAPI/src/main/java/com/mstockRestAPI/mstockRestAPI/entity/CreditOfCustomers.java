package com.mstockRestAPI.mstockRestAPI.entity;


import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.List;

@Entity
@Table(name = "creditOfCustomers")
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CreditOfCustomers extends BaseEntity{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "givenAmount", precision = 10, scale = 2)
    private BigDecimal givenAmount;

    @ManyToOne(fetch = FetchType.EAGER, cascade = {
            CascadeType.PERSIST,
            CascadeType.REFRESH,
            CascadeType.MERGE
    })
    @JoinColumn(name="customer_id")
    private Customer customer;

    @Column(name = "totalAmount", precision = 10, scale = 2)
    private BigDecimal totalAmount;

    @Column(name = "percentageFee", precision = 10, scale = 2)
    private BigDecimal percentageFee;

    @Column(name = "commissionAmount", precision = 10, scale = 2)
    private BigDecimal commissionAmount;

    @Column(name = "monthlyInstallment", precision = 10, scale = 2)
    private BigDecimal monthlyInstallment;

    @Column(name = "creditMonths")
    private Integer creditMonths;

    @Column(name = "finePercentage", precision = 10, scale = 2)
    private BigDecimal finePercentage;

    @Column(name = "dueDate")
    private Timestamp dueDate;

    @OneToMany
    @JoinColumn(name="paymentCustomerCredit_id")
    private List<PaymentCustomerCredit> payments;

    @Column(name = "installmentCount")
    @Builder.Default
    private Integer installmentCount = 0;

    @Column(name = "closed")
    private Boolean closed;

    @Column(nullable = false, columnDefinition = "TINYINT default 1")
    @Builder.Default
    private Byte isActive = 1;
}
