package com.mstockRestAPI.mstockRestAPI.service;

import com.mstockRestAPI.mstockRestAPI.dto.ProductDto;
import com.mstockRestAPI.mstockRestAPI.entity.Product;

import java.util.List;

public interface ProductService {
    List<ProductDto> addAll(List<ProductDto> productDtoList);
    Product add(ProductDto productDto);
    Product update(ProductDto productDto);
    List<ProductDto> getAll();
    ProductDto getById();
    Product getByBarcode();
    Product getAllWhereIsActive(byte isActive);


}
