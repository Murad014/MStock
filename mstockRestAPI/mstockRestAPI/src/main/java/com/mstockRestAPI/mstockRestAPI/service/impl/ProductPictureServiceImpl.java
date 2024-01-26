package com.mstockRestAPI.mstockRestAPI.service.impl;

import com.mstockRestAPI.mstockRestAPI.dto.ProductBarcodeDto;
import com.mstockRestAPI.mstockRestAPI.dto.ProductPictureDto;
import com.mstockRestAPI.mstockRestAPI.entity.ProductPicture;
import com.mstockRestAPI.mstockRestAPI.exception.ResourceNotFoundException;
import com.mstockRestAPI.mstockRestAPI.exception.SqlProcessException;
import com.mstockRestAPI.mstockRestAPI.payload.converter.Converter;
import com.mstockRestAPI.mstockRestAPI.repository.ProductPictureRepository;
import com.mstockRestAPI.mstockRestAPI.service.ProductPictureService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductPictureServiceImpl implements ProductPictureService {

    private final ProductPictureRepository productPictureRepository;
    private final Converter converter;

    @Autowired
    public ProductPictureServiceImpl(ProductPictureRepository productPictureRepository, Converter converter){

        this.productPictureRepository = productPictureRepository;
        this.converter = converter;
    }

    @Override
    public ProductPictureDto add(ProductPictureDto productPictureDto) throws SqlProcessException {
        assert productPictureDto.getId() == null;

        ProductPicture saved = productPictureRepository.save(
                converter.mapToEntity(productPictureDto, ProductPicture.class)
        );
        return converter.mapToDto(saved, ProductPictureDto.class);
    }

    @Override
    public ProductPictureDto update(ProductPictureDto productPictureDto) {
        Long id = productPictureDto.getId();
        assert id != null;
        productPictureRepository.findById(productPictureDto.getId())
                .orElseThrow(() -> new ResourceNotFoundException("ProductPicture", "id", id.toString()));

        ProductPicture saved = productPictureRepository.save(
                converter.mapToEntity(productPictureDto, ProductPicture.class)
        );

        return converter.mapToDto(saved, ProductPictureDto.class);
    }

    @Override
    public List<ProductPictureDto> findAll() {
        return null;
    }

    @Override
    public List<ProductPictureDto> findByIsActive(byte isActive) {
        List<ProductPicture> findFromDb = productPictureRepository.findByIsActive(isActive);

        return findFromDb.stream()
                .map(picture -> converter.mapToDto(picture, ProductPictureDto.class))
                .toList();
    }
}
