package com.mstockRestAPI.mstockRestAPI.tools.creator;

import com.mstockRestAPI.mstockRestAPI.dto.ProductSaleDto;
import com.mstockRestAPI.mstockRestAPI.entity.Product;

import com.mstockRestAPI.mstockRestAPI.entity.ProductMovements;
import com.mstockRestAPI.mstockRestAPI.entity.SaleReceipt;
import com.mstockRestAPI.mstockRestAPI.enums.SaleStatus;
import com.mstockRestAPI.mstockRestAPI.tools.utils.Util;
import net.bytebuddy.utility.RandomString;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Stream;

public class ProductMovementCreator {

    private static final int NUMBER_OF_PRODUCT_SALE= 10;
    private static final int PRODUCT_RANDOM_DESCRIPTION_LENGTH = 100;

    public static ProductMovements entity(){
        Product productEntity = ProductCreator.entity();
        SaleReceipt receipt = SaleReceiptCreator.entity();
        receipt.setId(1L);
        return ProductMovements.builder()
                .product(productEntity)
                .quantity(BigDecimal.valueOf(Util.generateRandomPrice(1, 607)))
                .salePrice(BigDecimal.valueOf(Util.generateRandomPrice(1, 1000)))
                .discountPercent(BigDecimal.valueOf(Util.generateRandomPrice(1, 1000)))
                .discountDecimal(BigDecimal.valueOf(Util.generateRandomPrice(1, 1000)))
                .comment(RandomString.make(PRODUCT_RANDOM_DESCRIPTION_LENGTH))
                .saleStatus(Util.chooseRandomEnum(SaleStatus.class))
                .receipt(receipt)
                .build();
    }

    public static List<ProductMovements> entityList(){
        return Stream.generate(ProductMovementCreator::entity)
                .limit(NUMBER_OF_PRODUCT_SALE)
                .toList();
    }

    public static ProductSaleDto dto(){
        return null;
    }







}
