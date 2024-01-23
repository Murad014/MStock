package com.mstockRestAPI.mstockRestAPI.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.sql.Time;
import java.sql.Timestamp;
import java.time.Instant;
import java.util.List;

@Entity
@Table(name = "company", uniqueConstraints = {
        @UniqueConstraint(name = "uk_companyName", columnNames = "companyName")
})
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class Company {
    @Id
    @Column(name="id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "companyName", unique = true, nullable = false, length = 100)
    private String companyName;

    @Column(name = "createdDate", updatable = false)
    @CreationTimestamp
    private Timestamp createdDate;

    @Column(name = "updatedDate")
    @UpdateTimestamp
    private Timestamp updatedDate;

    @OneToMany(mappedBy = "company", cascade = CascadeType.ALL)
    private List<Product> products;


    @Column(name="isActive")
    @Builder.Default
    private Byte isActive = 1;

}
