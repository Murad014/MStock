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
@ToString
public class Company extends BaseEntity{
    @Id
    @Column(name="id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "companyName", unique = true, nullable = false, length = 100)
    private String companyName;

    @Column(name="isActive")
    private Byte isActive = 1;

}
