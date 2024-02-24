package com.mstockRestAPI.mstockRestAPI.service;

import com.mstockRestAPI.mstockRestAPI.dto.BankCardAccountDto;
import com.mstockRestAPI.mstockRestAPI.entity.BankCardAccount;
import com.mstockRestAPI.mstockRestAPI.entity.Customer;
import com.mstockRestAPI.mstockRestAPI.entity.PaymentExtraInfo;
import com.mstockRestAPI.mstockRestAPI.payload.converter.Converter;
import com.mstockRestAPI.mstockRestAPI.repository.BankCardAccountRepository;
import com.mstockRestAPI.mstockRestAPI.service.impl.BankCardAccountServiceImpl;
import com.mstockRestAPI.mstockRestAPI.tools.creator.BankCardAccountCreator;
import org.junit.jupiter.api.*;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;

@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@TestPropertySource(locations = "/application-test.properties")
public class BankCardAccountServiceTest {

    @Mock
    private Converter converter;

    @Mock
    private BankCardAccountRepository bankCardAccountRepository;

    @InjectMocks
    private BankCardAccountServiceImpl bankCardAccountService;

    private BankCardAccount entity;
    private BankCardAccountDto dto;

    @BeforeEach
    public void beforeEach(){
        entity = BankCardAccountCreator.entity();
        dto = BankCardAccountCreator.dto();
    }

    @Test
    @DisplayName("Add Bank Card Account")
    @Order(1)
    public void givenEntity_whenAdd_thenReturnEntity(){

        // When
        when(bankCardAccountRepository.save(entity)).thenReturn(entity);
        when(converter.mapToEntity(any(), eq(BankCardAccount.class))).thenReturn(entity);
        when(converter.mapToDto(any(), eq(BankCardAccountDto.class))).thenReturn(dto);

        // Save
        BankCardAccountDto saved = bankCardAccountService.add(dto);

        // Assert
        assertNotNull(saved);
        assertThat(saved)
                .usingRecursiveComparison()
                .isEqualTo(dto);

    }


    @Test
    @DisplayName("Make Bank Card Account deActive")
    @Order(2)
    public void givenAccountNumber_whenFind_thenUpdateDeActive(){
        String bankAccountNumber = entity.getAccountNumber();
        dto.setAccountNumber(bankAccountNumber);

        // When
        when(bankCardAccountRepository.findById(bankAccountNumber)).thenReturn(Optional.ofNullable(entity));
        when(bankCardAccountRepository.save(entity)).thenReturn(entity);
        when(converter.mapToEntity(any(), eq(BankCardAccount.class))).thenReturn(entity);
        when(converter.mapToDto(any(), eq(BankCardAccountDto.class))).thenReturn(dto);

        Boolean deActive = bankCardAccountService.makeDeActive(bankAccountNumber);

        assertTrue(deActive);

    }





}
