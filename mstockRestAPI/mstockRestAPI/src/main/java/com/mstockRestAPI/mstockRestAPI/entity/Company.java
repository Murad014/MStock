package com.mstockRestAPI.mstockRestAPI.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.sql.Time;
import java.sql.Timestamp;
import java.time.Instant;

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

    @Column(name="createdDate")
    private Timestamp createdDate;

    @Column(name="updatedDate")
    private Timestamp updatedDate = Timestamp.from(Instant.now());

    @Column(name="isActive")
    private Byte isActive = 1;

}
