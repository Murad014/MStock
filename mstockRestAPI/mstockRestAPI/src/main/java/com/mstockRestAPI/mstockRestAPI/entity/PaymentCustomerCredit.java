package com.mstockRestAPI.mstockRestAPI.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.math.BigDecimal;
import java.sql.Timestamp;

@Entity
@Table(name = "paymentCreditCustomer")
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
            CascadeType.REFRESH,
    })
    @JoinColumn(name="creditOfCustomers_id")
    private CreditOfCustomers credit;

    @OneToOne(cascade = CascadeType.PERSIST, fetch = FetchType.EAGER)
    @JoinColumn(name = "paymentExtraInfo_id", referencedColumnName = "id", nullable = false)
    private PaymentExtraInfo paymentExtraInfo;

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
