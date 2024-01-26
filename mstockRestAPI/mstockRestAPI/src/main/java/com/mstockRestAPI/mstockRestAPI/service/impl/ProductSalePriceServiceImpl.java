package com.mstockRestAPI.mstockRestAPI.service.impl;

import com.mstockRestAPI.mstockRestAPI.dto.ProductSalePriceDto;
import com.mstockRestAPI.mstockRestAPI.payload.converter.Converter;
import com.mstockRestAPI.mstockRestAPI.entity.ProductSalePrice;
import com.mstockRestAPI.mstockRestAPI.exception.ResourceNotFoundException;
import com.mstockRestAPI.mstockRestAPI.repository.ProductSalePricesRepository;
import com.mstockRestAPI.mstockRestAPI.service.ProductSalePriceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductSalePriceServiceImpl implements ProductSalePriceService {

    private final ProductSalePricesRepository productSalePricesRepository;
    private final Converter converter;

    @Autowired
    public ProductSalePriceServiceImpl(ProductSalePricesRepository productSalePricesRepository,
                                       Converter converter){
        this.productSalePricesRepository = productSalePricesRepository;
        this.converter = converter;
    }

    @Override
    public List<ProductSalePriceDto> addList(List<ProductSalePriceDto> list) {
        List<ProductSalePrice> entityList = list.stream().
                map(salePrice -> converter.mapToEntity(salePrice, ProductSalePrice.class)).toList();

        return productSalePricesRepository.saveAll(entityList).stream()
                .map(salePrice -> converter.mapToDto(salePrice, ProductSalePriceDto.class))
                .toList();
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
