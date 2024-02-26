package com.mstockRestAPI.mstockRestAPI.service;

import com.mstockRestAPI.mstockRestAPI.dto.InvoiceDto;
import com.mstockRestAPI.mstockRestAPI.dto.InvoiceItemDto;
import com.mstockRestAPI.mstockRestAPI.entity.Invoice;
import com.mstockRestAPI.mstockRestAPI.entity.InvoiceItem;
import com.mstockRestAPI.mstockRestAPI.entity.Product;
import com.mstockRestAPI.mstockRestAPI.payload.converter.Converter;
import com.mstockRestAPI.mstockRestAPI.repository.InvoiceItemRepository;
import com.mstockRestAPI.mstockRestAPI.repository.InvoiceRepository;
import com.mstockRestAPI.mstockRestAPI.repository.ProductRepository;
import com.mstockRestAPI.mstockRestAPI.service.impl.InvoiceItemServiceImpl;
import com.mstockRestAPI.mstockRestAPI.tools.creator.InvoiceCreator;
import com.mstockRestAPI.mstockRestAPI.tools.creator.InvoiceItemCreator;
import com.mstockRestAPI.mstockRestAPI.tools.creator.ProductCreator;
import org.junit.jupiter.api.*;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.mockito.ArgumentMatchers.*;
import java.util.List;
import java.util.Optional;
import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;

@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@TestPropertySource(locations = "/application-test.properties")
public class InvoiceItemServiceTest {
    @Mock
    private Converter converter;
    @Mock
    private InvoiceRepository invoiceRepository;
    @Mock
    private InvoiceItemRepository invoiceItemRepository;
    @Mock
    private ProductRepository productRepository;

    @InjectMocks
    private InvoiceItemServiceImpl invoiceItemService;

    private InvoiceItemDto invoiceItemDto;
    private InvoiceItem invoiceItemEntity;
    private List<InvoiceItem> invoiceItemEntityList;
    private List<InvoiceItemDto> invoiceItemListDto;

    private Invoice invoiceEntity;
    private InvoiceDto invoiceDto;
    private Product productEntity;



    @BeforeEach
    public void beforeEach(){
        invoiceItemDto = InvoiceItemCreator.dto();
        invoiceItemEntity = InvoiceItemCreator.entity();

        invoiceEntity = InvoiceCreator.entity();
        invoiceDto = InvoiceCreator.dto();
        productEntity = ProductCreator.entity();
        invoiceItemListDto = InvoiceItemCreator.dtoList();
        invoiceItemEntityList = InvoiceItemCreator.entityList();
    }

    @Test
    @DisplayName("Add All")
    @Order(1)
    public void givenDtoList_whenSave_thenReturnDtoList(){
        // Mock
        mocks();
        when(invoiceRepository.findById(anyLong())).thenReturn(Optional.ofNullable(invoiceEntity));
        when(productRepository.findById(anyLong())).thenReturn(Optional.ofNullable(productEntity));
        when(invoiceItemRepository.saveAll(any())).thenReturn(invoiceItemEntityList);

        // Save
        List<InvoiceItemDto> savedList = invoiceItemService.addAll(invoiceItemListDto);

        // Assert
        assertNotNull(savedList);
        assertFalse(savedList.isEmpty());

        // Verify
        verify(converter, times(savedList.size())).mapToDto(any(), any());
        verify(converter, times(savedList.size())).mapToEntity(any(), any());
        verify(invoiceRepository, times(savedList.size())).findById(any());
        verify(productRepository, times(savedList.size())).findById(any());
        verify(invoiceItemRepository, times(1)).saveAll(any());

    }

    @Test
    @DisplayName("Add")
    @Order(2)
    public void givenDto_whenSave_thenReturnDto(){
        // Mock
        mocks();
        when(invoiceRepository.findById(anyLong())).thenReturn(Optional.ofNullable(invoiceEntity));
        when(productRepository.findById(anyLong())).thenReturn(Optional.ofNullable(productEntity));
        when(invoiceItemRepository.saveAll(any())).thenReturn(invoiceItemEntityList);

        // Save
        InvoiceItemDto saved = invoiceItemService.add(invoiceItemDto);

        // Assert
        assertNotNull(saved);


        // Verify
        verify(converter, times(1)).mapToDto(any(), any());
        verify(converter, times(1)).mapToEntity(any(), any());
        verify(invoiceRepository, times(1)).findById(any());
        verify(productRepository, times(1)).findById(any());
        verify(invoiceItemRepository, times(1)).save(any());

    }



    private void mocks(){
        when(converter.mapToEntity(any(), eq(InvoiceItem.class)))
                .thenReturn(invoiceItemEntity);
        when(converter.mapToDto(any(), eq(InvoiceItemDto.class)))
                .thenReturn(invoiceItemDto);
    }

}
