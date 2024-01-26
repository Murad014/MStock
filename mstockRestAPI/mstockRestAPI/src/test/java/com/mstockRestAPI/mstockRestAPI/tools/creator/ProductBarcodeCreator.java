package com.mstockRestAPI.mstockRestAPI.tools.creator;

import com.mstockRestAPI.mstockRestAPI.dto.ProductBarcodeDto;
import com.mstockRestAPI.mstockRestAPI.entity.Product;
import com.mstockRestAPI.mstockRestAPI.entity.ProductBarcode;
import com.mstockRestAPI.mstockRestAPI.entity.ProductCategory;
import com.mstockRestAPI.mstockRestAPI.tools.utils.Util;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.*;
import java.util.Random;
import java.util.stream.*;

public class ProductBarcodeCreator {
    private static final int GENERATE_LIMIT = 10;

    public static ProductBarcode entity(){
        Byte isActive = (byte) (Math.random() * 2);
        return ProductBarcode.builder()
                .barcode(Util.generateRandomBarcode(13))
                .isActive(isActive)
                .build();
    }

    public static List<ProductBarcode> entityList(){
        return Stream.generate(ProductBarcodeCreator::entity)
                .limit(GENERATE_LIMIT)
                .toList();
    }

    public static ProductBarcodeDto dto(){
        Byte isActive = (byte) (Math.random() * 2);
        return ProductBarcodeDto.builder()
                .barcode(Util.generateRandomBarcode(13))
                .isActive(isActive)
                .build();
    }

    public static List<ProductBarcodeDto> dtoList(){
        return Stream.generate(ProductBarcodeCreator::dto)
                .limit(GENERATE_LIMIT)
                .toList();
    }





}
