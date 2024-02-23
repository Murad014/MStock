package com.mstockRestAPI.mstockRestAPI.service;

import com.mstockRestAPI.mstockRestAPI.dto.CreditOfCustomersDto;
import com.mstockRestAPI.mstockRestAPI.dto.CustomerDto;
import com.mstockRestAPI.mstockRestAPI.entity.CreditOfCustomers;
import com.mstockRestAPI.mstockRestAPI.entity.Customer;
import com.mstockRestAPI.mstockRestAPI.repository.CreditsOfCustomersRepository;
import com.mstockRestAPI.mstockRestAPI.repository.CustomerRepository;
import com.mstockRestAPI.mstockRestAPI.service.impl.CreditsOfCustomersServiceImpl;
import com.mstockRestAPI.mstockRestAPI.tools.creator.CreditOfCustomersCreator;
import com.mstockRestAPI.mstockRestAPI.tools.creator.CustomerCreator;
import org.aspectj.lang.annotation.Before;
import org.junit.jupiter.api.*;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import com.mstockRestAPI.mstockRestAPI.payload.converter.*;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.when;

@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@TestPropertySource(locations = "/application-test.properties")
public class CreditsOfCustomersServiceTest {

    @Mock
    private Converter converter;
    @Mock
    private CreditsOfCustomersRepository creditsOfCustomersRepository;
    @Mock
    private CustomerRepository customerRepository;

    @InjectMocks
    private CreditsOfCustomersServiceImpl creditsOfCustomersService;

    private CreditOfCustomers entity;
    private List<CreditOfCustomers> entityList;
    private CreditOfCustomersDto dto;
    private List<CreditOfCustomersDto> dtoList;

    @BeforeEach
    public void beforeEach(){
        entity = CreditOfCustomersCreator.entity();
        entityList = CreditOfCustomersCreator.entityList();

        dto = CreditOfCustomersCreator.dto();
        dtoList = CreditOfCustomersCreator.dtoList();
    }

    @Test
    @DisplayName("Add by customer id")
    @Order(1)
    public void givenEntityAndCustomerId_whenSave_thenReturnEntity(){
        Long customerId = 12L;
        Customer customer = CustomerCreator.entity();
        customer.setId(customerId);

        // When
        singleMock(entity, dto);

        when(creditsOfCustomersRepository.save(entity))
                .thenReturn(entity);

        when(customerRepository.findById(customerId)).thenReturn(Optional.of(customer));

        CreditOfCustomersDto saveDto = creditsOfCustomersService.addByCustomerId(dto, customerId);
        assertNotNull(saveDto);
        assertThat(saveDto)
                .usingRecursiveComparison()
                .isEqualTo(dto);
    }

    @Test
    @DisplayName("Add customer id card number")
    @Order(2)
    public void givenEntityAndCardNumber_whenSave_thenReturnEntity(){
        String customerIdCardNumber = "83M12AS";
        Customer customer = CustomerCreator.entity();
        customer.setIdCardNumber(customerIdCardNumber);

        // When
        singleMock(entity, dto);

        when(creditsOfCustomersRepository.save(entity))
                .thenReturn(entity);

        when(customerRepository.findByIdCardNumber(customerIdCardNumber)).thenReturn(customer);

        CreditOfCustomersDto saveDto = creditsOfCustomersService.addByCustomerIdCardNumber(dto, customerIdCardNumber);
        assertNotNull(saveDto);
        assertThat(saveDto)
                .usingRecursiveComparison()
                .isEqualTo(dto);
    }


    private void singleMock(CreditOfCustomers entity, CreditOfCustomersDto dto){
        when(converter.mapToEntity(dto, CreditOfCustomers.class))
                .thenReturn(entity);
        when(converter.mapToDto(entity, CreditOfCustomersDto.class))
                .thenReturn(dto);
    }

}
