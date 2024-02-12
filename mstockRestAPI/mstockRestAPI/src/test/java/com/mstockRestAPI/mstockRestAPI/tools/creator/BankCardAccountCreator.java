package com.mstockRestAPI.mstockRestAPI.tools.creator;

import com.mstockRestAPI.mstockRestAPI.entity.BankCardAccount;
import com.mstockRestAPI.mstockRestAPI.enums.CardType;
import com.mstockRestAPI.mstockRestAPI.enums.Currency;

import java.math.BigDecimal;

public class BankCardAccountCreator {

    public static BankCardAccount entity(){
        return BankCardAccount.builder()
                .accountNumber("1234567891234567")
                .cardHolderName("MURAD GULIYEV")
                .cardType(CardType.VISA)
                .balance(BigDecimal.valueOf(41231))
                .currency(Currency.AZN)
                .build();
    }


}
