package com.mstockRestAPI.mstockRestAPI.service.impl;

import com.mstockRestAPI.mstockRestAPI.dto.*;
import com.mstockRestAPI.mstockRestAPI.payload.converter.Converter;
import com.mstockRestAPI.mstockRestAPI.entity.Product;
import com.mstockRestAPI.mstockRestAPI.exception.SqlProcessException;
import com.mstockRestAPI.mstockRestAPI.repository.*;
import com.mstockRestAPI.mstockRestAPI.service.*;
import com.mstockRestAPI.mstockRestAPI.utils.Util;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Service
public class ProductServiceImpl implements ProductService {
    @Value("${upload.directory}") // Specify the directory where you want to save the uploaded files
    private String uploadDirectory;
    private final ProductRepository productRepository;
    private final ProductBarcodeService productBarcodeService;
    private final ProductSalePriceService productSalePriceService;
    private final CompanyService companyService;
    private final ProductCategoriesService productCategoriesService;
    private final Converter converter;

    @Autowired
    public ProductServiceImpl(
            ProductRepository productRepository,
            ProductBarcodeService productBarcodeService,
            ProductSalePriceService productSalePriceService,
            CompanyService companyService,
            ProductCategoriesService productCategoriesService,
            Converter converter
    ){
        this.productRepository = productRepository;
        this.productBarcodeService = productBarcodeService;
        this.productSalePriceService = productSalePriceService;
        this.companyService = companyService;
        this.productCategoriesService = productCategoriesService;
        this.converter = converter;

    }

    @Override
    @Transactional
    public ProductDto add(ProductDto productDto, List<MultipartFile> productPictures) throws SqlProcessException {
        if(productDto.getId() != null)
            throw new SqlProcessException("Product", "add", "product id must be null");

        for(MultipartFile file: productPictures) {
            if (file != null && !file.isEmpty()) {
                String imageName = Util.handleFileUpload(file, uploadDirectory);
                productDto.setPictureName(imageName);
            }
        }
        saveProductRelations(productDto);
        Product entity = converter.mapToEntity(productDto, Product.class);
        Product productSave = productRepository.save(entity);

        return converter.mapToDto(productSave, ProductDto.class);
    }

    @Override
    public ProductDto update(ProductDto productDto, MultipartFile file) {
        return null;
    }

    @Override
    public List<ProductDto> getAll() {
        return productRepository.findAll()
                .stream()
                .map(product -> converter.mapToDto(product, ProductDto.class))
                .toList();

    }

    @Override
    public ProductDto getById(Long id) {
        return null;
    }

    @Override
    public ProductDto getByBarcodeAndIsActive(String barcode, byte isActive) {
        return null;
    }

    @Override
    public ProductDto getAllAndIsActive(byte isActive) {
        return null;
    }

    @Override
    public List<ProductDto> getListByProductNameAndIsActive(String productName, byte isActive) {
        return null;
    }



    private void saveProductRelations(ProductDto productDto){
//        productDto.setCompanyDto(companyService.add(productDto.getCompanyDto()));
//        productDto.setProductBarcodeList(productBarcodeService.addList(productDto.getProductBarcodeList()));
//        productSalePriceService.addList(productDto.getProductSalePrices());
//        productDto.setProductCategoryDto(productCategoriesService.add(productDto.getProductCategoryDto()));

    }



}
