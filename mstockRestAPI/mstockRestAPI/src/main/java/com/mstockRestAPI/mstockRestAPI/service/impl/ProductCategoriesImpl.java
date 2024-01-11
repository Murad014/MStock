package com.mstockRestAPI.mstockRestAPI.service.impl;

import com.mstockRestAPI.mstockRestAPI.dto.ProductCategoryDto;
import com.mstockRestAPI.mstockRestAPI.entity.ProductCategory;
import com.mstockRestAPI.mstockRestAPI.repository.ProductCategoryRepository;
import com.mstockRestAPI.mstockRestAPI.service.ProductCategories;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public class ProductCategoriesImpl implements ProductCategories {

    private final ProductCategoryRepository productCategoryRepository;

    @Autowired
    public ProductCategoriesImpl(ProductCategoryRepository productCategoryRepository){
        this.productCategoryRepository = productCategoryRepository;
    }


    @Override
    public ProductCategory add(ProductCategoryDto productCategoryDto) {
        return null;
    }

    @Override
    public ProductCategory update(ProductCategoryDto productCategoryDto) {
        return null;
    }

    @Override
    public List<ProductCategory> getAll() {
        return null;
    }

    @Override
    public ProductCategoryDto getById(Long id) {
        return null;
    }

    @Override
    public void setIsActiveFalseById(Long id) {

    }
}
