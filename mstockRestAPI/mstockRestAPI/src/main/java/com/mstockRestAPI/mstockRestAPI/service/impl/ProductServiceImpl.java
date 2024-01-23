package com.mstockRestAPI.mstockRestAPI.service.impl;

import com.mstockRestAPI.mstockRestAPI.dto.ProductDto;
import com.mstockRestAPI.mstockRestAPI.repository.ProductRepository;
import com.mstockRestAPI.mstockRestAPI.service.ProductService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductServiceImpl implements ProductService {

//    private final ProductRepository productRepository;
//    private final ProductB

    @Override
    public ProductDto add(ProductDto productDto) {





        return null;
    }

    @Override
    public ProductDto update(ProductDto productDto) {
        return null;
    }

    @Override
    public List<ProductDto> getAll() {
        return null;
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
}
