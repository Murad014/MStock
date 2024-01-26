package com.mstockRestAPI.mstockRestAPI.dto;


import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.sql.Timestamp;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProductBarcodeDto {
    private Long id;

    @NotBlank(message = "Barcode cannot be blank")
    private String barcode;

    @Builder.Default
    private Byte isActive = 1;

}