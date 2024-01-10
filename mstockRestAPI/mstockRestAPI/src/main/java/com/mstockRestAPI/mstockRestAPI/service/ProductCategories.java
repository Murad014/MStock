package com.mstockRestAPI.mstockRestAPI.service;

import com.mstockRestAPI.mstockRestAPI.dto.ProductCategoryDto;
import com.mstockRestAPI.mstockRestAPI.entity.ProductCategory;

import java.util.*;

public interface ProductCategories {
    ProductCategory add(ProductCategoryDto productCategoryDto);
    ProductCategory update(ProductCategoryDto productCategoryDto);
    List<ProductCategory> getAll();
    ProductCategoryDto getById(Long id);
    void setIsActiveFalseById(Long id);

}
