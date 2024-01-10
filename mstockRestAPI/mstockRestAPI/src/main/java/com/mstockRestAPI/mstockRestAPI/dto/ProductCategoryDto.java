package com.mstockRestAPI.mstockRestAPI.dto;



import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

import java.sql.Timestamp;

public class ProductCategoryDto {
        private Long id;

        @NotEmpty
        @NotNull
        private String categoryName;
        private String description;
        private Timestamp createDate;
        private Timestamp updatedDate;
        private Byte isActive;
}
