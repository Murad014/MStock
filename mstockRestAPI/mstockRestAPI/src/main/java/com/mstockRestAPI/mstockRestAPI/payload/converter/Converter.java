package com.mstockRestAPI.mstockRestAPI.payload.converter;


import com.mstockRestAPI.mstockRestAPI.entity.Company;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class Converter {

    private final ModelMapper modelMapper;

    @Autowired
    public Converter(ModelMapper modelMapper){
        this.modelMapper = modelMapper;

    }

    public <D, E> E mapToEntity(D dto, Class<E> entity){
        return modelMapper.map(dto, entity);
    }

    public  <E, D> D mapToDto(E entity, Class<D> dto){
        return modelMapper.map(entity, dto);
    }






}
