package com.mstockRestAPI.mstockRestAPI.dto;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.math.BigDecimal;
import java.sql.Timestamp;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CustomerDto {
    private Long id;

    @Valid
    @NotEmpty(message = "Customer name cannot be null")
    @NotNull(message = "Customer name cannot be empty")
    private String name;
    private String surname;

    @Valid
    @Email
    private String email;

    private String phone;
    private String comment;
    private BigDecimal bonusRate;

    @Builder.Default
    private Byte isActive = 1;
}