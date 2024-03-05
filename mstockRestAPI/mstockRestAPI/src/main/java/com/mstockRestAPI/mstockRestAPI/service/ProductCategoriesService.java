package com.mstockRestAPI.mstockRestAPI.service;

import com.mstockRestAPI.mstockRestAPI.dto.ProductCategoryDto;
import com.mstockRestAPI.mstockRestAPI.entity.ProductCategory;
import com.mstockRestAPI.mstockRestAPI.payload.response.SuccessResponse;

import java.util.*;

public interface ProductCategoriesService {
    ProductCategoryDto add(ProductCategoryDto productCategoryDto);
    ProductCategoryDto update(Long productId, ProductCategoryDto productCategoryDto);
    List<ProductCategoryDto> getAll();
    ProductCategoryDto getById(Long id);
    ProductCategoryDto findByCategoryName(String categoryName);
    boolean existsByCategoryName(String categoryName);
    List<ProductCategoryDto> findByIsActive(Byte isActive);
    SuccessResponse updateIsActiveById(Long categoryId, Byte isActive);



}
