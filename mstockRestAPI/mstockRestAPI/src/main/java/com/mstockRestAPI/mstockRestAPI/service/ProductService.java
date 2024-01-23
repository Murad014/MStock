package com.mstockRestAPI.mstockRestAPI.service;

import com.mstockRestAPI.mstockRestAPI.dto.ProductDto;
import com.mstockRestAPI.mstockRestAPI.entity.Product;

import java.util.List;

public interface ProductService {
    ProductDto add(ProductDto productDto);
    ProductDto update(ProductDto productDto);
    List<ProductDto> getAll();
    ProductDto getById(Long id);
    ProductDto getByBarcodeAndIsActive(String barcode, byte isActive);
    ProductDto getAllAndIsActive(byte isActive);
    List<ProductDto> getListByProductNameAndIsActive(String productName, byte isActive);
}
