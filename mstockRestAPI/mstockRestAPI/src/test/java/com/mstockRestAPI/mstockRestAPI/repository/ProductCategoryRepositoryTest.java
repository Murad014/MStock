package com.mstockRestAPI.mstockRestAPI.repository;

import com.mstockRestAPI.mstockRestAPI.entity.ProductCategory;
import com.mstockRestAPI.mstockRestAPI.tools.creator.ProductCategoryCreator;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;

import static org.junit.jupiter.api.Assertions.*;

import java.sql.Timestamp;
import java.util.*;

@SpringBootTest
@TestPropertySource(locations = "/application-test.properties")
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class ProductCategoryRepositoryTest {

    @Autowired
    private ProductCategoryRepository productCategoryRepository;
    private List<ProductCategory> productCategoryList;
    private ProductCategory productCategory;

    @BeforeEach()
    public void beforeEach(){
        productCategory = ProductCategoryCreator.createRandomProductCategoryEntity();
        productCategoryList = ProductCategoryCreator.createProductCategoryEntities();
        productCategoryRepository.deleteAll();
    }

    @Test
    @DisplayName("Add product Category")
    @Order(1)
    public void givenProductCategoryEntity_whenSave_thenReturnProductCategoryEntity(){
        // Act
        ProductCategory savedProductCategory = productCategoryRepository.save(productCategory);

        // Assert
        assertEquals(productCategory, savedProductCategory);
    }

    @Test
    @DisplayName("Get product category by id")
    @Order(2)
    public void givenProductCategoryId_whenFind_thenReturnProductCategoryEntity(){

        // Act
        ProductCategory saveProductCategory = productCategoryRepository.save(productCategory);
        Long existsProductCategoryId = saveProductCategory.getId();

        ProductCategory findProductCategory = productCategoryRepository
                .findById(existsProductCategoryId)
                .orElse(null);

        // Assert
        productCategory.setId(existsProductCategoryId);
        assertions(productCategory, findProductCategory);
    }

    @Test
    @DisplayName("Update product Category")
    @Order(3)
    public void givenProductCategoryEntity_whenUpdate_thenReturnProductCategoryEntity(){
        String name = "Books";
        String description = "Updated description";
        Timestamp updateDate = Timestamp.valueOf("2023-12-03 17:48:52.083725");
        Byte isActive = 0;


        ProductCategory productCategorySave = productCategoryRepository.save(productCategory);

        // Act
        ProductCategory findProductCategory = productCategoryRepository
                .findById(productCategorySave.getId())
                .orElse(null);

        assertNotNull(findProductCategory);

        // Update fields
        findProductCategory.setCategoryName(name);
        findProductCategory.setDescription(description);
        findProductCategory.setUpdatedDate(updateDate);
        findProductCategory.setIsActive(isActive);

        // Act
        ProductCategory updateProductCategory = productCategoryRepository.save(findProductCategory);

        // Assert
        assertions(findProductCategory, updateProductCategory);
    }

    @Test
    @DisplayName("Get all product categories")
    @Order(4)
    public void whenFindAll_thenReturnListOfProductCategories(){
        productCategoryRepository.saveAll(productCategoryList);

        List<ProductCategory> allProductCategoriesFromDb = productCategoryRepository.findAll();

        // Assert
        assertNotNull(allProductCategoriesFromDb);
        assertFalse(allProductCategoriesFromDb.isEmpty());
        assertEquals(productCategoryList.size(), allProductCategoriesFromDb.size());
    }

    @Test
    @DisplayName("Get product category by product category name")
    @Order(5)
    public void findById_whenFind_thenReturnProductCategory(){
        // Act
        ProductCategory productCategorySaved = productCategoryRepository.save(productCategory);
        ProductCategory productCategoryFromDb = productCategoryRepository.findByCategoryName(
                productCategorySaved.getCategoryName()
        );

        // Assert
        assertions(productCategorySaved, productCategoryFromDb);
    }

    @Test
    @DisplayName("Get all where active is 1")
    @Order(6)
    public void findByCategoryNameWhereActive_whenFind_thenReturnListOfProductCategories(){
        // Act
        Byte active = 1;

        List<ProductCategory> productCategorySaved = productCategoryRepository.saveAll(productCategoryList);
        List<ProductCategory> productCategoryIsActive = productCategoryRepository.findByIsActive(active);

        // Assert
        assertNotNull(productCategoryIsActive);
        assertFalse(productCategoryIsActive.isEmpty(),
                "Product Categories list must NOT be empty");

        assertEquals(productCategorySaved.size(), productCategoryIsActive.size());
    }

    @Test
    @DisplayName("Get all where active is 0")
    @Order(7)
    public void findByCategoryNameWhereNotActive_whenFind_thenReturnListOfProductCategories(){
        // Act
        Byte notActive = 0;
        productCategoryList = productCategoryList.stream().peek(productCategory ->
                productCategory.setIsActive((byte) 0)).toList();

        List<ProductCategory> productCategorySaved = productCategoryRepository.saveAll(productCategoryList);

        List<ProductCategory> productCategoryIsActive = productCategoryRepository.findByIsActive(notActive);


        // Assert
        assertNotNull(productCategoryIsActive);
        assertFalse(productCategoryIsActive.isEmpty(),
                "Product Categories list must NOT be empty");
        assertEquals(productCategorySaved.size(), productCategoryIsActive.size());
    }

    private void assertions(ProductCategory expected, ProductCategory actual){
        assertNotNull(actual);

        assertEquals(expected.getId(), actual.getId(),
                "Id must be identical");

        assertEquals(expected.getCategoryName(), actual.getCategoryName(),
                "Category name must identical");

        assertEquals(expected.getDescription(), actual.getDescription(),
                "Description must be identical");

        assertEquals(expected.getIsActive(), actual.getIsActive()
                ,"isActive must identical");

        assertEquals(expected.getCreateDate(), actual.getCreateDate(),
                "Create Date must be identical");

        assertEquals(expected.getUpdatedDate(), actual.getUpdatedDate(),
                "Update Date must be identical");
    }



}
