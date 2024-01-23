package com.mstockRestAPI.mstockRestAPI.service.impl;

import com.mstockRestAPI.mstockRestAPI.entity.ProductSale;
import com.mstockRestAPI.mstockRestAPI.entity.ProductSalePrice;
import com.mstockRestAPI.mstockRestAPI.exception.ResourceNotFoundException;
import com.mstockRestAPI.mstockRestAPI.repository.ProductSalePricesRepository;
import com.mstockRestAPI.mstockRestAPI.service.ProductSalePriceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ProductSalePriceServiceImpl implements ProductSalePriceService {

    private final ProductSalePricesRepository productSalePricesRepository;

    @Autowired
    public ProductSalePriceServiceImpl(ProductSalePricesRepository productSalePricesRepository){
        this.productSalePricesRepository = productSalePricesRepository;
    }

    @Override
    public ProductSalePrice add(ProductSalePrice productSalePrice) {
        return productSalePricesRepository.save(productSalePrice);
    }

    @Override
    public ProductSalePrice update(ProductSalePrice productSalePrice) {
        Long id = productSalePrice.getId();
        productSalePricesRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Product sale price not found", "id",
                        id.toString()));

        return productSalePricesRepository.save(productSalePrice);
    }
}
