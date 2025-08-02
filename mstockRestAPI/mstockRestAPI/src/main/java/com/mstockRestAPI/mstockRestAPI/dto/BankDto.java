package com.mstockRestAPI.mstockRestAPI.dto;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class BankDto {

    private Long id;

    @NotBlank(message = "Bank name is required")
    private String bankName;

    @Min(value = 0, message = "isActive must be 0 or 1")
    @Max(value = 1, message = "isActive must be 0 or 1")
    @Builder.Default
    private byte isActive = 1;
}
