package com.mstockRestAPI.mstockRestAPI.tools.creator;

import com.mstockRestAPI.mstockRestAPI.dto.ProductDto;
import com.mstockRestAPI.mstockRestAPI.dto.ProductSalePriceDto;
import com.mstockRestAPI.mstockRestAPI.entity.ProductSalePrice;
import com.mstockRestAPI.mstockRestAPI.tools.utils.Util;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.List;
import java.util.stream.Stream;

public class ProductSalePricesCreator {
    private static  final Timestamp updateDate = Timestamp.valueOf("2023-12-03 17:48:52.08375");
    private static final int CREATE_SIZE = 10;

    public static ProductSalePrice entity(){
        byte isActive = (byte) (Math.random() * 2);
        BigDecimal sellingPrice = BigDecimal.valueOf(Util.generateRandomPrice(1, 200));
        return ProductSalePrice.builder()
                .sellingPrice(sellingPrice)
                .isActive(isActive)
                .build();
    }

    public static ProductSalePriceDto dto(){
        byte isActive = (byte) (Math.random() * 2);
        BigDecimal sellingPrice = BigDecimal.valueOf(Util.generateRandomPrice(1, 200));
        return ProductSalePriceDto.builder()
                .sellingPrice(sellingPrice)
                .isActive(isActive)
                .build();
    }

    public static List<ProductSalePrice> entityList(){
        return Stream
                .generate(ProductSalePricesCreator::entity)
                .limit(CREATE_SIZE)
                .toList();
    }

    public static List<ProductSalePriceDto> dtoList(){
        return Stream
                .generate(ProductSalePricesCreator:: dto)
                .limit(CREATE_SIZE)
                .toList();
    }



}
