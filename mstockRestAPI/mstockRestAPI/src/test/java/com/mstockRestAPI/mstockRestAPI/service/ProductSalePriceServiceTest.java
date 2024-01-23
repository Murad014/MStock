package com.mstockRestAPI.mstockRestAPI.service;

import com.mstockRestAPI.mstockRestAPI.dto.CompanyDto;
import com.mstockRestAPI.mstockRestAPI.dto.ProductCategoryDto;
import com.mstockRestAPI.mstockRestAPI.dto.converter.Converter;
import com.mstockRestAPI.mstockRestAPI.entity.Company;
import com.mstockRestAPI.mstockRestAPI.entity.ProductCategory;
import com.mstockRestAPI.mstockRestAPI.entity.ProductSalePrice;
import com.mstockRestAPI.mstockRestAPI.exception.ResourceNotFoundException;
import com.mstockRestAPI.mstockRestAPI.repository.ProductCategoryRepository;
import com.mstockRestAPI.mstockRestAPI.repository.ProductSalePricesRepository;
import com.mstockRestAPI.mstockRestAPI.service.impl.ProductCategoriesServiceImpl;

import com.mstockRestAPI.mstockRestAPI.service.impl.ProductSalePriceServiceImpl;
import com.mstockRestAPI.mstockRestAPI.tools.creator.ProductCategoryCreator;
import com.mstockRestAPI.mstockRestAPI.tools.creator.ProductSalePricesCreator;
import org.junit.jupiter.api.*;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.mockito.Mockito.*;

import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;

import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;

@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@TestPropertySource(locations = "/application-test.properties")
public class ProductSalePriceServiceTest {
    @Mock
    private ProductSalePricesRepository productSalePricesRepository;
    @InjectMocks
    private ProductSalePriceServiceImpl productSalePriceService;
    private ProductSalePrice productSalePriceEntity;

    @BeforeEach
    public void beforeEach(){
        productSalePriceEntity = ProductSalePricesCreator.entity();
    }

    @Test
    @DisplayName("Add")
    @Order(1)
    public void givenEntity_whenAdd_thenReturnEntity(){
        // When
        when(productSalePricesRepository.save(productSalePriceEntity)).thenReturn(productSalePriceEntity);

        // Act
        ProductSalePrice productSalePriceAdd = productSalePriceService.add(productSalePriceEntity);

        // Assert
        assertThat(productSalePriceAdd)
                .usingRecursiveComparison()
                .isEqualTo(productSalePriceEntity);
    }

    @Test
    @DisplayName("Update by id")
    @Order(2)
    public void givenEntity_whenUpdate_thenReturnEntity(){
        // when
        productSalePriceEntity.setId(1L);
        when(productSalePricesRepository.save(productSalePriceEntity)).thenReturn(productSalePriceEntity);

        // Act
        ProductSalePrice productSalePriceUpdate = productSalePricesRepository.save(productSalePriceEntity);

        // Assert
        assertThat(productSalePriceUpdate)
                .usingRecursiveComparison()
                .isEqualTo(productSalePriceEntity);

        // Verify
        verify(productSalePricesRepository, times(1)).save(productSalePriceEntity);
    }

    @Test
    @DisplayName("Update by no exist id")
    @Order(3)
    public void givenEntity_whenTryUpdateWithNotExistId_thenReturnException(){
        // when
        Long updatedId = 1L;
        productSalePriceEntity.setId(updatedId);
        when(productSalePricesRepository.findById(updatedId)).thenThrow(
                ResourceNotFoundException.class);

        // Assert
        assertThrows(ResourceNotFoundException.class,
                () -> productSalePriceService.update(productSalePriceEntity));

        // Verify
        verify(productSalePricesRepository, times(1)).findById(updatedId);
    }

}
