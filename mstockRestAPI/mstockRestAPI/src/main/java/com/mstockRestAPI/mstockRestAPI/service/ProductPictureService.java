package com.mstockRestAPI.mstockRestAPI.service;

import com.mstockRestAPI.mstockRestAPI.dto.ProductPictureDto;
import com.mstockRestAPI.mstockRestAPI.exception.SqlProcessException;

import java.util.List;

public interface ProductPictureService {
    ProductPictureDto add(ProductPictureDto productPictureDto) throws SqlProcessException;
    ProductPictureDto update(ProductPictureDto productPictureDto);
    List<ProductPictureDto> findAll();
    List<ProductPictureDto> findByIsActive(byte isActive);

}
