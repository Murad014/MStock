package com.mstockRestAPI.mstockRestAPI.repository;


import com.mstockRestAPI.mstockRestAPI.entity.CreditOfCustomers;
import com.mstockRestAPI.mstockRestAPI.entity.PaymentCustomerCredit;
import com.mstockRestAPI.mstockRestAPI.tools.creator.CreditOfCustomersCreator;
import com.mstockRestAPI.mstockRestAPI.tools.creator.PaymentCustomerCreditCreator;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
@TestPropertySource(locations = "/application-test.properties",
        properties = "server.port=8081")
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class PaymentCustomerCreditRepositoryTest {

    @Autowired
    private PaymentCustomerCreditRepository paymentCustomerCreditRepository;


    private PaymentCustomerCredit entity;
    private List<PaymentCustomerCredit> entityList;

    @BeforeEach
    public void beforeEach(){
        entity = PaymentCustomerCreditCreator.entity();
        entityList = PaymentCustomerCreditCreator.entityList();
    }

    @Test
    @DisplayName("Add payment")
    @Transactional
    @Order(1)
    public void givenEntity_whenAdd_thenReturnEntity(){
        // Save
        PaymentCustomerCredit saveEntity = paymentCustomerCreditRepository.save(entity);

        // Assert
        assertNotNull(saveEntity);
        assertThat(saveEntity)
                .usingRecursiveComparison()
                .isEqualTo(entity);

    }

//    @Test
//    @DisplayName("Fetch payments by credit id")
//    @Order(2)
//    public void givenCreditId_whenFind_thenReturnList(){
//        CreditOfCustomers credit = CreditOfCustomersCreator.entity();
//        List<PaymentCustomerCredit> payments = PaymentCustomerCreditCreator.entityList()
//                .stream()
//                .peek((payment) -> payment.setCredit(credit))
//                .toList();
//
//
//        // Save
//        paymentCustomerCreditRepository.saveAll(payments);
//
//        // Fetch by credit Id
//        Long creditId = payments.get(0).getCredit().getId();
//        List<PaymentCustomerCredit> paymentsFromDB = paymentCustomerCreditRepository.find
//    }




}
