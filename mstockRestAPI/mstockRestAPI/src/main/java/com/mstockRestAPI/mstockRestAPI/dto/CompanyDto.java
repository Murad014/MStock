package com.mstockRestAPI.mstockRestAPI.dto;

import com.mstockRestAPI.mstockRestAPI.validation.UniqueCompanyName;
import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.sql.Time;
import java.sql.Timestamp;
import java.time.Instant;
import java.time.LocalDateTime;

@Getter
@Setter
@ToString
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CompanyDto {
    private Long id;

    @NotEmpty
    @UniqueCompanyName
    @Size(min = 2, max = 100, message = "Company name: min: 2, max: 100")
    private String companyName;

    @Builder.Default
    private Byte isActive = 1;
}
