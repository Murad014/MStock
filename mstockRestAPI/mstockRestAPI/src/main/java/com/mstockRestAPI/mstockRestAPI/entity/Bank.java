package com.mstockRestAPI.mstockRestAPI.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "banks")
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Bank extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "bankName", nullable = false, unique = true)
    private String bankName;

    @Column(name = "isActive", columnDefinition = "TINYINT DEFAULT 1")
    @Builder.Default
    private Byte isActive = 1;
}
