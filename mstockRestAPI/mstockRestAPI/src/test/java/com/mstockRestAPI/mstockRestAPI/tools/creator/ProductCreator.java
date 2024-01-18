package com.mstockRestAPI.mstockRestAPI.tools.creator;

import com.mstockRestAPI.mstockRestAPI.entity.Product;
import com.mstockRestAPI.mstockRestAPI.entity.ProductCategory;
import com.mstockRestAPI.mstockRestAPI.tools.utils.Util;
import net.bytebuddy.utility.RandomString;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class ProductCreator {

    private static final int NUMBER_OF_PRODUCTS= 10;
    private static final int CATEGORY_PRODUCT_NAME_LENGTH = 10;
    private static final int PRODUCT_RANDOM_DESCRIPTION_LENGTH = 10;
    private static  final Timestamp createdDate = Timestamp.valueOf("2023-12-03 17:48:52.083725");


    public static Product entity(){
        BigDecimal wholesale = BigDecimal.valueOf(Util.generateRandomPrice(1.00, 1000.00));
        BigDecimal quantity = BigDecimal.valueOf(Util.generateRandomPrice(1.00, 1000.00));
        byte isActive = (byte) (Math.random() * 2);

        ProductCategory productCategory = ProductCategoryCreator.createRandomProductCategoryEntity();
        productCategory.setId(1L);
        return Product.builder()
                .productName(RandomString.make(CATEGORY_PRODUCT_NAME_LENGTH))
                .description(RandomString.make(PRODUCT_RANDOM_DESCRIPTION_LENGTH))
                .wholesale(wholesale)
                .unit(chooseRandomUnit())
                .company(CompanyCreator.createCompanyEntity())
                .quantity(quantity)
                .currentQuantity(quantity)
                .expiredDate(Timestamp.valueOf(createdDate.toLocalDateTime().plusDays(20)))
                .category(productCategory)
                .createdDate(createdDate)
                .updatedDate(createdDate)
                .isActive(isActive)
                .build();
    }

    public static List<Product> entityList(){
        return Stream.generate(ProductCreator:: entity)
                .limit(NUMBER_OF_PRODUCTS)
                .collect(Collectors.toList());
    }


    private static Product.Unit chooseRandomUnit(){
        Product.Unit[] values = Product.Unit.values();
        Random random = new Random();
        int index = random.nextInt(values.length);

        return values[index];
    }



}