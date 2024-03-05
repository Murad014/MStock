package com.mstockRestAPI.mstockRestAPI.dto;


import com.mstockRestAPI.mstockRestAPI.validation.UniqueBarcode;
import jakarta.validation.Valid;
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
    @UniqueBarcode
    @Valid
    private String barcode;

    @Builder.Default
    private Byte isActive = 1;


}