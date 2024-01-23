package com.mstockRestAPI.mstockRestAPI.repository;

import com.mstockRestAPI.mstockRestAPI.entity.Product;
import com.mstockRestAPI.mstockRestAPI.entity.ProductSalePrice;
import com.mstockRestAPI.mstockRestAPI.tools.creator.ProductSalePricesCreator;
import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;

import java.math.BigDecimal;
import java.util.*;

import static org.assertj.core.api.Assertions.*;
import static org.assertj.core.api.InstanceOfAssertFactories.list;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@TestPropertySource(locations = "/application-test.properties")
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class ProductSalePricesRepositoryTest {

    @Autowired
    private ProductSalePricesRepository productSalePricesRepository;

    private ProductSalePrice productSalePriceEntity;
    private List<ProductSalePrice> productSalePriceList;

    @BeforeEach
    public void beforeEach(){
        productSalePriceEntity = ProductSalePricesCreator.entity();
        productSalePriceList = ProductSalePricesCreator.entityList();

        productSalePricesRepository.deleteAll();
    }

    @Test
    @DisplayName("Add")
    @Order(1)
    public void givenEntity_whenAdd_thenReturnEntity(){
        // Act
        ProductSalePrice productSalePrice = productSalePricesRepository.save(productSalePriceEntity);

        // Assert
        assertThat(productSalePrice)
                .usingRecursiveComparison()
                .isEqualTo(productSalePriceEntity);
    }

    @Test
    @DisplayName("Update")
    @Order(2)
    public void givenEntity_whenUpdate_thenReturnEntity(){
        BigDecimal updatePrice = BigDecimal.valueOf(2.22);

        // When
        ProductSalePrice productSalePrice = productSalePricesRepository.save(productSalePriceEntity);
        productSalePrice.setSellingPrice(updatePrice);

        // Then
        ProductSalePrice productSalePriceUpdate = productSalePricesRepository.save(productSalePrice);

        // Assert
        assertThat(productSalePriceUpdate)
                .usingRecursiveComparison()
                .ignoringFields("createdDate")
                .ignoringFields("updatedDate")
                .isEqualTo(productSalePrice);
    }

    @Test
    @DisplayName("Get By Id")
    @Order(3)
    public void givenId_whenFind_thenReturnEntity(){
        // When
        ProductSalePrice save = productSalePricesRepository.save(productSalePriceEntity);

        // Find
        ProductSalePrice find = productSalePricesRepository.findById(save.getId())
                .orElse(null);

        // Assert
        assertNotNull(find);
        assertThat(find)
                .usingRecursiveComparison()
                .withComparatorForType(Double::compare, Double.class)
                .ignoringFields("createdDate")
                .ignoringFields("sellingPrice")
                .isEqualTo(save);

        assertEquals(save.getSellingPrice().doubleValue(), find.getSellingPrice().doubleValue());

    }

    @Test
    @DisplayName("Get All")
    @Order(4)
    public void whenFindAll_thenReturnList(){
        // Act
        productSalePricesRepository.saveAll(productSalePriceList);

        // When
        List<ProductSalePrice> listProductSales = productSalePricesRepository.findAll();

        // Assert
        assertNotNull(listProductSales);
        assertFalse(listProductSales.isEmpty());
        assertEquals(productSalePriceList.size(), listProductSales.size());
    }

    @ParameterizedTest
    @ValueSource(bytes = { 1, 0 })
    @DisplayName("Get All Where is Active")
    @Order(5)
    public void whenFindAllByIsActive_thenReturnList(byte isActive) {
        // Arrange
        List<ProductSalePrice> listWhereActive = productSalePriceList
                .stream()
                .filter(salePrice -> salePrice.getIsActive() == isActive)
                .toList();

        // Act
        List<ProductSalePrice> productSalePricesFromDB = productSalePricesRepository.saveAll(productSalePriceList);

        // Filter
        List<ProductSalePrice> filterWhereActiveFromDB = productSalePricesRepository.findByIsActive(isActive);

        // Assert
        assertNotNull(productSalePricesFromDB);
        assertFalse(productSalePricesFromDB.isEmpty());
        assertFalse(filterWhereActiveFromDB
                .stream().anyMatch(price -> price.getIsActive() != isActive));
        assertEquals(listWhereActive.size(), filterWhereActiveFromDB.size());
    }



}
