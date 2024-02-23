package com.mstockRestAPI.mstockRestAPI.tools.creator;

import com.mstockRestAPI.mstockRestAPI.dto.CustomerDto;
import com.mstockRestAPI.mstockRestAPI.entity.Customer;
import net.bytebuddy.utility.RandomString;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.stream.Stream;

public class CustomerCreator {
    private static final int NUMBER_OF_CUSTOMER= 10;
    private static final int PRODUCT_RANDOM_LENGTH = 100;

    public static Customer entity(){
        byte isActive = (byte) (Math.random() * 2);

        return Customer.builder()
                .name(RandomString.make(7))
                .surname(RandomString.make(8))
                .email(String.format("%s@%s.com",
                        RandomString.make(6),
                        RandomString.make(4)))
                .phone(RandomString.make(10))
                .idCardNumber(RandomString.make(7))
                .comment(RandomString.make(PRODUCT_RANDOM_LENGTH))
                .credits(new ArrayList<>())
                .bonusRate(BigDecimal.TEN)
                .isActive(isActive)
                .build();
    }

    public static CustomerDto dto(){
        byte isActive = (byte) (Math.random() * 2);

        return CustomerDto.builder()
                .name(RandomString.make(7))
                .surname(RandomString.make(8))
                .email(String.format("%s@%s.com",
                        RandomString.make(6),
                        RandomString.make(4)))
                .idCardNumber(RandomString.make(7))
                .phone(RandomString.make(10))
                .comment(RandomString.make(PRODUCT_RANDOM_LENGTH))
                .credits(new ArrayList<>())
                .bonusRate(BigDecimal.TEN)
                .isActive(isActive)
                .build();
    }

    public static List<Customer> entityList(){
        return Stream.generate(CustomerCreator:: entity)
                .limit(NUMBER_OF_CUSTOMER)
                .toList();
    }
    public static List<CustomerDto> dtoList(){
        return Stream.generate(CustomerCreator:: dto)
                .limit(NUMBER_OF_CUSTOMER)
                .toList();
    }


}
