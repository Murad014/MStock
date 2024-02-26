package com.mstockRestAPI.mstockRestAPI.tools.creator;

import com.mstockRestAPI.mstockRestAPI.dto.InvoiceItemDto;
import com.mstockRestAPI.mstockRestAPI.entity.Invoice;
import com.mstockRestAPI.mstockRestAPI.entity.InvoiceItem;
import com.mstockRestAPI.mstockRestAPI.tools.utils.Util;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Stream;

public class InvoiceItemCreator {
    private static int GENERATE_LIMIT = 10;

    public static InvoiceItem entity(){
        Byte isActive = (byte) (Math.random() * 2);
        return InvoiceItem.builder()
                .invoice(InvoiceCreator.entity())
                .product(ProductCreator.entity())
                .quantity(BigDecimal.TEN)
                .price(
                        BigDecimal.valueOf(Util.generateRandomPrice(1, 200))
                )
                .isActive(isActive)
                .build();
    }

    public static InvoiceItemDto dto(){
        Byte isActive = (byte) (Math.random() * 2);
        return InvoiceItemDto.builder()
                .quantity(BigDecimal.TEN)
                .invoiceId(1L)
                .productId(1L)
                .price(
                        BigDecimal.valueOf(Util.generateRandomPrice(1, 200))
                )
                .isActive(isActive)
                .build();
    }

    public static List<InvoiceItem> entityList(){
        return Stream
                .generate(InvoiceItemCreator:: entity)
                .limit(GENERATE_LIMIT).toList();
    }

    public static List<InvoiceItemDto> dtoList(){
        return Stream
                .generate(InvoiceItemCreator:: dto)
                .limit(GENERATE_LIMIT).toList();
    }


}
