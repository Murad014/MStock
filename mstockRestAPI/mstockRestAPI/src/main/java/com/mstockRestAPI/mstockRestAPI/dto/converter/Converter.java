package com.mstockRestAPI.mstockRestAPI.dto.converter;


import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

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
