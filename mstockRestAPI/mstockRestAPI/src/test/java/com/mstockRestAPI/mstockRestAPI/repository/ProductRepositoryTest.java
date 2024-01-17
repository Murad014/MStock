package com.mstockRestAPI.mstockRestAPI.repository;


import com.mstockRestAPI.mstockRestAPI.entity.Company;
import com.mstockRestAPI.mstockRestAPI.entity.Product;
import com.mstockRestAPI.mstockRestAPI.entity.ProductCategory;
import com.mstockRestAPI.mstockRestAPI.tools.creator.ProductCreator;
import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;
import org.hibernate.Hibernate;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.TestPropertySource;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

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


    private List<Product> productEntityList;
    private Product productEntity;

    @BeforeEach
    public void beforeEach(){
        productEntity = ProductCreator.entity();
        productEntityList = ProductCreator.entityList();

        productRepository.deleteAll();
    }

    @Test
    @DisplayName("Add")
    @Order(1)
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

        // When Find
        Product update = productRepository.save(productEntity);

        assertThat(update)
                .usingRecursiveComparison()
                .isEqualTo(productEntity);
    }

    @Test
    @DisplayName("Find By id")
    @Transactional
    @Order(3)
    public void givenId_whenFind_thenReturnDto(){
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
    public void whenGetAll_thenReturnListDto(){

    }



    private void saveCategoryAndCompanyAndSet(){
        ProductCategory productCategory = productCategoryRepository.save(productEntity.getCategory());
        Company productCompany = companyRepository.save(productEntity.getCompany());

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
