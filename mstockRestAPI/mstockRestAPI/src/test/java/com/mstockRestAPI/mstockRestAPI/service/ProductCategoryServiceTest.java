package com.mstockRestAPI.mstockRestAPI.service;


import com.mstockRestAPI.mstockRestAPI.dto.ProductCategoryDto;
import com.mstockRestAPI.mstockRestAPI.payload.converter.Converter;
import com.mstockRestAPI.mstockRestAPI.entity.ProductCategory;
import com.mstockRestAPI.mstockRestAPI.exception.ResourceNotFoundException;
import com.mstockRestAPI.mstockRestAPI.repository.ProductCategoryRepository;
import com.mstockRestAPI.mstockRestAPI.service.impl.ProductCategoriesServiceImpl;

import com.mstockRestAPI.mstockRestAPI.tools.creator.ProductCategoryCreator;
import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.mockito.Mockito.*;

import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;

import java.util.List;
import java.util.Optional;

@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@TestPropertySource(locations = "/application-test.properties")
public class ProductCategoryServiceTest {

    @Mock
    private ProductCategoryRepository productCategoryRepository;
    @Mock
    private Converter converter;
    @InjectMocks
    private ProductCategoriesServiceImpl productCategoriesService;

    private List<ProductCategory> productCategoryEntityList;
    private List<ProductCategoryDto> productCategoryDtoList;
    private ProductCategory productCategoryEntity;
    private ProductCategoryDto productCategoryDto;

    @BeforeEach()
    public void beforeEach(){
        productCategoryEntity = ProductCategoryCreator.createRandomProductCategoryEntity();
        productCategoryEntityList = ProductCategoryCreator.createProductCategoryEntities();
        productCategoryDto = ProductCategoryCreator.createProductCategoryDto();
        productCategoryDtoList = ProductCategoryCreator.createProductCategoryDtoList();
    }

    @Test
    @DisplayName("Add product category")
    @Order(1)
    public void givenProductCategoryDto_whenSave_returnEntity(){
        // When
        simpleMappingMock();
        when(productCategoryRepository.save(productCategoryEntity))
                .thenReturn(productCategoryEntity);

        // Act
        ProductCategoryDto savedProductCategory = productCategoriesService.add(productCategoryDto);

        //Assert
        singleDtoAssertions(productCategoryDto, savedProductCategory);
    }

    @Test
    @DisplayName("Update product category if exists vice versa")
    @Order(2)
    public void givenProductCategoryDto_whenUpdated_returnDto(){
        Long existsId = 1L, notExists = 2L;
        productCategoryEntity.setId(existsId);
        productCategoryDto.setId(existsId);
        // When
        simpleMappingMock();
        when(productCategoryRepository.findById(existsId))
                .thenReturn(Optional.ofNullable(productCategoryEntity));
        when(productCategoryRepository.save(productCategoryEntity))
                .thenReturn(productCategoryEntity);

        // Act
        ProductCategoryDto productCategoryDtoUpdate = productCategoriesService.update(productCategoryDto);

        // Assert
        singleDtoAssertions(productCategoryDto, productCategoryDtoUpdate);


        // Assert - does not exists
        productCategoryDtoUpdate.setId(notExists);
        assertThrows(ResourceNotFoundException.class, () -> { productCategoriesService.update(productCategoryDto);}
        , "If there is not product category in DB then should be throw exception.");

    }

    @Test
    @DisplayName("Get All product categories")
    @Order(3)
    public void getAllProductCategories_whenFindAll_thenReturnListOfDto(){
        // When
        mockConverterMapping();
        when(productCategoryRepository.findAll())
                .thenReturn(productCategoryEntityList);

        // Act
        List<ProductCategoryDto> productCategoryDtos = productCategoriesService.getAll();

        // Assert
        assertArrayEquals(productCategoryDtoList, productCategoryDtos);
    }

    @Test
    @DisplayName("Get by Id")
    @Order(4)
    public void getById_whenFind_thenReturnDto(){
        Long existId = 1L, doesNotExists = 2L;

        // When
        simpleMappingMock();
        when(productCategoryRepository.findById(existId))
                .thenReturn(Optional.ofNullable(productCategoryEntity));

        // Act - Where exist
        ProductCategoryDto productCategoryDtoFromDb = productCategoriesService.getById(existId);


        // Assert
        singleDtoAssertions(productCategoryDto, productCategoryDtoFromDb);
        assertThrows(ResourceNotFoundException.class, () -> {  productCategoriesService.getById(doesNotExists);}
                , "If there is not product category in DB then should be throw exception.");

    }

