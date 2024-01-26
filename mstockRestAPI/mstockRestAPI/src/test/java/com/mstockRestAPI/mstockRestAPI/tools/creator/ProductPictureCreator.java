package com.mstockRestAPI.mstockRestAPI.tools.creator;

import com.mstockRestAPI.mstockRestAPI.dto.ProductPictureDto;
import com.mstockRestAPI.mstockRestAPI.entity.ProductPicture;
import com.mstockRestAPI.mstockRestAPI.tools.utils.Util;

import java.util.List;
import java.util.stream.Stream;

public class ProductPictureCreator {

    private static final int GENERATE_LIMIT = 10;
    public static ProductPicture entity(){
        Byte isActive = (byte) (Math.random() * 2);
        return ProductPicture.builder()
                .pictureName(Util.generateRandomBarcode(13))
                .isActive(isActive)
                .build();
    }

    public static List<ProductPicture> entityList(){
        return Stream.generate(ProductPictureCreator:: entity)
                .limit(GENERATE_LIMIT)
                .toList();
    }


    public static ProductPictureDto dto(){
        Byte isActive = (byte) (Math.random() * 2);
        return ProductPictureDto.builder()
                .pictureName(Util.generateRandomBarcode(13))
                .isActive(isActive)
                .build();
    }

    public static List<ProductPictureDto> dtoList(){
        return Stream.generate(ProductPictureCreator:: dto)
                .limit(GENERATE_LIMIT)
                .toList();
    }


}
