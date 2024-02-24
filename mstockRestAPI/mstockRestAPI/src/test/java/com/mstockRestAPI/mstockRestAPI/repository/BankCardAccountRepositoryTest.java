package com.mstockRestAPI.mstockRestAPI.repository;

import com.mstockRestAPI.mstockRestAPI.dto.BankCardAccountDto;
import com.mstockRestAPI.mstockRestAPI.entity.BankCardAccount;
import com.mstockRestAPI.mstockRestAPI.entity.ProductMovements;
import com.mstockRestAPI.mstockRestAPI.exception.SomethingWentWrongException;
import com.mstockRestAPI.mstockRestAPI.tools.creator.BankCardAccountCreator;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@TestPropertySource(locations = "/application-test.properties",
        properties = "server.port=8081")
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class BankCardAccountRepositoryTest {

    @Autowired
    private BankCardAccountRepository bankCardAccountRepository;

    private BankCardAccount entity;
    private BankCardAccountDto dto;
    private List<BankCardAccount> entityList;
    private List<BankCardAccountDto> dtoList;



    @BeforeEach
    public void beforeEach(){
        entity = BankCardAccountCreator.entity();
        dto = BankCardAccountCreator.dto();
        entityList = BankCardAccountCreator.entityList();
        dtoList = BankCardAccountCreator.dtoList();
    }

    @Test
    @DisplayName("Add")
    @Order(1)
    @Transactional
    public void givenEntity_thenSave_thenReturnEntity(){
        // Act
        BankCardAccount savedEntity = bankCardAccountRepository.save(entity);

        // Assert
        assertThat(savedEntity)
                .usingRecursiveComparison()
                .isEqualTo(entity);
    }

    @Test
    @DisplayName("Add Wrong bank account number")
    @Order(2)
    @Transactional
    public void givenWrongEntity_thenSave_thenReturnException(){
        entity.setAccountNumber("14123124123");
        // Assert
        assertThrows(SomethingWentWrongException.class, () -> {
            bankCardAccountRepository.save(entity);});

    }

    @Test
    @DisplayName("DeActive the bank account.")
    @Order(3)
    @Transactional
    public void givenEntity_thenUpdate_thenReturnEntity(){
        // Save
        BankCardAccount savedEntity = bankCardAccountRepository.save(entity);

        // Update
        savedEntity.setIsActive((byte)0);
        BankCardAccount updatedEntity = bankCardAccountRepository.save(savedEntity);

        // Assert
        assertNotNull(updatedEntity);
        assertEquals(0, (byte)updatedEntity.getIsActive());
    }




}
