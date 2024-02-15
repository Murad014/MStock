package com.mstockRestAPI.mstockRestAPI.repository;

import com.mstockRestAPI.mstockRestAPI.entity.Customer;
import com.mstockRestAPI.mstockRestAPI.tools.creator.CustomerCreator;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import static org.junit.jupiter.api.Assertions.*;

import java.math.BigDecimal;
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

    private Customer customerEntity;
    private List<Customer> customerEntityList;

    @BeforeEach
    public void beforeEach(){
        customerEntity = CustomerCreator.entity();
        customerEntityList = CustomerCreator.entityList();

        customerRepository.deleteAll();
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
                .isEqualTo(save);
    }

    @Test
    @DisplayName("Get All")
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




}
