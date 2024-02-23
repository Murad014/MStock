package com.mstockRestAPI.mstockRestAPI.service.impl;

import com.mstockRestAPI.mstockRestAPI.dto.PaymentExtraInfoDto;
import com.mstockRestAPI.mstockRestAPI.dto.ProductMovementDto;
import com.mstockRestAPI.mstockRestAPI.dto.ProductMovementListDto;
import com.mstockRestAPI.mstockRestAPI.entity.Customer;
import com.mstockRestAPI.mstockRestAPI.entity.PaymentExtraInfo;
import com.mstockRestAPI.mstockRestAPI.entity.ProductMovements;
import com.mstockRestAPI.mstockRestAPI.entity.SaleReceipt;
import com.mstockRestAPI.mstockRestAPI.payload.converter.Converter;
import com.mstockRestAPI.mstockRestAPI.payload.response.SuccessResponse;
import com.mstockRestAPI.mstockRestAPI.repository.ProductMovementsRepository;
import com.mstockRestAPI.mstockRestAPI.repository.SaleReceiptRepository;
import com.mstockRestAPI.mstockRestAPI.service.ProductMovementsService;
import com.mstockRestAPI.mstockRestAPI.utils.Util;
import jakarta.transaction.Transactional;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductMovementsServiceImpl implements ProductMovementsService {

    private final ProductMovementsRepository productMovementsRepository;
    private final Converter converter;
    private final SaleReceiptRepository saleReceiptRepository;

    @Autowired
    public ProductMovementsServiceImpl(
            ProductMovementsRepository productMovementsRepository,
            SaleReceiptRepository saleReceiptRepository,
            Converter converter
    ){
        this.productMovementsRepository = productMovementsRepository;
        this.saleReceiptRepository = saleReceiptRepository;
        this.converter = converter;
    }


    @Override
    @Transactional
    public SaleReceipt addAllSalesOrReturnsProducts(ProductMovementListDto productMovementListDto) {

        // Create receipt
        String receiptNumber = String.valueOf(Util.generateReceiptNumber());
        PaymentExtraInfo paymentExtraInfoFromClient = converter.mapToEntity(
            productMovementListDto.getPaymentExtraInfo(),
            PaymentExtraInfo.class
        );
        Customer customerFromClient = converter.mapToEntity(
                productMovementListDto.getCustomer(),
                Customer.class
                );

        SaleReceipt createReceipt = SaleReceipt.builder()
                .number(receiptNumber)
                .paymentExtraInfo(paymentExtraInfoFromClient)
                .customer(customerFromClient)
                .currency(productMovementListDto.getCurrency())
                .comment(productMovementListDto.getReceiptComment())
                .build();

        SaleReceipt receipt = saleReceiptRepository.save(createReceipt);

        // Set receipt to productMovements
        List<ProductMovements> entities = productMovementListDto.getProductsList()
                .stream()
                .map(productMovement ->
                {
                    ProductMovements entity = converter.mapToEntity(productMovement, ProductMovements.class);
                    entity.setReceipt(receipt);

                    return entity;
                })
                .toList();

        // Finally save
        productMovementsRepository.saveAll(entities);


        return receipt;
    }




}
