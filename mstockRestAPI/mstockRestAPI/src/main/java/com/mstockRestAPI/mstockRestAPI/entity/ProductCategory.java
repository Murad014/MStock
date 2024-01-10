package com.mstockRestAPI.mstockRestAPI.entity;

import jakarta.persistence.*;
import lombok.*;

import java.sql.Timestamp;

@Getter
@Setter
@ToString
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "productCategories")
public class ProductCategory {

    @Id
    @Column(name="id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name="categoryName", unique = true, nullable = false, length = 100)
    private String categoryName;

    @Column(name = "description")
    private String description;

    @Column(name = "createDate")
    private Timestamp createDate;

    @Column(name="updatedDate")
    private Timestamp updatedDate;

    @Column(name="isActive")
    private Byte isActive;



}
