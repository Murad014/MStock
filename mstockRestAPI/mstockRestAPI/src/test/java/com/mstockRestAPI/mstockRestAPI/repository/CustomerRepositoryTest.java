package com.mstockRestAPI.mstockRestAPI.repository;

import com.mstockRestAPI.mstockRestAPI.entity.CreditOfCustomers;
import com.mstockRestAPI.mstockRestAPI.entity.Customer;
import com.mstockRestAPI.mstockRestAPI.tools.creator.CreditOfCustomersCreator;
import com.mstockRestAPI.mstockRestAPI.tools.creator.CustomerCreator;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import static org.junit.jupiter.api.Assertions.*;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@TestPropertySource(locations = "/application-test.properties",
        properties = "server.port=8081")
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class CustomerRepositoryTest {
    @Autowired
    private CustomerRepository customerRepository;
    @Autowired
    private CreditsOfCustomersRepository creditsOfCustomersRepository;


    private Customer customerEntity;
    private List<Customer> customerEntityList;

    @BeforeEach
    public void beforeEach(){
        customerEntity = CustomerCreator.entity();
        customerEntityList = CustomerCreator.entityList();

//        creditsOfCustomersRepository.deleteAll();
//        customerRepository.deleteAll();
    }

    @Test
    @DisplayName("Add Customer")
    @Order(1)
    public void givenEntity_thenAdd_thenReturnEntity(){
        // Act
        Customer save = customerRepository.save(customerEntity);

        // Assert
        assertNotNull(save);
        assertThat(save)
                .usingRecursiveComparison()
                .isEqualTo(customerEntity);

    }

    @Test
    @DisplayName("Update Customer")
    @Order(2)
    public void givenEntity_whenUpdate_thenReturnEntity(){
        // Save
        Customer save = customerRepository.save(customerEntity);

        // Find
        Customer customerFromDB = customerRepository.findById(save.getId()).orElse(null);
        assertNotNull(customerFromDB);

        // Update
        customerFromDB.setName("Updated Name");
        Customer updatedCustomer = customerRepository.save(customerFromDB);

        // Assert
        assertNotNull(updatedCustomer);

        assertThat(updatedCustomer)
                .usingRecursiveComparison()
                .ignoringFields("updatedDate")
                .isEqualTo(customerFromDB);


    }

    @Test
    @DisplayName("Find By id")
    @Order(3)
    public void givenId_whenFind_thenReturnEntity(){
        // Save
        Customer save = customerRepository.save(customerEntity);

        // Find
        Customer customerFromDB = customerRepository.findById(save.getId()).orElse(null);

        // Asserts
        assertNotNull(customerFromDB);
        assertEquals(customerFromDB.getBonusRate().doubleValue(), save.getBonusRate().doubleValue());
        assertThat(customerFromDB)
                .usingRecursiveComparison()
                .ignoringFields("updatedDate")
                .ignoringFields("bonusRate")
                .ignoringFields("credits")
                .isEqualTo(save);
    }

    @Test
    @DisplayName("Save All")
    @Order(4)
    public void getAllListOfCustomerEntity(){
        // Save All
        List<Customer> saveAll = customerRepository.saveAll(customerEntityList);

        // Assert
        assertNotNull(saveAll);
        assertFalse(saveAll.isEmpty());
        assertFalse(saveAll.stream()
                .anyMatch(Objects::isNull));

        assertEquals(customerEntityList.size(), saveAll.size());
    }

    @Test
    @DisplayName("Get Credits")
    @Order(5)
    @Transactional
    public void givenCustomerId_thenFind_thenReturnCredits(){

        CreditOfCustomers credit01 = CreditOfCustomersCreator.entity();
        CreditOfCustomers credit02 = CreditOfCustomersCreator.entity();

        // Act
        List<CreditOfCustomers> savedCredits = creditsOfCustomersRepository.saveAll(List.of(credit01, credit02));

        customerEntity.setCredits(savedCredits);
        Customer save = customerRepository.save(customerEntity);

        // Find
        Customer findById = customerRepository.findById(save.getId()).orElse(null);
        List<CreditOfCustomers> credits = findById.getCredits();
        System.out.println(findById);

        // Assert
        assertNotNull(findById);
        assertFalse(credits.isEmpty());
        assertEquals(save.getCredits().size(), findById.getCredits().size());
    }

    @Test
    @DisplayName("Get Customer By id card number")
    @Transactional
    @Order(6)
    public void givenCustomerIdCardNumber_whenFind_thenReturnEntity(){
        CreditOfCustomers credit01 = CreditOfCustomersCreator.entity();
        CreditOfCustomers credit02 = CreditOfCustomersCreator.entity();

        // Act
        List<CreditOfCustomers> savedCredits = creditsOfCustomersRepository.saveAll(List.of(credit01, credit02));

        customerEntity.setCredits(savedCredits);
        Customer save = customerRepository.save(customerEntity);

        // Find
        Customer findById = customerRepository.findByIdCardNumber(save.getIdCardNumber());
        List<CreditOfCustomers> credits = findById.getCredits();

        // Assert
        assertNotNull(findById);
        assertFalse(credits.isEmpty());
        assertEquals(save.getIdCardNumber(), findById.getIdCardNumber());
    }




}
