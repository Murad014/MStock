package com.mstockRestAPI.mstockRestAPI.dto;


import com.mstockRestAPI.mstockRestAPI.validation.UniqueBarcode;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.sql.Timestamp;

@Getter
@Setter
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class ProductBarcodeDto {
    private Long id;

    @NotBlank(message = "Barcode cannot be blank")
    @UniqueBarcode
    private String barcode;

    @Builder.Default
    private Byte isActive = 1;

}