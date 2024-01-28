package com.mstockRestAPI.mstockRestAPI.service;

import com.mstockRestAPI.mstockRestAPI.dto.ProductDto;
import com.mstockRestAPI.mstockRestAPI.entity.Product;
import com.mstockRestAPI.mstockRestAPI.exception.SqlProcessException;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface ProductService {
    ProductDto add(ProductDto productDto) throws SqlProcessException;
    ProductDto update(ProductDto productDto) throws SqlProcessException;
    List<ProductDto> getAll();
    ProductDto getById(Long id);
    ProductDto getByBarcodeAndIsActive(String barcode, byte isActive);
    List<ProductDto> getAllAndIsActive(byte isActive);
    List<ProductDto> getListByProductNameAndIsActive(String productName, byte isActive);
}
