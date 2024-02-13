package com.mstockRestAPI.mstockRestAPI.service.impl;

import com.mstockRestAPI.mstockRestAPI.dto.SaleReceiptDto;
import com.mstockRestAPI.mstockRestAPI.repository.SaleReceiptRepository;
import com.mstockRestAPI.mstockRestAPI.service.SaleReceiptService;
import com.mstockRestAPI.mstockRestAPI.utils.Util;
import org.springframework.beans.factory.annotation.Autowired;

public class SaleReceiptServiceImpl implements SaleReceiptService {

    private final SaleReceiptRepository saleReceiptRepository;

    @Autowired
    public SaleReceiptServiceImpl(SaleReceiptRepository saleReceiptRepository){
        this.saleReceiptRepository = saleReceiptRepository;
    }


    @Override
    public SaleReceiptDto createReceipt(SaleReceiptDto saleReceiptDto) {
        String receiptNumber = String.valueOf(Util.generateReceiptNumber());

        return null;
    }

}
