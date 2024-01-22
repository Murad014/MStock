package com.mstockRestAPI.mstockRestAPI.repository;


import com.mstockRestAPI.mstockRestAPI.entity.ProductBarcode;
import com.mstockRestAPI.mstockRestAPI.tools.creator.ProductBarcodeCreator;
import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;

import java.util.List;
import java.util.Random;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.InstanceOfAssertFactories.list;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@TestPropertySource(locations = "/application-test.properties")
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class ProductBarcodeRepositoryTest {

    @Autowired
    private ProductBarcodeRepository productBarcodeRepository;
    private List<ProductBarcode> productBarcodeEntityList;
    private ProductBarcode productBarcodeEntity;

    private Random random = new Random();
    @BeforeEach
    public void beforeEach(){
        productBarcodeEntity = ProductBarcodeCreator.entity();
        productBarcodeEntityList = ProductBarcodeCreator.entityList();

        productBarcodeRepository.deleteAll();
    }

    @Test
    @DisplayName("Add")
    @Order(1)
    public void givenEntity_thenAdd_thenReturnEntity(){
        // Act
        ProductBarcode productBarcode = productBarcodeRepository.save(productBarcodeEntity);

        // Assert
        assertThat(productBarcode)
                .usingRecursiveComparison()
                .isEqualTo(productBarcodeEntity);
    }

    @Test
    @DisplayName("Update")
    @Order(1)
    public void givenEntity_thenUpdate_thenReturnEntity(){
        String updatedBarcode = "1231412313";

        // When
        ProductBarcode productBarcodeAdd = productBarcodeRepository.save(productBarcodeEntity);
        ProductBarcode find = productBarcodeRepository.findById(productBarcodeAdd.getId())
                .orElse(null);

        assertNotNull(find);
        find.setBarcode(updatedBarcode);
        find.setIsActive((byte)0);

        // Act
        ProductBarcode productBarcodeUpdate = productBarcodeRepository.save(find);


        // Assert
        assertThat(productBarcodeUpdate)
                .usingRecursiveComparison()
                .isEqualTo(find);

    }

    @Test
    @DisplayName("Get All")
    @Order(2)
    public void whenGetAll_thenReturnList(){
        // When
        productBarcodeRepository.saveAll(productBarcodeEntityList);

        // Act
        List<ProductBarcode> getAll = productBarcodeRepository.findAll();

        // Assert
        assertNotNull(getAll);
        assertFalse(getAll.isEmpty());
        assertEquals(productBarcodeEntityList.size(), getAll.size());

    }

    @Test
    @DisplayName("Get By Id")
    @Order(3)
    public void whenGetById_thenReturnEntity(){
        // When
        ProductBarcode save = productBarcodeRepository.save(productBarcodeEntity);

        // Then
        ProductBarcode productBarcode = productBarcodeRepository.findById(save.getId())
                .orElse(null);


        // Assert
        assertNotNull(productBarcode);
        assertEquals(save.getId(), productBarcode.getId());
        assertEquals(save.getBarcode(), productBarcode.getBarcode());
        assertEquals(save.getIsActive(), productBarcode.getIsActive());
    }


    @ParameterizedTest
    @ValueSource(bytes = { 1, 0 })
    @DisplayName("Get All Where isActive")
    @Order(4)
    public void whenGetAllWhereIsActive_returnList(byte isActive){
        // Arrange
        List<ProductBarcode> list = productBarcodeEntityList.stream().filter(
                barcode -> barcode.getIsActive() == isActive
        ).toList();


        // When
        productBarcodeRepository.saveAll(productBarcodeEntityList);

        // Then
        List<ProductBarcode> getWhereIsActive = productBarcodeRepository.findByIsActive(isActive);

        // Assert
        assertNotNull(getWhereIsActive);
        assertFalse(getWhereIsActive.isEmpty());
        assertFalse(
                getWhereIsActive.stream().anyMatch(
                        barcode -> barcode.getIsActive() != isActive
                )
        );
        assertEquals(list.size(), getWhereIsActive.size());
    }

    @Test
    @DisplayName("Exist by barcode")
    @Order(5)
    public void thenFind_thenReturnBoolean(){

        // Save All
        List<ProductBarcode> saveAll = productBarcodeRepository.saveAll(productBarcodeEntityList);
        ProductBarcode existsBarcode = saveAll.get(random.nextInt(100) % saveAll.size());

        // Act
        boolean existByBarcode = productBarcodeRepository.existsByBarcode(existsBarcode.getBarcode());

        assertTrue(existByBarcode);


    }







}
