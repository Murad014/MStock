package com.mstockRestAPI.mstockRestAPI.tools.creator;

import com.mstockRestAPI.mstockRestAPI.dto.InvoiceDto;
import com.mstockRestAPI.mstockRestAPI.entity.Invoice;
import com.mstockRestAPI.mstockRestAPI.entity.SupplierOfProduct;
import com.mstockRestAPI.mstockRestAPI.tools.utils.Util;
import net.bytebuddy.utility.RandomString;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Stream;

public class InvoiceCreator {

    public static Invoice entity(){
        Byte isActive = (byte) (Math.random() * 2);

        return Invoice.builder()
                .invoiceCode(RandomString.make(10))
                .supplier(SupplierOFProductCreator.entity())
                .invoiceDate(LocalDateTime.now())
                .initialTotalAmount(
                        BigDecimal.valueOf(Util.generateRandomPrice(1, 100))
                )
                .isActive(isActive)
                .build();
    }

    public static InvoiceDto dto(){
        Byte isActive = (byte) (Math.random() * 2);

        return InvoiceDto.builder()
                .invoiceCode(RandomString.make(10))
                .supplier(SupplierOFProductCreator.entity())
                .invoiceDate(LocalDateTime.now())
                .initialTotalAmount(
                        BigDecimal.valueOf(Util.generateRandomPrice(1, 100))
                )
                .isActive(isActive)
                .build();
    }

    public static List<InvoiceDto> dtoList(){
        return Stream
                .generate(InvoiceCreator:: dto)
                .limit(10)
                .toList();
    }

    public static List<Invoice> entityList(){
        return Stream
                .generate(InvoiceCreator:: entity)
                .limit(10)
                .toList();
    }


}
