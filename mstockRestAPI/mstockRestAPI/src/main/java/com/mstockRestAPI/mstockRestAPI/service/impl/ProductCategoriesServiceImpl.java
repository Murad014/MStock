package com.mstockRestAPI.mstockRestAPI.service.impl;

import com.mstockRestAPI.mstockRestAPI.dto.ProductCategoryDto;
import com.mstockRestAPI.mstockRestAPI.dto.converter.Converter;
import com.mstockRestAPI.mstockRestAPI.entity.ProductCategory;
import com.mstockRestAPI.mstockRestAPI.exception.ResourceNotFoundException;
import com.mstockRestAPI.mstockRestAPI.repository.ProductCategoryRepository;
import com.mstockRestAPI.mstockRestAPI.service.ProductCategoriesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductCategoriesServiceImpl implements ProductCategoriesService {

    private final ProductCategoryRepository productCategoryRepository;

    private final Converter converter;

    @Autowired
    public ProductCategoriesServiceImpl(ProductCategoryRepository productCategoryRepository,
                                        Converter converter){
        this.productCategoryRepository = productCategoryRepository;
        this.converter = converter;
    }


    @Override
    public ProductCategoryDto add(ProductCategoryDto productCategoryDto) {
        ProductCategory productCategory = productCategoryRepository.save(
                converter.mapToEntity(productCategoryDto, ProductCategory.class)
        );

        return converter.mapToDto(productCategory, ProductCategoryDto.class);
    }

    @Override
    public ProductCategoryDto update(ProductCategoryDto productCategoryDto) {
        // Check exists or not
        Long existsId = productCategoryDto.getId();
        productCategoryRepository.findById(existsId).orElseThrow(
                () -> new ResourceNotFoundException("Product Category", "id", existsId.toString())
        );

        ProductCategory updateProductCategory = productCategoryRepository.save(
                converter.mapToEntity(productCategoryDto, ProductCategory.class));

        return converter.mapToDto(updateProductCategory, ProductCategoryDto.class);
    }

    @Override
    public List<ProductCategoryDto> getAll() {
        List<ProductCategory> allCategoriesFromDB = productCategoryRepository.findAll();
        return allCategoriesFromDB.stream()
                .map(c ->
                        converter.mapToDto(c, ProductCategoryDto.class)).toList();
    }

    @Override
    public ProductCategoryDto getById(Long id) {
        ProductCategory productCategoryDto = productCategoryRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("Product Category", "id", id.toString())
        );
        return converter.mapToDto(productCategoryDto, ProductCategoryDto.class);
    }

    @Override
    public ProductCategoryDto findByCategoryName(String categoryName) {
        ProductCategory productCategoryDto = productCategoryRepository.findByCategoryName(categoryName).orElseThrow(
                () -> new ResourceNotFoundException("Product Category", "categoryName", categoryName)
        );
        return converter.mapToDto(productCategoryDto, ProductCategoryDto.class);
    }

    @Override
    public boolean existsByCategoryName(String categoryName) {
        return productCategoryRepository.existsByCategoryName(categoryName);
    }

    @Override
    public List<ProductCategoryDto> findByIsActive(Byte isActive) {
        return productCategoryRepository.findByIsActive(isActive)
                .stream()
                .map(entity ->
                        converter.mapToDto(entity, ProductCategoryDto.class))
                .toList();
    }

}
