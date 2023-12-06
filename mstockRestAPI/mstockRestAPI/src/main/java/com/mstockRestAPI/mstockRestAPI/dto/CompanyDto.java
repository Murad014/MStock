package com.mstockRestAPI.mstockRestAPI.dto;

import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.sql.Time;
import java.sql.Timestamp;
import java.time.Instant;
import java.time.LocalDateTime;

@Getter
@Setter
@ToString
@Builder
public class CompanyDto {
    private Long id;

    @NotEmpty
    @Size(min = 2, max = 100, message = "Company name: min: 2, max: 100")
    private String companyName;

    @NotEmpty
    private Timestamp updatedDate;

    @Builder.Default
    private Byte isActive = 1;
}
