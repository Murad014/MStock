package com.mstockRestAPI.mstockRestAPI.service;

import com.mstockRestAPI.mstockRestAPI.dto.CustomerDto;
import com.mstockRestAPI.mstockRestAPI.dto.ProductBarcodeDto;
import com.mstockRestAPI.mstockRestAPI.entity.Customer;
import com.mstockRestAPI.mstockRestAPI.entity.ProductBarcode;
import com.mstockRestAPI.mstockRestAPI.exception.ResourceNotFoundException;
import com.mstockRestAPI.mstockRestAPI.payload.converter.Converter;
import com.mstockRestAPI.mstockRestAPI.repository.CustomerRepository;
import com.mstockRestAPI.mstockRestAPI.service.impl.CustomerServiceImpl;
import com.mstockRestAPI.mstockRestAPI.tools.creator.CustomerCreator;
import org.junit.jupiter.api.*;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@TestPropertySource(locations = "/application-test.properties")
public class CustomerServiceTest {

    @Mock
    private Converter converter;

    @Mock
    private CustomerRepository customerRepository;

    @InjectMocks
    private CustomerServiceImpl customerService;

    private CustomerDto dto;
    private List<CustomerDto> dtoList;

    private Customer entity;
    private List<Customer> entityList;


    @BeforeEach
    public void beforeEach(){
        dto = CustomerCreator.dto();
        dtoList = CustomerCreator.dtoList();

        entity = CustomerCreator.entity();
        entityList = CustomerCreator.entityList();
    }


    @Test
    @DisplayName("Add")
    @Order(1)
    public void givenEntity_whenSave_thenReturnEntity(){
        // When
        singleMock(entity, dto);

        when(customerRepository.save(entity))
                .thenReturn(entity);

        CustomerDto saveDto = customerService.add(dto);
        assertNotNull(saveDto);
        assertThat(saveDto)
                .usingRecursiveComparison()
                .isEqualTo(dto);
    }

    @Test
    @DisplayName("Update")
    @Order(2)
    public void givenIdAndEntity_whenUpdate_thenReturnEntity(){
        Long updatedId = 1L;
        String updateCustomerName = "Updated customer name";
        entity.setName(updateCustomerName);
        entity.setId(updatedId);
        dto.setName(updateCustomerName);
        dto.setId(updatedId);

        // When
        singleMock(entity, dto);
        when(customerRepository.save(entity))
                .thenReturn(entity);
        when(customerRepository.findById(updatedId))
                .thenReturn(Optional.ofNullable(entity));

        // When Save
        CustomerDto saveDto = customerService.update(updatedId, dto);
        assertNotNull(saveDto);
        assertEquals(updateCustomerName, saveDto.getName());
        assertThat(saveDto)
                .usingRecursiveComparison()
                .isEqualTo(dto);
    }

    @Test
    @DisplayName("Update not exists id")
    @Order(3)
    public void givenIdAndEntity_whenUpdateNotExistsId_thenReturnException(){
        Long updatedId = 1L;
        String updateCustomerName = "Updated customer name";
        entity.setName(updateCustomerName);
        entity.setId(updatedId);
        dto.setName(updateCustomerName);
        dto.setId(updatedId);

        // When
        singleMock(entity, dto);
        when(customerRepository.save(entity))
                .thenReturn(entity);
        when(customerRepository.findById(updatedId))
                .thenThrow(ResourceNotFoundException.class);

        // Assert
        assertThrows(ResourceNotFoundException.class,
                () -> customerService.update(updatedId, dto));
    }



    private void singleMock(Customer entity, CustomerDto dto){
        when(converter.mapToEntity(dto, Customer.class))
                .thenReturn(entity);
        when(converter.mapToDto(entity, CustomerDto.class))
                .thenReturn(dto);
    }


}
