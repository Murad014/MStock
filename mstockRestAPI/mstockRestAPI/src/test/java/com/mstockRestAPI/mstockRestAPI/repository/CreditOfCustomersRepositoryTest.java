package com.mstockRestAPI.mstockRestAPI.repository;


import com.mstockRestAPI.mstockRestAPI.dto.CustomerDto;
import com.mstockRestAPI.mstockRestAPI.entity.CreditOfCustomers;
import com.mstockRestAPI.mstockRestAPI.entity.Customer;
import com.mstockRestAPI.mstockRestAPI.entity.PaymentCustomerCredit;
import com.mstockRestAPI.mstockRestAPI.tools.creator.CreditOfCustomersCreator;
import com.mstockRestAPI.mstockRestAPI.tools.creator.CustomerCreator;
import com.mstockRestAPI.mstockRestAPI.tools.creator.PaymentCustomerCreditCreator;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@TestPropertySource(locations = "/application-test.properties",
        properties = "server.port=8081")
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class CreditOfCustomersRepositoryTest {

    @Autowired
    private CreditsOfCustomersRepository creditOfCustomersRepository;
    @Autowired
    private CustomerRepository customerRepository;

    private CreditOfCustomers entity;
    private List<CreditOfCustomers> entityList;

    @BeforeEach
    public void beforeEach(){
        entity = CreditOfCustomersCreator.entity();
        entityList = CreditOfCustomersCreator.entityList();

//        customerRepository.deleteAll();
//        creditOfCustomersRepository.deleteAll();
    }

    @Test
    @DisplayName("Add Customer Credit")
    @Order(1)
    public void givenEntity_whenAdd_thenReturnEntity(){
        // Act
        CreditOfCustomers save = creditOfCustomersRepository.save(entity);

        // Assert
        assertNotNull(save);
        assertThat(save)
                .usingRecursiveComparison()
                .isEqualTo(entity);

    }

    @Test
    @DisplayName("Closed the credit")
    @Order(2)
    public void givenCreditId_thenClosed_thenReturnEntity(){
        // Act
        CreditOfCustomers saved = creditOfCustomersRepository.save(entity);

        // Update Close
        saved.setClosed(true);
        CreditOfCustomers updated = creditOfCustomersRepository.save(saved);

        // Asserts
        assertNotNull(updated);
        assertTrue(updated.getClosed());
    }

    @Test
    @DisplayName("Add payment")
    @Order(3)
    public void givenCreditId_thenAddPayment_thenReturnEntity(){
        entity.setPayments(new ArrayList<>());
        entity.getPayments().add(PaymentCustomerCredit.builder()
                        .paymentAmount(BigDecimal.valueOf(12))
                .build());
        entity.getPayments().add(PaymentCustomerCredit.builder()
                        .paymentAmount(BigDecimal.valueOf(10))
                .build());
        // Save
        CreditOfCustomers save = creditOfCustomersRepository.save(entity);

        // Equations
        BigDecimal payment1 = save.getPayments().get(0).getPaymentAmount();
        BigDecimal payment2 = save.getPayments().get(1).getPaymentAmount();
        BigDecimal total = payment1.add(payment2);

        // Assert
        assertEquals(22, total.intValue());

    }

    @Test
    @DisplayName("Fetch credit by id")
    @Order(4)
    public void givenCreditId_whenFindAll_thenReturnList(){

        // Save
        CreditOfCustomers credit = creditOfCustomersRepository.save(entity);

        // Find All
        CreditOfCustomers creditFromDb = creditOfCustomersRepository.findById(credit.getId()).orElse(null);

        assertNotNull(creditFromDb);


    }





}
