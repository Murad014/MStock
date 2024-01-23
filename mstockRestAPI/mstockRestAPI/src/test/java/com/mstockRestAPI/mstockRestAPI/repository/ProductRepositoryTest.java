package com.mstockRestAPI.mstockRestAPI.repository;


import com.mstockRestAPI.mstockRestAPI.entity.*;
import com.mstockRestAPI.mstockRestAPI.tools.creator.ProductBarcodeCreator;
import com.mstockRestAPI.mstockRestAPI.tools.creator.ProductCreator;
import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;
import org.hibernate.Hibernate;
import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.TestPropertySource;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@TestPropertySource(locations = "/application-test.properties",
properties = "server.port=8081")
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
//@DataJpaTest
public class ProductRepositoryTest {
    //    @MockBean
    //    private EntityManager entityManager;

    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private ProductCategoryRepository productCategoryRepository;
    @Autowired
    private CompanyRepository companyRepository;
    @Autowired
    private ProductBarcodeRepository productBarcodeRepository;
    @Autowired
    private ProductSalePricesRepository productSalePricesRepository;


    private List<Product> productEntityList;
    private Product productEntity;

    @BeforeEach
    public void beforeEach(){
        productEntity = ProductCreator.entity();
        productEntityList = ProductCreator.entityList();

        productRepository.deleteAll();
        productBarcodeRepository.deleteAll();
        companyRepository.deleteAll();
        productSalePricesRepository.deleteAll();
    }

    @Test
    @DisplayName("Add")
    @Order(1)
    @Transactional
    public void givenProductEntity_whenAdd_thenReturnEntity(){
        // Arrange
        saveCategoryAndCompanyAndSet();

        Product savedProduct = productRepository.save(productEntity);

        // Assert
        assertThat(savedProduct)
                .usingRecursiveComparison()
                .isEqualTo(productEntity);

//        verify(entityManager, times(1)).persist(any());
//        verify(entityManager, times(2)).persist(any());

    }

    @Test
    @DisplayName("Update")
    @Order(2)
    @Transactional
    public void givenProductEntity_whenUpdate_thenReturnEntity(){
        // Arrange
        saveCategoryAndCompanyAndSet();

        // Act
        Product save = productRepository.save(productEntity);
        save.setProductName("Updated");
        save.getProductSalePrices().add(
                ProductSalePrice.builder().sellingPrice(
                        BigDecimal.valueOf(121.25)
                )
                .build()
        );

        // When Find
        Product update = productRepository.save(productEntity);
        assertTrue(update.getProductSalePrices().stream().anyMatch(
                productSalePrice -> Objects.equals(productSalePrice.getSellingPrice(), BigDecimal.valueOf(121.25))
        ));
        assertThat(update)
                .usingRecursiveComparison()
                .isEqualTo(productEntity);
    }

    @Test
    @DisplayName("Find By id")
    @Transactional
    @Order(3)
    public void givenId_whenFind_thenReturnEntity(){
        saveCategoryAndCompanyAndSet();

        // Act
        productRepository.save(productEntity);
        Product newProduct = productRepository
                .findById(productEntity.getId()).orElse(null);

        // Assert
        Assertions.assertNotNull(newProduct);
        assertThat(newProduct)
                .usingRecursiveComparison()
                .isEqualTo(productEntity);
    }

    @Test
    @DisplayName("Get All")
    @Transactional
    @Order(4)
    public void whenGetAll_thenReturnListEntity(){
        saveProductReferences();

        // Act
        List<Product> products = productRepository.saveAll(productEntityList);

        // Arrange
        List<Product> findAll = productRepository.findAll();

        assertNotNull(findAll);
        assertFalse(findAll.isEmpty());
        assertEquals(products.size(), findAll.size());
    }

