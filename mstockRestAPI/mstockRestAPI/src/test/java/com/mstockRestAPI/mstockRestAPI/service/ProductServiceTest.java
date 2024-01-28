package com.mstockRestAPI.mstockRestAPI.service;

import com.mstockRestAPI.mstockRestAPI.dto.*;
import com.mstockRestAPI.mstockRestAPI.entity.Product;
import com.mstockRestAPI.mstockRestAPI.entity.ProductBarcode;
import com.mstockRestAPI.mstockRestAPI.entity.ProductCategory;
import com.mstockRestAPI.mstockRestAPI.exception.ResourceNotFoundException;
import com.mstockRestAPI.mstockRestAPI.exception.SomethingWentWrongException;
import com.mstockRestAPI.mstockRestAPI.exception.SqlProcessException;
import com.mstockRestAPI.mstockRestAPI.payload.converter.Converter;
import com.mstockRestAPI.mstockRestAPI.repository.ProductRepository;
import com.mstockRestAPI.mstockRestAPI.service.impl.ProductServiceImpl;
import com.mstockRestAPI.mstockRestAPI.tools.creator.ProductCreator;
import com.mstockRestAPI.mstockRestAPI.utils.Util;
import org.junit.jupiter.api.*;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.TestPropertySource;
import org.springframework.web.multipart.MultipartFile;

import static org.junit.jupiter.api.Assertions.*;

import java.util.*;
import java.util.stream.IntStream;
import java.util.stream.LongStream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@TestPropertySource(locations = "/application-test.properties")
public class ProductServiceTest {
    @Mock
    private ProductRepository productRepository;
    @Mock
    private Converter converter;

    @Mock
    private Util util;

    @InjectMocks
    private ProductServiceImpl productService;

    private ProductDto productDto;
    private Product productEntity;
    private List<Product> productEntityList;
    private List<ProductDto> productDtoList;

    @BeforeEach
    public void beforeEach(){
        productDto = ProductCreator.dto();
        productDtoList = ProductCreator.dtoList();
        productEntity = ProductCreator.entity();
        productEntityList = ProductCreator.entityList();
    }


    @Test
    @DisplayName("Add")
    @Order(1)
    public void givenEntity_whenAdd_thenReturnEntity() throws SqlProcessException {
        // When
        mockSingle();

        // Act
        ProductDto saved = productService.add(productDto);

        // Assert
        assertNotNull(saved);
        assertThat(saved)
                .usingRecursiveComparison()
                .isEqualTo(productDto);


    }

    @Test
    @DisplayName("Add with null ID")
    @Order(2)
    public void givenEntityWithIdNull_whenTryAdd_thenException() throws SqlProcessException {
        // When
        mockSingle();
        productDto.setId(1L);

        // Assert
        assertThrows(SqlProcessException.class,
                () -> productService.add(productDto));
    }

    @Test
    @DisplayName("Update")
    @Order(3)
    public void givenEntity_whenUpdate_thenReturnEntity() throws SqlProcessException {
        // Arrange
        mockSingle();
        productDto.setId(1L);
        productDto.setProductName("Test Update");
        // Act
        ProductDto update = productService.update(productDto);

        assertNotNull(update);
        assertThat(update)
                .usingRecursiveComparison()
                .isEqualTo(productDto);

    }

    @Test
    @DisplayName("Update Exceptions")
    @Order(4)
    public void testExceptions(){
        // Arrange
        mockMap();

        // Assert
        assertThrows(SqlProcessException.class,
                () -> productService.update(productDto));

        // Test Exception - Not exist id
        productDto.setId(1L);
        when(productRepository.findById(1L)).thenThrow(ResourceNotFoundException.class);

        assertThrows(ResourceNotFoundException.class,
                () -> productService.update(productDto));

    }

    @Test
    @DisplayName("Fetch All")
    @Order(5)
    public void fetchAll(){
        // Arrange
        mockMap();
        when(productRepository.findAll()).thenReturn(productEntityList);

        // Act
        List<ProductDto> listProduct = productService.getAll();

        // Assert
        assertNotNull(listProduct);
        assertFalse(listProduct.isEmpty());
        assertEquals(productEntityList.size(), listProduct.size());


    }

    @Test
    @DisplayName("Get by Id")
    @Order(6)
    public void givenId_whenFind_thenReturnEntity(){
        Long existId = 1L, doesNotExists = 2L;

        // When
        mockMap();
        when(productRepository.findById(existId))
                .thenReturn(Optional.ofNullable(productEntity));
        when(productRepository.findById(doesNotExists))
                .thenThrow(ResourceNotFoundException.class);
        // Act
        ProductDto find = productService.getById(existId);

        // Assert
        assertNotNull(find);
        assertThat(find)
                .usingRecursiveComparison()
                .isEqualTo(productDto);

        assertThrows(ResourceNotFoundException.class, () -> productService.getById(doesNotExists));

    }

    @Test
    @DisplayName("Get Product By Barcode And isActive")
    @Order(7)
    public void givenBarcode_whenFind_thenReturnProductEntity(){
        // Arrange
        String existBarcode = "123456789";
        List<Product> singleElement = new ArrayList<>();
        singleElement.add(productEntity);


        when(converter.mapToDto(singleElement.get(0), ProductDto.class))
                .thenReturn(productDto);
        when(productRepository.findByBarcode(existBarcode, (byte)1))
                .thenReturn(singleElement);
        // Act
        ProductDto find = productService.getByBarcodeAndIsActive(existBarcode, (byte)1);

        // Assert
        assertNotNull(find);
        assertEquals(productDto.getProductName(),
                find.getProductName());

    }

    @Test
    @DisplayName("Check Exceptions given not exist Barcode")
    @Order(7)
    public void givenWrongBarcode_returnException(){
        // Arrange
        String existBarcode = "123456789";
        List<Product> singleElement = new ArrayList<>();

        // When
        when(productRepository.findByBarcode(existBarcode, (byte)1))
                .thenReturn(singleElement);

        // Assert
        assertThrows(ResourceNotFoundException.class, () ->
                productService.getByBarcodeAndIsActive(existBarcode, (byte)1));
    }

    @Test
    @DisplayName("Check Exceptions given not exist Barcode")
    @Order(8)
    public void givenBarcodeWhichHasOneMoreProduct_returnException(){
        // Arrange
        String existBarcode = "123456789";
        List<Product> singleElement = new ArrayList<>();
        singleElement.add(productEntity);
        singleElement.add(productEntity);
        // When
        when(productRepository.findByBarcode(existBarcode, (byte)1))
                .thenReturn(singleElement);

        // Assert
        assertThrows(SomethingWentWrongException.class, () ->
                productService.getByBarcodeAndIsActive(existBarcode, (byte)1));
    }


    private void mockSingle(){
        mockMap();
        when(productRepository.save(productEntity)).thenReturn(productEntity);
        when(productRepository.findById(1L)).thenReturn(Optional.ofNullable(productEntity));
    }

    private void mockMap(){
        when(converter.mapToEntity(productDto, Product.class))
                .thenReturn(productEntity);
        when(converter.mapToDto(productEntity, ProductDto.class))
                .thenReturn(productDto);
    }







}
