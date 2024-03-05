package com.mstockRestAPI.mstockRestAPI.service;

import com.mstockRestAPI.mstockRestAPI.dto.ProductDto;
import com.mstockRestAPI.mstockRestAPI.entity.Product;
import com.mstockRestAPI.mstockRestAPI.exception.SqlProcessException;
import com.mstockRestAPI.mstockRestAPI.payload.request.ProductSearchKeys;
import com.mstockRestAPI.mstockRestAPI.payload.response.ProductResponse;
import com.mstockRestAPI.mstockRestAPI.payload.response.SuccessResponse;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface ProductService {
    ProductDto add(ProductDto productDto) throws SqlProcessException;
    ProductDto update(Long productId, ProductDto productDto) throws SqlProcessException;
    SuccessResponse deActiveById(Long productId, Byte isActive) throws SqlProcessException;
    List<ProductDto> getAll();
    List<ProductDto> getAllAndIsActive(byte isActive);
    ProductDto getById(Long id);
    ProductDto getByBarcodeAndIsActive(String barcode, byte isActive);
    List<ProductDto> getListByProductNameAndIsActive(String productName, byte isActive);
    ProductDto addPictures(Long productId, List<MultipartFile> pictures);

    ProductResponse searchAndPagination(ProductSearchKeys productSearchKeys);
}
