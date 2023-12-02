package com.mstockRestAPI.mstockRestAPI.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.sql.Time;
import java.sql.Timestamp;
@Entity
@Table(name = "company", uniqueConstraints = {
        @UniqueConstraint(name = "uk_companyName", columnNames = "companyName")
})
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
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
    private Timestamp updatedTime;

    @Column(name="isActive")
    private Integer isActive = 1;

}
