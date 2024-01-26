package com.mstockRestAPI.mstockRestAPI.service;

import com.mstockRestAPI.mstockRestAPI.dto.ProductBarcodeDto;
import com.mstockRestAPI.mstockRestAPI.dto.ProductPictureDto;
import com.mstockRestAPI.mstockRestAPI.entity.ProductBarcode;
import com.mstockRestAPI.mstockRestAPI.entity.ProductPicture;
import com.mstockRestAPI.mstockRestAPI.exception.SqlProcessException;
import com.mstockRestAPI.mstockRestAPI.payload.converter.Converter;
import com.mstockRestAPI.mstockRestAPI.repository.ProductPictureRepository;
import com.mstockRestAPI.mstockRestAPI.service.impl.ProductPictureServiceImpl;
import com.mstockRestAPI.mstockRestAPI.tools.creator.ProductBarcodeCreator;
import com.mstockRestAPI.mstockRestAPI.tools.creator.ProductPictureCreator;
import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@TestPropertySource(locations = "/application-test.properties")
public class ProductPictureServiceTest {


    @Mock
    private Converter converter;
    @Mock
    private ProductPictureRepository productPictureRepository;
    @InjectMocks
    private ProductPictureServiceImpl productPictureService;

    private ProductPicture productPictureEntity;
    private List<ProductPicture> productPicturesEntityList;
    private ProductPictureDto productPictureDto;
    private List<ProductPictureDto> productPictureDtoList;

    @BeforeEach
    public void beforeEach(){
        productPictureEntity = ProductPictureCreator.entity();
        productPicturesEntityList = ProductPictureCreator.entityList();

        productPictureDto = ProductPictureCreator.dto();
        productPictureDtoList = ProductPictureCreator.dtoList();
    }


    @Test
    @DisplayName("Add")
    @Order(1)
    public void givenEntity_whenAdd_thenReturnEntity() throws SqlProcessException {
        // When
        singleConverterMapping();
        when(productPictureRepository.save(productPictureEntity)).thenReturn(productPictureEntity);

        // Act
        ProductPictureDto save = productPictureService.add(productPictureDto);

        // Assert
        assertNotNull(save);
        assertThat(save)
                .usingRecursiveComparison()
                .isEqualTo(productPictureDto);

        // Verify
        verify(productPictureRepository, times(1)).save(productPictureEntity);


    }

    @Test
    @DisplayName("Update exist Id")
    @Order(2)
    public void givenEntity_whenUpdate_thenReturnEntity(){
        String pictureName = "Updated picture name";
        // When
        productPictureDto.setId(1L);
        productPictureEntity.setId(1L);
        singleConverterMapping();
        when(productPictureRepository.save(productPictureEntity)).thenReturn(productPictureEntity);
        when(productPictureRepository.findById(productPictureEntity.getId()))
                .thenReturn(Optional.ofNullable(productPictureEntity));
        // Act
        productPictureDto.setPictureName(pictureName);
        ProductPictureDto productBarcodeUpdated = productPictureService.update(productPictureDto);

        // Assert
        assertNotNull(productBarcodeUpdated);
        assertThat(productBarcodeUpdated)
                .usingRecursiveComparison()
                .isEqualTo(productPictureDto);
        assertEquals(pictureName, productBarcodeUpdated.getPictureName());;
        // Verify
        verify(productPictureRepository, times(1)).save(productPictureEntity);
        verify(productPictureRepository, times(1)).findById(productPictureEntity.getId());
    }

    @ParameterizedTest
    @ValueSource(bytes = {0, 1})
    @DisplayName("Find All By isActive")
    @Order(3)
    public void returnAllListByIsActive(byte isActive){
        // Filter
        List<ProductPicture> productPictureListFilter = productPicturesEntityList.stream()
                .filter(product -> product.getIsActive() == isActive)
                .toList();

        // When
        mockConverterMapping();
        when(productPictureRepository.findByIsActive(isActive)).thenReturn(productPictureListFilter);

        // Act
        List<ProductPictureDto> listFromDB = productPictureService.findByIsActive(isActive);

        // Assert
        assertNotNull(listFromDB);
        assertFalse(listFromDB.isEmpty());
        assertFalse(listFromDB.stream()
                .anyMatch(product -> product.getIsActive() != isActive));
        assertEquals(productPictureListFilter.size(), listFromDB.size());

        // Verify
        verify(productPictureRepository, times(1)).findByIsActive(isActive);
    }



    private void singleConverterMapping(){
        when(converter.mapToEntity(productPictureDto, ProductPicture.class))
                .thenReturn(productPictureEntity);
        when(converter.mapToDto(productPictureEntity, ProductPictureDto.class))
                .thenReturn(productPictureDto);
    }
    private void mockConverterMapping() {
        when(converter.mapToDto(any(), eq(ProductPictureDto.class)))
                .thenAnswer(invocation -> {
                    ProductPicture productPictureArgument = invocation.getArgument(0);
                    return convertProductEntityToDto(productPictureArgument);
                });

        when(converter.mapToEntity(any(), eq(ProductPicture.class)))
                .thenAnswer(invocation -> {
                    ProductPictureDto productPictureDtoArgument = invocation.getArgument(0);
                    return convertProductDtoToEntity(productPictureDtoArgument);
                });
    }

    private ProductPictureDto convertProductEntityToDto(ProductPicture productPicture) {
        return  ProductPictureDto.builder()
                .pictureName(productPicture.getPictureName())
                .isActive(productPicture.getIsActive())
                .build();
    }

    private ProductPicture convertProductDtoToEntity(ProductPictureDto productPictureDto) {
        return ProductPicture.builder()
                .pictureName(productPictureDto.getPictureName())
                .isActive(productPictureDto.getIsActive())
                .build();
    }



}
