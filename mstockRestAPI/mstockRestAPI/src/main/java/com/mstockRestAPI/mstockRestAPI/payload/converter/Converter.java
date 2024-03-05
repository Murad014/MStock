package com.mstockRestAPI.mstockRestAPI.payload.converter;


import com.mstockRestAPI.mstockRestAPI.dto.ProductBarcodeDto;
import com.mstockRestAPI.mstockRestAPI.dto.ProductDto;
import com.mstockRestAPI.mstockRestAPI.entity.Company;
import com.mstockRestAPI.mstockRestAPI.entity.Product;
import com.mstockRestAPI.mstockRestAPI.entity.ProductBarcode;
import org.modelmapper.ModelMapper;
import org.modelmapper.PropertyMap;
import org.modelmapper.TypeMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class Converter {

    private final ModelMapper modelMapper;


    @Autowired
    public Converter(ModelMapper modelMapper){
        this.modelMapper = modelMapper;
//        configureMapper();
    }

    public <D, E> E mapToEntity(D dto, Class<E> entity){
        return modelMapper.map(dto, entity);
    }

    public  <E, D> D mapToDto(E entity, Class<D> dto){
        return modelMapper.map(entity, dto);
    }


//    private void configureMapper() {
//        modelMapper.addMappings(new PropertyMap<Product, ProductDto>() {
//            @Override
//            protected void configure() {
//                map().setProductBarcodeList(source.getProductBarcodeList().stream()
//                        .map(ProductBarcode::getBarcode)
//                        .collect(Collectors.toList()));
//            }
//        });
//    }




}
