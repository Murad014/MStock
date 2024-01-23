package com.mstockRestAPI.mstockRestAPI.tools.creator;

import com.mstockRestAPI.mstockRestAPI.dto.ProductCategoryDto;
import com.mstockRestAPI.mstockRestAPI.entity.ProductCategory;
import net.bytebuddy.utility.RandomString;

import java.sql.Timestamp;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class ProductCategoryCreator {
    private static  final Timestamp CREATED_DATE = Timestamp.valueOf("2023-12-03 17:48:52.083725");
    private static final int NUMBER_OF_CATEGORIES = 10;
    private static final int CATEGORY_RANDOM_NAME_LENGTH = 10;
    private static final int CATEGORY_RANDOM_DESCRIPTION_LENGTH = 10;


    public static ProductCategory createRandomProductCategoryEntity(){
        return ProductCategory.builder()
                .categoryName(RandomString.make(CATEGORY_RANDOM_NAME_LENGTH))
                .description(RandomString.make(CATEGORY_RANDOM_DESCRIPTION_LENGTH))
                .isActive((byte)1)
                .build();
    }


    public static List<ProductCategory> createProductCategoryEntities() {
        return Stream.generate(ProductCategoryCreator::createRandomProductCategoryEntity)
                .limit(NUMBER_OF_CATEGORIES)
                .collect(Collectors.toList());
    }

    public static ProductCategoryDto createProductCategoryDto(){
        Byte isActive = (byte) (Math.random() * 2);
        return ProductCategoryDto.builder()
                .categoryName(RandomString.make(CATEGORY_RANDOM_NAME_LENGTH))
                .description(RandomString.make(CATEGORY_RANDOM_DESCRIPTION_LENGTH))
                .isActive(isActive)
                .build();
    }

    public static List<ProductCategoryDto> createProductCategoryDtoList(){
        return Stream.generate(ProductCategoryCreator:: createProductCategoryDto)
                .limit(NUMBER_OF_CATEGORIES)
                .toList();
    }

}
