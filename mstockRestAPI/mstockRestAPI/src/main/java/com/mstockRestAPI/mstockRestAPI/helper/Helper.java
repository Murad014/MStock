package com.mstockRestAPI.mstockRestAPI.helper;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;

public class Helper {
    @Autowired
    private static ModelMapper modelMapper;

    public static <D, E> E mapToEntity(D dto, Class<E> entity){
        return modelMapper.map(dto, entity);
    }

    public static <E, D> D mapToDto(E entity, Class<D> dto){
        return modelMapper.map(entity, dto);
    }



}
