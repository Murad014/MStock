package com.mstockRestAPI.mstockRestAPI.repository;

import com.mstockRestAPI.mstockRestAPI.entity.*;
import com.mstockRestAPI.mstockRestAPI.enums.SaleStatus;
import com.mstockRestAPI.mstockRestAPI.tools.creator.CustomerCreator;
import com.mstockRestAPI.mstockRestAPI.tools.creator.ProductSaleCreator;
import jakarta.transaction.Transactional;
import org.aspectj.lang.annotation.Before;
import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.from;
import static org.junit.jupiter.api.Assertions.*;
import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@TestPropertySource(locations = "/application-test.properties",
properties = "server.port=8081")
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class ProductSaleRepositoryTest {

    @Autowired
    private ProductSaleRepository productSaleRepository;
    @Autowired
    private ReceiptRepository receiptRepository;
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

    @Autowired
    private CustomerRepository customerRepository;

    private static final String staticReceiptNumber = "14123123";

    private ProductSale productSaleEntity;
    private List<ProductSale> productSaleEntityList;

    @BeforeEach
    public void beforeEach(){
        productSaleEntity = ProductSaleCreator.entity();
        productSaleEntityList = ProductSaleCreator.entityList();

        productSaleRepository.deleteAll();
        productRepository.deleteAll();
        productBarcodeRepository.deleteAll();
        receiptRepository.deleteAll();
    }

    @Test
    @DisplayName("Add")
    @Order(1)
    @Transactional
    public void givenEntity_thenSave_thenReturnEntity(){
        // Arrange
        saveReferencesAndSet(productSaleEntity);

        // Act
        ProductSale productSale = productSaleRepository.save(productSaleEntity);

        // Assert
        assertThat(productSale)
                .usingRecursiveComparison()
                .isEqualTo(productSaleEntity);
    }

    @Test
    @DisplayName("Update")
    @Order(2)
    @Transactional
    public void givenEntity_whenUpdate_thenReturnEntity(){
        // Arrange
        saveReferencesAndSet(productSaleEntity);
        ProductSale productSale = productSaleRepository.save(productSaleEntity);
        productSale.getProduct().setProductName("Update test ProductName");
        productSale.setComment("Comment Update Test");


        ProductSale productSaleFromDB = productSaleRepository.findById(productSale.getId()).orElse(null);
        assertNotNull(productSaleFromDB);
        ProductSale productSaleUpdate = productSaleRepository.save(productSaleFromDB);

        assertNotNull(productSaleUpdate);
        assertThat(productSaleUpdate)
                .usingRecursiveComparison()
                .isEqualTo(productSale);
    }

    @Test
    @DisplayName("Fetch All")
    @Order(3)
    @Transactional
    public void fetchAllProductSales(){
        // Arrange
        for(ProductSale productSale: productSaleEntityList)
            saveReferencesAndSet(productSale);

        productSaleRepository.saveAll(productSaleEntityList);

        // Fetch All
        List<ProductSale> list = productSaleRepository.findAll();

        // Assert
        assertNotNull(list);
        assertFalse(list.isEmpty());
        assertEquals(productSaleEntityList.size(), list.size());
    }

    @ParameterizedTest
    @ValueSource(strings = {"SOLD", "RETURNED"})
    @DisplayName("Get by sale status")
    @Order(4)
    @Transactional
    public void getAllBySaleStatusWithValidStatus(String status){
        // Filter
        List<ProductSale> salesFilter = productSaleEntityList
                .stream()
                .filter(sale -> sale.getSaleStatus() == SaleStatus.valueOf(status))
                .toList();

        // Arrange
        for(ProductSale productSale: productSaleEntityList)
            saveReferencesAndSet(productSale);

        // Save All
        productSaleRepository.saveAll(productSaleEntityList);

        // Find All
        List<ProductSale> productSalesFromDB =
                productSaleRepository.findBySaleStatus(SaleStatus.valueOf(status));

        // Assert
        assertNotNull(productSalesFromDB);
        assertFalse(productSalesFromDB.isEmpty());
        assertFalse(
                productSalesFromDB.stream()
                        .anyMatch(sale -> sale.getSaleStatus() != SaleStatus.valueOf(status))
        );
        assertEquals(salesFilter.size(), productSalesFromDB.size());


    }




    /* --------------------------------- PRIVATE METHODS --------------------------------- */
    private void saveReferencesAndSet(ProductSale productSaleEntity) {
        arrangeProduct(productSaleEntity);
        Customer customer = customerRepository.save(CustomerCreator.entity());
        productSaleEntity.getReceipt().setCustomer(customer);
        Receipt findReceipt = receiptRepository.findByNumber(staticReceiptNumber);
        if(findReceipt == null) {
            if(productSaleEntity.getReceipt().getProductSaleList() == null)
                productSaleEntity.getReceipt().setProductSaleList(
                        new ArrayList<>(List.of(productSaleEntity))
                );
            else
                productSaleEntity.getReceipt().getProductSaleList().add(productSaleEntity);

            Receipt receiptSaved = receiptRepository.save(productSaleEntity.getReceipt());
            productSaleEntity.setReceipt(receiptSaved);
        }else{
            productSaleEntity.setReceipt(findReceipt);
        }

    }

    private void arrangeProduct(ProductSale productSaleEntity){
        var productEntity = productSaleEntity.getProduct();

        ProductCategory productCategory = productCategoryRepository.save(productEntity.getCategory());
        Company productCompany = companyRepository.save(productEntity.getCompany());
        List<ProductSalePrice> productSalePrice =
                productSalePricesRepository.saveAll(productEntity.getProductSalePrices());
        List<ProductBarcode> productBarcodeList =
                productBarcodeRepository.saveAll(productEntity.getProductBarcodeList());
        Product productSaved = productRepository.save(productEntity);

        productEntity.setProductBarcodeList(productBarcodeList);
        productEntity.setProductSalePrices(productSalePrice);
        productEntity.setCategory(productCategory);
        productEntity.setCompany(productCompany);

        productSaleEntity.setProduct(productSaved);

    }




}