    @Test
    @DisplayName("Exists by category name")
    @Order(5)
    public void getByCategoryName_whenFind_thenBoolean(){
        // When
        when(productCategoryRepository.existsByCategoryName(productCategoryDto.getCategoryName()))
                .thenReturn(true);

        // Act
        boolean exists = productCategoriesService.existsByCategoryName(productCategoryDto.getCategoryName());

        // Assert
        assertTrue(exists);
    }

    @Test
    @DisplayName("Get By category name")
    @Order(6)
    public void getByCategoryName_whenFind_thenReturnDto(){
        // When
        when(productCategoryRepository.findByCategoryName(productCategoryDto.getCategoryName()))
                .thenReturn(Optional.ofNullable(productCategoryEntity));
        simpleMappingMock();

        // Act
        ProductCategoryDto productCategoryDtoFromService = productCategoriesService.findByCategoryName(
                productCategoryDto.getCategoryName()
        );

        // Assert
        assertNotNull(productCategoryDtoFromService);
        singleDtoAssertions(productCategoryDto, productCategoryDtoFromService);
    }

    @Test
    @DisplayName("Get By category name when not exists")
    @Order(7)
    public void getByCategoryName_whenDoesNotFind_thenReturnResourceNotFoundException(){
        // When
        when(productCategoryRepository.findByCategoryName(productCategoryDto.getCategoryName()))
                .thenThrow(ResourceNotFoundException.class);
        simpleMappingMock();

        // Assert
        assertThrows(ResourceNotFoundException.class, () -> {
                    productCategoriesService.findByCategoryName(
                            productCategoryDto.getCategoryName()
                    );
                }
                , "If there is not product category in DB then should be throw exception.");
    }

    @Test
    @DisplayName("Get By is Active")
    @Order(8)
    public void getByIsActive_whenFind_thenReturnDtoList(){
        // When
        mockConverterMapping();
        when(productCategoryRepository.findByIsActive((byte)1)).thenReturn(productCategoryEntityList);

        // Act
        List<ProductCategoryDto> listOfDto = productCategoriesService.findByIsActive((byte)1);

        // Assert
        assertNotNull(listOfDto);
        assertFalse(listOfDto.isEmpty());
        assertEquals(listOfDto.size(), productCategoryDtoList.size());
    }



    private void assertArrayEquals(List<ProductCategoryDto> expected,
                                   List<ProductCategoryDto> actual) {
        assertNotNull(actual);
        assertFalse(actual.isEmpty());
        assertEquals(expected.size(), actual.size());
    }


    private void simpleMappingMock(){
        when(converter.mapToEntity(productCategoryDto, ProductCategory.class))
                .thenReturn(productCategoryEntity);
        when(converter.mapToDto(productCategoryEntity, ProductCategoryDto.class))
                .thenReturn(productCategoryDto);
    }

    private void mockConverterMapping() {
        when(converter.mapToDto(any(), eq(ProductCategoryDto.class)))
                .thenAnswer(invocation -> {
                    ProductCategory companyDtoArgument = invocation.getArgument(0);
                    return convertProductCategoryToDto(companyDtoArgument);
                });

        when(converter.mapToEntity(any(), eq(ProductCategory.class)))
                .thenAnswer(invocation -> {
                    ProductCategoryDto companyDtoArgument = invocation.getArgument(0);
                    return convertProductCategoryDtoToEntity(companyDtoArgument);
                });

    }

    private ProductCategory convertProductCategoryDtoToEntity(ProductCategoryDto productCategoryDto) {
        return ProductCategory.builder()
                .categoryName(productCategoryDto.getCategoryName())
                .isActive(productCategoryDto.getIsActive())
                .description(productCategoryDto.getDescription())

                .build();

    }

    private ProductCategoryDto convertProductCategoryToDto(ProductCategory productCategory) {
        return ProductCategoryDto.builder()
                .categoryName(productCategory.getCategoryName())
                .description(productCategory.getDescription())
                .isActive(productCategory.getIsActive())
                .build();

    }

    private void singleDtoAssertions(ProductCategoryDto expected, ProductCategoryDto actual){
        assertNotNull(actual);
        assertEquals(expected.getCategoryName(), actual.getCategoryName()
        , "Product category name must be identical.");
        assertEquals(expected.getId(), actual.getId()
                , "Product category id must be identical.");
        assertEquals(expected.getDescription(), actual.getDescription()
                , "Product category description name must be identical.");
        assertEquals(expected.getIsActive(), actual.getIsActive()
                , "Product category id must be identical.");
    }



}
