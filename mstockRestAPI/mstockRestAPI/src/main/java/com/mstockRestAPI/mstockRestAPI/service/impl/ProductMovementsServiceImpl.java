package com.mstockRestAPI.mstockRestAPI.service.impl;

import com.mstockRestAPI.mstockRestAPI.dto.ProductMovementDto;
import com.mstockRestAPI.mstockRestAPI.dto.ProductMovementListDto;
import com.mstockRestAPI.mstockRestAPI.repository.ProductMovementsRepository;
import com.mstockRestAPI.mstockRestAPI.repository.SaleReceiptRepository;
import com.mstockRestAPI.mstockRestAPI.service.ProductMovementsService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductMovementsServiceImpl implements ProductMovementsService {

    private final ProductMovementsRepository productMovementsRepository;
    private final ModelMapper modelMapper;
    private final SaleReceiptRepository saleReceiptRepository;

    @Autowired
    public ProductMovementsServiceImpl(
            ProductMovementsRepository productMovementsRepository,
            SaleReceiptRepository saleReceiptRepository,
            ModelMapper modelMapper
    ){
        this.productMovementsRepository = productMovementsRepository;
        this.saleReceiptRepository = saleReceiptRepository;
        this.modelMapper = modelMapper;
    }


    @Override
    public List<ProductMovementDto> addAllSalesOrReturnsProducts(ProductMovementListDto productMovementListDto) {

        return null;
    }


}
