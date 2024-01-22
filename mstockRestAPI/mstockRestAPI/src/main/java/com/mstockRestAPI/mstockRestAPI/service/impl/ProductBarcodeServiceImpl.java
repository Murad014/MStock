package com.mstockRestAPI.mstockRestAPI.service.impl;

import com.mstockRestAPI.mstockRestAPI.dto.converter.Converter;
import com.mstockRestAPI.mstockRestAPI.entity.ProductBarcode;
import com.mstockRestAPI.mstockRestAPI.exception.ResourceNotFoundException;
import com.mstockRestAPI.mstockRestAPI.repository.ProductBarcodeRepository;
import com.mstockRestAPI.mstockRestAPI.service.ProductBarcodeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductBarcodeServiceImpl implements ProductBarcodeService {

    private final ProductBarcodeRepository productBarcodeRepository;
    private final Converter converter;
    @Autowired
    public ProductBarcodeServiceImpl(ProductBarcodeRepository productBarcodeRepository,
                                     Converter converter){
        this.productBarcodeRepository = productBarcodeRepository;
        this.converter = converter;
    }

    @Override
    public ProductBarcode add(ProductBarcode productBarcode) {
        return productBarcodeRepository.save(productBarcode);
    }

    @Override
    public ProductBarcode update(ProductBarcode productBarcode) {
        Long id = productBarcode.getId();
        productBarcodeRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("Product Barcode not found", "id", id.toString())
        );

        return productBarcodeRepository.save(productBarcode);
    }

    @Override
    public void makeIsActiveToDeActive(Long id) {
        // Find By id
        ProductBarcode productBarcodeFromDB = productBarcodeRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("Product Barcode not found", "id", id.toString())
        );

        // Make deActive and update
        byte deActive = 0;
        productBarcodeFromDB.setIsActive(deActive);
        productBarcodeRepository.save(productBarcodeFromDB);

    }

    @Override
    public List<ProductBarcode> findAllByIsActive(Byte isActive) {
        return productBarcodeRepository.findByIsActive(isActive);
    }

    @Override
    public boolean existsByBarcode(String barcode) {
        return productBarcodeRepository.existsByBarcode(barcode);
    }
}
