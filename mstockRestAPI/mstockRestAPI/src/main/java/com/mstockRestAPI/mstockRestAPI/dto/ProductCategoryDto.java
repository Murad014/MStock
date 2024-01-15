package com.mstockRestAPI.mstockRestAPI.dto;



import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.sql.Timestamp;
import java.time.Instant;

@Getter
@Setter
@ToString
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProductCategoryDto {
        private Long id;

        @NotEmpty
        @NotNull
        @Size(min = 2, max = 100, message = "Product category name - min: 2, max: 100")
        private String categoryName;

        private String description;

        @Builder.Default
        private Timestamp updatedDate = Timestamp.from(Instant.now());

        @Builder.Default
        private Byte isActive = 1;
}
