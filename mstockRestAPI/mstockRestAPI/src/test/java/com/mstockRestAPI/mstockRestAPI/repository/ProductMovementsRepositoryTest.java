package com.mstockRestAPI.mstockRestAPI.repository;

import com.mstockRestAPI.mstockRestAPI.entity.*;
import com.mstockRestAPI.mstockRestAPI.enums.SaleStatus;
import com.mstockRestAPI.mstockRestAPI.tools.creator.CustomerCreator;
import com.mstockRestAPI.mstockRestAPI.tools.creator.ProductMovementCreator;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@TestPropertySource(locations = "/application-test.properties",
properties = "server.port=8081")
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class ProductMovementsRepositoryTest {

    @Autowired
    private ProductMovementsRepository productSaleRepository;
    @Autowired
    private SaleReceiptRepository saleReceiptRepository;
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
    private PaymentExtraInfoRepository paymentExtraInfoRepository;

    @Autowired
    private CustomerRepository customerRepository;

    private static final String staticReceiptNumber = "14123123";

    private ProductMovements productMovementEntity;
    private List<ProductMovements> productMovementEntityList;

    @BeforeEach
    public void beforeEach(){
        productMovementEntity = ProductMovementCreator.entity();
        productMovementEntityList = ProductMovementCreator.entityList();

        productSaleRepository.deleteAll();
        productRepository.deleteAll();
        productBarcodeRepository.deleteAll();
        saleReceiptRepository.deleteAll();
        paymentExtraInfoRepository.deleteAll();
    }

    @Test
    @DisplayName("Add")
    @Order(1)
    @Transactional
    public void givenEntity_thenSave_thenReturnEntity(){
        // Arrange
        saveReferencesAndSet(productMovementEntity);

        // Act
        ProductMovements productSale = productSaleRepository.save(productMovementEntity);

        // Assert
        assertThat(productSale)
                .usingRecursiveComparison()
                .isEqualTo(productMovementEntity);
    }

    @Test
    @DisplayName("Update")
    @Order(2)
    @Transactional
    public void givenEntity_whenUpdate_thenReturnEntity(){
        // Arrange
        saveReferencesAndSet(productMovementEntity);

        ProductMovements productSale = productSaleRepository.save(productMovementEntity);
        productSale.getProduct().setProductName("Update test ProductName");
        productSale.setComment("Comment Update Test");


        ProductMovements productSaleFromDB = productSaleRepository.findById(productSale.getId()).orElse(null);
        assertNotNull(productSaleFromDB);
        ProductMovements productSaleUpdate = productSaleRepository.save(productSaleFromDB);

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
        for(ProductMovements productSale: productMovementEntityList)
            saveReferencesAndSet(productSale);

        productSaleRepository.saveAll(productMovementEntityList);

        // Fetch All
        List<ProductMovements> list = productSaleRepository.findAll();

        // Assert
        assertNotNull(list);
        assertFalse(list.isEmpty());
        assertEquals(productMovementEntityList.size(), list.size());
    }

    @ParameterizedTest
    @ValueSource(strings = {"SOLD", "RETURNED"})
    @DisplayName("Get by sale status")
    @Order(4)
    @Transactional
    public void getAllBySaleStatusWithValidStatus(String status){
        // Filter
        List<ProductMovements> salesFilter = productMovementEntityList
                .stream()
                .filter(sale -> sale.getSaleStatus() == SaleStatus.valueOf(status))
                .toList();

        // Arrange
        for(ProductMovements productMovements: productMovementEntityList)
            saveReferencesAndSet(productMovements);

        // Save All
        productSaleRepository.saveAll(productMovementEntityList);

        // Find All
        List<ProductMovements> productSalesFromDB =
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
    private void saveReferencesAndSet(ProductMovements productMovementEntity) {
        arrangeProduct(productMovementEntity);
        Customer customer = customerRepository.save(CustomerCreator.entity());
        productMovementEntity.getReceipt().setCustomer(customer);
        SaleReceipt findReceipt = saleReceiptRepository.findByNumber(staticReceiptNumber);

        if(findReceipt == null) {
            if(productMovementEntity.getReceipt().getProductSaleList() == null)
                productMovementEntity.getReceipt().setProductSaleList(
                        new ArrayList<>(List.of(productMovementEntity))
                );
            else
                productMovementEntity.getReceipt().getProductSaleList().add(productMovementEntity);

            paymentExtraInfoRepository.save(productMovementEntity.getReceipt().getPaymentExtraInfo());
            SaleReceipt receiptSaved = saleReceiptRepository.save(productMovementEntity.getReceipt());
            productMovementEntity.setReceipt(receiptSaved);
        }else{
            productMovementEntity.setReceipt(findReceipt);
        }

    }

    private void arrangeProduct(ProductMovements productMovementEntity){
        var productEntity = productMovementEntity.getProduct();

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

        productMovementEntity.setProduct(productSaved);

    }




}
