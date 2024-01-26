package com.mstockRestAPI.mstockRestAPI.dto;

import jakarta.persistence.Entity;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

import jakarta.persistence.*;
import lombok.*;
import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.sql.Blob;
import java.sql.Time;
import java.sql.Timestamp;
import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDateTime;



@Getter
@Setter
@ToString
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProductPictureDto {

    private Long id;

    @NotBlank(message = "Picture name cannot be blank")
    private String pictureName;

    @Builder.Default
    private Byte isActive = 1;

}