    @ParameterizedTest
    @ValueSource(bytes = {0, 1})
    @DisplayName("Get where isActive")
    @Transactional
    @Order(5)
    public void whenGetAllByIsActive_thenReturnList(byte isActive){
        saveProductReferences();

        // Act
        List<Product> productListExist = productEntityList
                .stream()
                .filter(product -> product.getIsActive() == isActive)
                .toList();

        // Save All
        productRepository.saveAll(productEntityList);

        // Find All by IsActive
        List<Product> findAllByIsActive = productRepository.findByIsActive(isActive);

        // Assert
        assertNotNull(findAllByIsActive);
        assertFalse(findAllByIsActive.isEmpty());
        assertFalse(findAllByIsActive.stream().anyMatch(
                product -> product.getIsActive() != isActive
        ));
        assertEquals(productListExist.size(), findAllByIsActive.size());

    }

    @Test
    @DisplayName("Get by Barcode")
    @Transactional
    @Order(6)
    public void whenFindByBarcode_thenReturnProduct(){
        saveProductReferences();

        // Save All
        List<Product> productSave = productRepository.saveAll(productEntityList);

        Product findInList = productSave.stream()
                .filter(
                        product -> product.getId() > 3 % productSave.size()
                        && product.getIsActive() == 1
                ) // mod used because might be size change.
                // Prevent the error
                .findAny().orElse(null);

        assert findInList != null;
        ProductBarcode actualBarcodeInList = findInList.getProductBarcodeList().get(
                3 % findInList.getProductBarcodeList().size()
        );

        // Find product by Barcode in Database
        List<Product> productByBarcode = productRepository.findByBarcode(actualBarcodeInList.getBarcode(),
                (byte) 1);

        assertNotNull(productByBarcode);
        assertFalse(productByBarcode.isEmpty());
        assertEquals(1, productByBarcode.size()); // Everytime size is 1 because there is no
        // duplicate barcode in ProductBarcode table..

    }

    @RepeatedTest(5)
    @DisplayName("Find By ProductName Where isActive")
    @Transactional
    @Order(7)
    public void findByProductName_whenFind_thenReturnList(){
        String keyword = "x".toLowerCase();
        byte isActive = 1;
        saveProductReferences();

        // From List find
        List<Product> likeProductNameList = productEntityList.stream()
                .filter(product ->
                        product.getProductName().toLowerCase().contains(keyword)
                                &&
                                product.getIsActive() == isActive)
                .toList();

        // Save All
        productRepository.saveAll(productEntityList);

        // From Database find
        List<Product> likeProductFromDB = productRepository.findByProductName(keyword, isActive);

        // Assert
        assertNotNull(likeProductFromDB);
        assertFalse(likeProductFromDB.stream().anyMatch(
                product -> product.getIsActive() != isActive
        ));
        assertEquals(likeProductNameList.size(), likeProductFromDB.size());
    }





    private void saveProductReferences(){
        for(Product productEntity: productEntityList) {
            ProductCategory productCategory = productCategoryRepository.save(productEntity.getCategory());
            Company productCompany = companyRepository.save(productEntity.getCompany());
            List<ProductSalePrice> productSalePrice =
                    productSalePricesRepository.saveAll(productEntity.getProductSalePrices());
            List<ProductBarcode> productBarcodeList =
                    productBarcodeRepository.saveAll(productEntity.getProductBarcodeList());
            productEntity.setProductBarcodeList(productBarcodeList);
            productEntity.setProductSalePrices(productSalePrice);
            productEntity.setCategory(productCategory);
            productEntity.setCompany(productCompany);
        }

    }

    private void saveCategoryAndCompanyAndSet(){
        ProductCategory productCategory = productCategoryRepository.save(productEntity.getCategory());
        Company productCompany = companyRepository.save(productEntity.getCompany());
        List<ProductSalePrice> productSalePrice =
                productSalePricesRepository.saveAll(productEntity.getProductSalePrices());
        List<ProductBarcode> productBarcodeList =
                productBarcodeRepository.saveAll(productEntity.getProductBarcodeList());

        productEntity.setProductBarcodeList(productBarcodeList);
        productEntity.setProductSalePrices(productSalePrice);
        productEntity.setCategory(productCategory);
        productEntity.setCompany(productCompany);
    }

    private void saveCategoryAndCompanyListAndSet(){
        ProductCategory productCategory = productCategoryRepository.save(productEntity.getCategory());
        Company productCompany = companyRepository.save(productEntity.getCompany());

        productEntity.setCategory(productCategory);
        productEntity.setCompany(productCompany);
    }


}
