package com.mstockRestAPI.mstockRestAPI.tools.creator;

import com.mstockRestAPI.mstockRestAPI.entity.ProductCategory;
import net.bytebuddy.utility.RandomString;
import org.apache.commons.lang3.RandomStringUtils;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class ProductCategoryCreator {
    private static  final Timestamp CREATED_DATE = Timestamp.valueOf("2023-12-03 17:48:52.083725");
    private static final int NUMBER_OF_CATEGORIES = 3;
    private static final int CATEGORY_RANDOM_NAME_LENGTH = 10;
    private static final int CATEGORY_RANDOM_DESCRIPTION_LENGTH = 10;


    public static ProductCategory createRandomProductCategory(){
        return ProductCategory.builder()
                .categoryName(RandomString.make(CATEGORY_RANDOM_NAME_LENGTH))
                .description(RandomString.make(CATEGORY_RANDOM_DESCRIPTION_LENGTH))
                .createDate(CREATED_DATE)
                .isActive((byte)1)
                .build();
    }


    public static List<ProductCategory> createProductCategories() {
        return Stream.generate(ProductCategoryCreator::createRandomProductCategory)
                .limit(NUMBER_OF_CATEGORIES)
                .collect(Collectors.toList());
    }

}
