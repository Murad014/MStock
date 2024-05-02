package com.mstockRestAPI.mstockRestAPI.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.sql.Timestamp;
import java.util.List;

@Getter
@Setter
@ToString
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class ProductCategory extends BaseEntity{

    @Id
    @Column(name="id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name="categoryName", unique = true, nullable = false, length = 100)
    private String categoryName;

    @Column(name = "description")
    private String description;

    @OneToMany(mappedBy = "category", cascade = {
            CascadeType.PERSIST,
            CascadeType.DETACH,
            CascadeType.REFRESH,
            CascadeType.MERGE
    })
    private List<Product> products;

    @Column(name="isActive")
    private Byte isActive;



}
