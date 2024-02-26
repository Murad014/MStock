package com.mstockRestAPI.mstockRestAPI.tools.creator;

import com.mstockRestAPI.mstockRestAPI.dto.CreditOfCustomersDto;
import com.mstockRestAPI.mstockRestAPI.entity.CreditOfCustomers;
import com.mstockRestAPI.mstockRestAPI.entity.PaymentCustomerCredit;
import com.mstockRestAPI.mstockRestAPI.tools.utils.Util;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import java.util.*;

public class CreditOfCustomersCreator {

    private static final int NUMBER_OF_CREDIT_CUSTOMERS = 10;

    public static CreditOfCustomers entity() {
        Byte isActive = (byte) (Math.random() * 2);

        return CreditOfCustomers.builder()
                .customer(CustomerCreator.entity())
                .givenAmount(BigDecimal.valueOf(Util.generateRandomPrice(100, 1000)))
                .totalAmount(BigDecimal.valueOf(Util.generateRandomPrice(500, 5000)))
                .percentageFee(BigDecimal.valueOf(Util.generateRandomPrice(1, 10)))
                .commissionAmount(BigDecimal.valueOf(Util.generateRandomPrice(10, 100)))
                .monthlyInstallment(BigDecimal.valueOf(Util.generateRandomPrice(50, 500)))
                .installmentCount(12)
                .creditMonths((int)Util.generateRandomPrice(1, 12))
                .finePercentage(BigDecimal.valueOf(Util.generateRandomPrice(1, 5)))
                .dueDate(new Timestamp(System.currentTimeMillis() + 86400000))
                .isActive(isActive)
                .closed(false)
                .build();
    }

    public static CreditOfCustomersDto dto() {
        Byte isActive = (byte) (Math.random() * 2);
        return CreditOfCustomersDto.builder()
                .customer(CustomerCreator.dto())
                .givenAmount(BigDecimal.valueOf(Util.generateRandomPrice(100, 1000)))
                .totalAmount(BigDecimal.valueOf(Util.generateRandomPrice(500, 5000)))
                .percentageFee(BigDecimal.valueOf(Util.generateRandomPrice(1, 10)))
                .commissionAmount(BigDecimal.valueOf(Util.generateRandomPrice(10, 100)))
                .monthlyInstallment(BigDecimal.valueOf(Util.generateRandomPrice(50, 500)))
                .installmentCount(12)
                .creditMonths((int)Util.generateRandomPrice(1, 12))
                .finePercentage(BigDecimal.valueOf(Util.generateRandomPrice(1, 5)))
                .dueDate(new Timestamp(System.currentTimeMillis() + 86400000))
                .isActive(isActive)
                .closed(false)
                .build();
    }

    public static List<CreditOfCustomers> entityList() {
        return Stream.generate(CreditOfCustomersCreator::entity)
                .limit(NUMBER_OF_CREDIT_CUSTOMERS)
                .collect(Collectors.toList());
    }

    public static List<CreditOfCustomersDto> dtoList() {
        return Stream.generate(CreditOfCustomersCreator::dto)
                .limit(NUMBER_OF_CREDIT_CUSTOMERS)
                .collect(Collectors.toList());
    }


}
