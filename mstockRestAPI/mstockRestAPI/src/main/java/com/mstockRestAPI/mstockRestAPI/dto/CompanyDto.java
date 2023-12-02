package com.mstockRestAPI.mstockRestAPI.dto;

import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

import java.sql.Time;
import java.sql.Timestamp;
import java.time.Instant;
import java.time.LocalDateTime;

@Getter
@Setter
public class CompanyDto {
    private Long id;
    @NotEmpty
    @Size(max = 100, min = 2)
    private String companyName;
    private Timestamp updatedTime = Timestamp.from(Instant.now());
    private Integer isActive = 1;
}
