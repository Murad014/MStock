package com.mstockRestAPI.mstockRestAPI.repository;

import com.mstockRestAPI.mstockRestAPI.entity.ProductPicture;
import com.mstockRestAPI.mstockRestAPI.tools.creator.ProductPictureCreator;
import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import static org.junit.jupiter.api.Assertions.*;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@TestPropertySource(locations = "/application-test.properties")
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class ProductPictureRepositoryTest {
    @Autowired
    private ProductPictureRepository productPictureRepository;

    private ProductPicture productPictureEntity;
    private List<ProductPicture> productPicturesEntityList;

    @BeforeEach
    public void beforeEach(){
        productPictureEntity = ProductPictureCreator.entity();
        productPicturesEntityList = ProductPictureCreator.entityList();

        productPictureRepository.deleteAll();
    }

    @Test
    @DisplayName("Add")
    @Order(1)
    public void givenEntity_whenAdd_thenReturnEntity(){
        // Act
        ProductPicture saved = productPictureRepository.save(productPictureEntity);

        // Assert
        assertThat(productPictureEntity)
                .usingRecursiveComparison()
                .isEqualTo(saved);
    }

    @Test
    @DisplayName("Update")
    @Order(2)
    public void givenEntity_whenUpdate_thenReturnEntity(){
        String updatedPictureName = "Updated Picture name";
        // Act
        ProductPicture saved = productPictureRepository.save(productPictureEntity);
        saved.setPictureName(updatedPictureName);
        ProductPicture updated = productPictureRepository.save(saved);

        // Assert
        assertNotNull(updated);
        assertEquals(updatedPictureName, updated.getPictureName());

    }

    @Test
    @DisplayName("Fetch All")
    @Order(3)
    public void whenFetchedAll_thenReturnList(){
        // Arrange
        List<ProductPicture> savedAll = productPictureRepository.saveAll(productPicturesEntityList);

        // Act
        List<ProductPicture> fetchAll = productPictureRepository.findAll();

        assertNotNull(fetchAll);
        assertFalse(fetchAll.isEmpty());
        assertEquals(savedAll.size(), fetchAll.size());
    }

    @ParameterizedTest
    @ValueSource(bytes = {0, 1})
    @DisplayName("Fetch All By IsActive")
    @Order(4)
    public void whenFetchedAllByIsActive_thenReturnList(byte isActive){
        List<ProductPicture> byActiveList = productPicturesEntityList.stream()
                .filter(picture -> picture.getIsActive() == isActive)
                .toList();

        productPictureRepository.saveAll(productPicturesEntityList);

        // Act
        List<ProductPicture> activeAllFromDB = productPictureRepository.findByIsActive(isActive);

        // Assert
        assertNotNull(activeAllFromDB);
        assertFalse(activeAllFromDB.isEmpty());
        assertFalse(activeAllFromDB.stream()
                .anyMatch(picture -> picture.getIsActive() != isActive));
        assertEquals(byActiveList.size(), activeAllFromDB.size());
    }





}
