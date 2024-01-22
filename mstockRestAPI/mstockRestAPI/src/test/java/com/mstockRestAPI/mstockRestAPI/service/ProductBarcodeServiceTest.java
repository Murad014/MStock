package com.mstockRestAPI.mstockRestAPI.service;


import com.mstockRestAPI.mstockRestAPI.dto.converter.Converter;
import com.mstockRestAPI.mstockRestAPI.entity.ProductBarcode;
import com.mstockRestAPI.mstockRestAPI.exception.ResourceNotFoundException;
import com.mstockRestAPI.mstockRestAPI.repository.ProductBarcodeRepository;
import com.mstockRestAPI.mstockRestAPI.service.impl.ProductBarcodeServiceImpl;
import com.mstockRestAPI.mstockRestAPI.tools.creator.ProductBarcodeCreator;
import org.junit.jupiter.api.*;

import static org.assertj.core.api.InstanceOfAssertFactories.list;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@TestPropertySource(locations = "/application-test.properties")
public class ProductBarcodeServiceTest {

    @Mock
    private Converter converter;
    @Mock
    private ProductBarcodeRepository productBarcodeRepository;
    @InjectMocks
    private ProductBarcodeServiceImpl productBarcodeService;

    private ProductBarcode productBarcodeEntity;
    private List<ProductBarcode> productBarcodeList;


    @BeforeEach
    public void beforeEach(){
        productBarcodeEntity = ProductBarcodeCreator.entity();
        productBarcodeList = ProductBarcodeCreator.entityList();
    }

    @Test
    @DisplayName("Add")
    @Order(1)
    public void givenEntity_whenSave_thenReturnEntity(){
        // When
        when(productBarcodeRepository.save(productBarcodeEntity)).thenReturn(productBarcodeEntity);

        // Act
        ProductBarcode save = productBarcodeService.add(productBarcodeEntity);

        // Assert
        assertNotNull(save);
        assertThat(save)
                .usingRecursiveComparison()
                .isEqualTo(productBarcodeEntity);

        // Verify
        verify(productBarcodeRepository, times(1)).save(productBarcodeEntity);
    }

    @Test
    @DisplayName("Update exist Id")
    @Order(2)
    public void givenEntity_whenUpdate_thenReturnEntity(){
        String barcode = "12341541231";
        // When
        productBarcodeEntity.setId(1L);
        when(productBarcodeRepository.save(productBarcodeEntity)).thenReturn(productBarcodeEntity);
        when(productBarcodeRepository.findById(productBarcodeEntity.getId()))
                .thenReturn(Optional.ofNullable(productBarcodeEntity));
        // Act
        productBarcodeEntity.setBarcode(barcode);
        ProductBarcode productBarcodeUpdated = productBarcodeService.update(productBarcodeEntity);

        // Assert
        assertNotNull(productBarcodeUpdated);
        assertThat(productBarcodeUpdated)
                .usingRecursiveComparison()
                .isEqualTo(productBarcodeEntity);
        assertEquals(barcode, productBarcodeUpdated.getBarcode());
        // Verify
        verify(productBarcodeRepository, times(1)).save(productBarcodeEntity);
        verify(productBarcodeRepository, times(1)).findById(productBarcodeEntity.getId());
    }

    @Test
    @DisplayName("Update not exist Id")
    @Order(2)
    public void givenEntity_whenNotExistsId_thenReturnException(){
        String barcode = "12341541231";
        // When
        productBarcodeEntity.setId(1L);
        when(productBarcodeRepository.save(productBarcodeEntity)).thenReturn(productBarcodeEntity);
        when(productBarcodeRepository.findById(productBarcodeEntity.getId()))
                .thenThrow(ResourceNotFoundException.class);
        // Act
        productBarcodeEntity.setBarcode(barcode);

        // Assert
        assertThrows(ResourceNotFoundException.class, () -> productBarcodeService.update(productBarcodeEntity));
        // Verify
        verify(productBarcodeRepository, times(1)).findById(productBarcodeEntity.getId());
    }

    @ParameterizedTest
    @ValueSource(bytes = {0, 1})
    @DisplayName("Find All By isActive")
    @Order(3)
    public void returnAllListByIsActive(byte isActive){
        // Filter
        List<ProductBarcode> productBarcodesFilter = productBarcodeList.stream()
                .filter(product -> product.getIsActive() == isActive)
                .toList();

        // When
        when(productBarcodeRepository.findByIsActive(isActive)).thenReturn(productBarcodesFilter);

        // Act
        List<ProductBarcode> listFromDB = productBarcodeService.findAllByIsActive(isActive);

        // Assert
        assertNotNull(listFromDB);
        assertFalse(listFromDB.isEmpty());
        assertFalse(listFromDB.stream()
                .anyMatch(product -> product.getIsActive() != isActive));
        assertEquals(productBarcodesFilter.size(), listFromDB.size());

        // Verify
        verify(productBarcodeRepository, times(1)).findByIsActive(isActive);
    }

    @Test
    @DisplayName("Exist by Barcode")
    @Order(4)
    public void givenProductBarcode_whenFind_thenReturnTrue(){
        String barcode = "312312354123";
        // When
        when(productBarcodeRepository.existsByBarcode(barcode)).thenReturn(true);

        // Act
        boolean exist = productBarcodeService.existsByBarcode(barcode);

        // Assert
        assertTrue(exist);
    }



}
