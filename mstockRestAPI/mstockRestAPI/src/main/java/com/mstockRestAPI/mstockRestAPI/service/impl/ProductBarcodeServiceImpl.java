package com.mstockRestAPI.mstockRestAPI.service.impl;

import com.mstockRestAPI.mstockRestAPI.dto.ProductBarcodeDto;
import com.mstockRestAPI.mstockRestAPI.payload.converter.Converter;
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
    public ProductBarcodeDto add(ProductBarcodeDto productBarcode) {
        ProductBarcode productBarcodeSaved = productBarcodeRepository.save(
                converter.mapToEntity(productBarcode, ProductBarcode.class)
        );
        return converter.mapToDto(productBarcodeSaved, ProductBarcodeDto.class);
    }

    @Override
    public ProductBarcodeDto update(ProductBarcodeDto productBarcodeDto) {
        Long id = productBarcodeDto.getId();
        productBarcodeRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("Product Barcode not found", "id", id.toString())
        );
        ProductBarcode saved = productBarcodeRepository.save(
                converter.mapToEntity(productBarcodeDto, ProductBarcode.class)
        );
        return converter.mapToDto(saved, ProductBarcodeDto.class);
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
    public List<ProductBarcodeDto> findAllByIsActive(Byte isActive) {
        return productBarcodeRepository.findByIsActive(isActive)
                .stream()
                .map(barcode -> converter.mapToDto(barcode, ProductBarcodeDto.class))
                .toList();
    }

    @Override
    public boolean existsByBarcode(String barcode) {
        return productBarcodeRepository.existsByBarcode(barcode);
    }
}
