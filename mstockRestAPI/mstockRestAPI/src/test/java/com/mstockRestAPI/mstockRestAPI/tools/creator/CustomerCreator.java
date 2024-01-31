package com.mstockRestAPI.mstockRestAPI.tools.creator;

import com.mstockRestAPI.mstockRestAPI.entity.Customer;
import net.bytebuddy.utility.RandomString;

import java.math.BigDecimal;
import java.util.Random;

public class CustomerCreator {
    private static final int NUMBER_OF_CUSTOMER= 10;
    private static final int PRODUCT_RANDOM_DESCRIPTION_LENGTH = 100;

    public static Customer entity(){
        byte isActive = (byte) (Math.random() * 2);

        return Customer.builder()
                .name(RandomString.make(7))
                .surname(RandomString.make(8))
                .email(String.format("%s@%s.com",
                        RandomString.make(6),
                        RandomString.make(4)))
                .phone(RandomString.make(10))
                .comment(RandomString.make(200))
                .bonusRate(BigDecimal.TEN)
                .isActive(isActive)
                .build();
    }

}
