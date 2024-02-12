package com.mstockRestAPI.mstockRestAPI.repository;

import com.mstockRestAPI.mstockRestAPI.entity.PaymentExtraInfo;
import com.mstockRestAPI.mstockRestAPI.enums.PaymentType;
import com.mstockRestAPI.mstockRestAPI.exception.PaymentProcessException;
import com.mstockRestAPI.mstockRestAPI.exception.ResourceNotFoundException;
import com.mstockRestAPI.mstockRestAPI.exception.SomethingWentWrongException;
import com.mstockRestAPI.mstockRestAPI.tools.creator.PaymentExtraInfoCreator;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
@TestPropertySource(locations = "/application-test.properties")
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class PaymentExtraInfoRepositoryTest {
    @Autowired
    private PaymentExtraInfoRepository paymentExtraInfoRepository;

    @Autowired
    private BankCardAccountRepository bankCardAccountRepository;

    @BeforeEach
    public void beforeEach(){
        paymentExtraInfoRepository.deleteAll();
        bankCardAccountRepository.deleteAll();
    }

    @Test
    @DisplayName("Add. Cases: Payment Type: CASH_AND_CARD ")
    @Order(1)
    public void paymentType_Is_CASH_AND_CARD(){
        PaymentExtraInfo newEntity = PaymentExtraInfoCreator.createEntity(
                BigDecimal.valueOf(23.23), // cashPay
                BigDecimal.valueOf(24.32), // cardPay
                null, // creditPay
                PaymentType.CASH_AND_CARD
                );

        PaymentExtraInfo pay = paymentExtraInfoRepository.save(newEntity);

        assertThat(newEntity)
                .usingRecursiveComparison()
                .isEqualTo(pay);
    }

    @Test
    @DisplayName("Add. Cases: Payment Type: CREDIT_AND_CASH")
    @Order(2)
    public void paymentType_Is_CREDIT_AND_CASH(){
        PaymentExtraInfo newEntity = PaymentExtraInfoCreator.createEntity(
                BigDecimal.valueOf(23.23), // cashPay
                null, // cardPay
                BigDecimal.valueOf(21.32), // creditPay
                PaymentType.CREDIT_AND_CASH
        );
        PaymentExtraInfo pay = paymentExtraInfoRepository.save(newEntity);

        assertThat(newEntity)
                .usingRecursiveComparison()
                .isEqualTo(pay);
    }

    @Test
    @DisplayName("Add. Cases: Payment Type: CREDIT_AND_CARD")
    @Order(3)
    public void paymentType_Is_CREDIT_AND_CARD(){
        PaymentExtraInfo newEntity = PaymentExtraInfoCreator.createEntity(
                null, // cashPay
                BigDecimal.valueOf(23.23), // cardPay
                BigDecimal.valueOf(21.32), // creditPay
                PaymentType.CREDIT_AND_CARD
        );
        PaymentExtraInfo pay = paymentExtraInfoRepository.save(newEntity);

        assertThat(newEntity)
                .usingRecursiveComparison()
                .isEqualTo(pay);
    }

    @Test
    @DisplayName("Add. Cases: Payment Type: CASH_AND_CARD Check Exceptions")
    @Order(4)
    public void paymentType_Is_CASH_AND_CARD_returnException(){
        PaymentExtraInfo newEntity = PaymentExtraInfoCreator.createEntity(
                null, // CashPay
                BigDecimal.valueOf(24.32), // cardPay
                null, // creditPay
                PaymentType.CASH_AND_CARD
        );


        assertThrows(PaymentProcessException.class, () -> { paymentExtraInfoRepository.save(newEntity);});


    }

    @Test
    @DisplayName("Add. Cases: Payment Type: CREDIT_AND_CARD Check Exceptions")
    @Order(5)
    public void paymentType_Is_CREDIT_AND_CARD_returnException02(){
        PaymentExtraInfo newEntity = PaymentExtraInfoCreator.createEntity(
                null, // CashPay
                null, // cardPay
                BigDecimal.valueOf(24.32), // creditPay
                PaymentType.CREDIT_AND_CARD
        );

        bankCardAccountRepository.save(newEntity.getBankAccountNumber());
        assertThrows(PaymentProcessException.class, () -> { paymentExtraInfoRepository.save(newEntity);});
    }

    @Test
    @DisplayName("Add. Cases: Payment Type: CREDIT_AND_CASH Check Exceptions")
    @Order(6)
    public void paymentType_Is_CREDIT_AND_CASH_returnException(){
        PaymentExtraInfo newEntity = PaymentExtraInfoCreator.createEntity(
                null, // CashPay
                null, // cardPay
                BigDecimal.valueOf(24.32), // creditPay
                PaymentType.CREDIT_AND_CASH
        );

        assertThrows(PaymentProcessException.class, () -> { paymentExtraInfoRepository.save(newEntity);});
    }

    @Test
    @DisplayName("Add. Cases: Payment Type: CARD_AND_CASH Check BankAccount")
    @Order(7)
    public void paymentType_Is_BankAccountValid(){
        PaymentExtraInfo newEntity = PaymentExtraInfoCreator.createEntity(
                BigDecimal.valueOf(24.32), // CashPay
                BigDecimal.valueOf(2344.32), // cardPay
                null, // creditPay
                PaymentType.CASH_AND_CARD
        );


        PaymentExtraInfo save = paymentExtraInfoRepository.save(newEntity);
        String accountNumber = save.getBankAccountNumber().getAccountNumber();

        assertEquals("1234-5678-9123-4567", accountNumber);
    }

    @Test
    @DisplayName("Add. Cases: Payment Type: CARD_AND_CASH Check BankAccount Exception")
    @Order(8)
    public void paymentType_Is_BankAccountValid_exception(){
        PaymentExtraInfo newEntity = PaymentExtraInfoCreator.createEntity(
                BigDecimal.valueOf(24.32), // CashPay
                BigDecimal.valueOf(2344.32), // cardPay
                null, // creditPay
                PaymentType.CASH_AND_CARD
        );
        newEntity.getBankAccountNumber().setAccountNumber("12412313");


        assertThrows(SomethingWentWrongException.class, () -> {
            bankCardAccountRepository.save(newEntity.getBankAccountNumber());});

    }






}
