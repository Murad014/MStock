package com.mstockRestAPI.mstockRestAPI.service;

import com.mstockRestAPI.mstockRestAPI.dto.CustomerDto;
import com.mstockRestAPI.mstockRestAPI.dto.InvoiceDto;
import com.mstockRestAPI.mstockRestAPI.entity.Invoice;
import com.mstockRestAPI.mstockRestAPI.payload.converter.Converter;
import com.mstockRestAPI.mstockRestAPI.repository.InvoiceRepository;
import com.mstockRestAPI.mstockRestAPI.service.impl.InvoiceServiceImpl;
import com.mstockRestAPI.mstockRestAPI.tools.creator.InvoiceCreator;
import org.junit.jupiter.api.*;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@TestPropertySource(locations = "/application-test.properties")
public class InvoiceServiceTest {
    @Mock
    private Converter converter;
    @Mock
    private InvoiceRepository invoiceRepository;

    @InjectMocks
    private InvoiceServiceImpl invoiceService;

    private Invoice entity;
    private InvoiceDto dto;
    private List<Invoice> entityList;
    private List<InvoiceDto> dtoList;

    @BeforeEach
    public void beforeEach(){
        entity = InvoiceCreator.entity();
        dto = InvoiceCreator.dto();

        entityList = InvoiceCreator.entityList();
        dtoList = InvoiceCreator.dtoList();
    }

    @Test
    @DisplayName("Add")
    @Order(1)
    public void givenEntity_whenSave_thenReturnEntity(){
        // When
        mocks();
        when(invoiceRepository.save(any())).thenReturn(entity);

        InvoiceDto saveDto = invoiceService.add(dto);

        // Asserts
        assertNotNull(saveDto);
        assertThat(saveDto)
                .usingRecursiveComparison()
                .isEqualTo(dto);

        // Verify
        verify(converter, times(1)).mapToDto(any(), any());
        verify(converter, times(1)).mapToEntity(any(), any());
        verify(invoiceRepository, times(1)).save(any());
    }

    @Test
    @DisplayName("Update")
    @Order(2)
    public void givenEntity_whenUpdate_thenReturnEntity(){
        Long updateId = 1L;
        // When
        mocks();
        when(invoiceRepository.save(any())).thenReturn(entity);
        when(invoiceRepository.findById(updateId)).thenReturn(Optional.ofNullable(entity));

        InvoiceDto saveDto = invoiceService.update(updateId, dto);

        // Asserts
        assertNotNull(saveDto);
        assertThat(saveDto)
                .usingRecursiveComparison()
                .isEqualTo(dto);

        // Verify

        verify(converter, times(1)).mapToDto(any(), any());
        verify(converter, times(1)).mapToEntity(any(), any());
        verify(invoiceRepository, times(1)).save(any());
        verify(invoiceRepository, times(1)).findById(any());
    }

    @Test
    @DisplayName("Fetch All")
    @Order(3)
    public void fetchAll(){
        Long updateId = 1L;
        // When
        mocks();
        when(invoiceRepository.save(any())).thenReturn(entity);
        when(invoiceRepository.findAll()).thenReturn(entityList);

        List<InvoiceDto> fetchAll = invoiceService.fetchAll();

        // Asserts
        assertNotNull(fetchAll);
        assertFalse(fetchAll.isEmpty());

        // Verify

        verify(converter, times(fetchAll.size())).mapToDto(any(), any());
        verify(invoiceRepository, times(1)).findAll();
    }

    @Test
    @DisplayName("Fetch by invoice Code")
    @Order(4)
    public void givenId_whenFind_thenReturnEntity(){
        String lookingFor = "test1123123";
        // When
        mocks();
        when(invoiceRepository.findByInvoiceCode(lookingFor)).thenReturn(entity);

        InvoiceDto fetchById = invoiceService.fetchByInvoiceCode(lookingFor);

        // Asserts
        assertNotNull(fetchById);
        assertThat(fetchById)
                .usingRecursiveComparison()
                .isEqualTo(dto);

        // Verify

        verify(converter, times(1)).mapToDto(any(), any());
        verify(invoiceRepository, times(1)).findByInvoiceCode(anyString());
    }





    private void mocks(){
        when(converter.mapToEntity(dto, Invoice.class))
                .thenReturn(entity);
        when(converter.mapToDto(entity, InvoiceDto.class))
                .thenReturn(dto);
    }



}
