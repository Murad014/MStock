package com.mstockRestAPI.mstockRestAPI.dto;

import com.mstockRestAPI.mstockRestAPI.entity.Company;
import jakarta.persistence.*;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.sql.Timestamp;


@Getter
@Setter
@ToString
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SupplierOfProductDto {
    private Long id;

    @Valid
    @NotEmpty(message = "Name cannot be empty")
    @NotNull(message = "Name cannot be null")
    private String name;

    @Valid
    @NotEmpty(message = "Surname cannot be empty")
    @NotNull(message = "Surname cannot be null")
    private String surname;

    @Valid
    @Email
    private String email;

    private String phone;
    private String address;
    private String comment;

    @NotNull(message = "cannot be null")
    @Size(min = 1)
    private Long companyId;

    @Builder.Default
    private Byte isActive = 1;

}
