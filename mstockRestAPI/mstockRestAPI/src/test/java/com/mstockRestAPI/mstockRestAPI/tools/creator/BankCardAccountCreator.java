package com.mstockRestAPI.mstockRestAPI.tools.creator;

import com.mstockRestAPI.mstockRestAPI.dto.BankCardAccountDto;
import com.mstockRestAPI.mstockRestAPI.entity.BankCardAccount;
import com.mstockRestAPI.mstockRestAPI.enums.CardType;
import com.mstockRestAPI.mstockRestAPI.enums.Currency;
import com.mstockRestAPI.mstockRestAPI.tools.utils.Util;
import net.bytebuddy.utility.RandomString;

import java.math.BigDecimal;
import java.util.Random;
import java.util.*;
import java.util.stream.Stream;


public class BankCardAccountCreator {

    public static BankCardAccount entity(){
        return BankCardAccount.builder()
                .accountNumber(Util.generateRandomCardNumber())
                .cardHolderName(RandomString.make(5) + RandomString.make(6))
                .cardType(CardType.VISA)
                .balance(BigDecimal.valueOf(1231))
                .currency(Currency.AZN)
                .build();
    }

    public static List<BankCardAccount> entityList(){
        return Stream
                .generate(BankCardAccountCreator:: entity)
                .limit(5)
                .toList();
    }
    public static List<BankCardAccountDto> dtoList(){
        return Stream
                .generate(BankCardAccountCreator:: dto)
                .limit(5)
                .toList();
    }
    public static BankCardAccountDto dto(){
        return BankCardAccountDto.builder()
                .accountNumber(Util.generateRandomCardNumber())
                .cardHolderName(RandomString.make(5) + RandomString.make(6))
                .cardType(CardType.VISA)
                .balance(BigDecimal.valueOf(1231))
                .currency(Currency.AZN)
                .build();
    }


}
