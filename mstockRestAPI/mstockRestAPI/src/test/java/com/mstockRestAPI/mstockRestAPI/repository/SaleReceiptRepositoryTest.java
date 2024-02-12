package com.mstockRestAPI.mstockRestAPI.repository;

import com.mstockRestAPI.mstockRestAPI.entity.*;
import com.mstockRestAPI.mstockRestAPI.tools.creator.ProductMovementCreator;
import com.mstockRestAPI.mstockRestAPI.tools.creator.SaleReceiptCreator;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@TestPropertySource(locations = "/application-test.properties",
        properties = "server.port=8081")
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class SaleReceiptRepositoryTest {

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


    @BeforeEach
    public void beforeEach(){
        productSaleRepository.deleteAll();
        saleReceiptRepository.deleteAll();
        productRepository.deleteAll();
        productBarcodeRepository.deleteAll();
        customerRepository.deleteAll();
    }

    @Test
    @DisplayName("Add receipt")
    @Order(1)
    @Transactional
    public void givenEntityReceipt_thenAdd_thenReturnReceiptEntity(){
        SaleReceipt receipt = saveReferencesAndSet();
        SaleReceipt savedReceipt = saleReceiptRepository.save(receipt);

        assertEquals(receipt.getPaymentExtraInfo().getBankAccountNumber().getAccountNumber(),
                savedReceipt.getPaymentExtraInfo().getBankAccountNumber().getAccountNumber());
        // Assert
        assertThat(savedReceipt)
                .usingRecursiveComparison()
                .isEqualTo(receipt);


    }

    @Test
    @DisplayName("Get receipt by number")
    @Order(2)
    @Transactional
    public void givenReceiptNumber_whenFind_thenReturnEntity(){
        SaleReceipt receipt = saveReferencesAndSet();
        SaleReceipt savedReceipt = saleReceiptRepository.save(receipt);

        SaleReceipt getFromDB = saleReceiptRepository.findByNumber(staticReceiptNumber);

        // Assert
        assertEquals(receipt.getPaymentExtraInfo().getBankAccountNumber().getAccountNumber(),
                getFromDB.getPaymentExtraInfo().getBankAccountNumber().getAccountNumber());

        assertNotNull(getFromDB);
        assertFalse(getFromDB.getProductSaleList().isEmpty());
        assertThat(getFromDB)
                .usingRecursiveComparison()
                .isEqualTo(savedReceipt);
    }


    /* --------------------------------- PRIVATE METHODS --------------------------------- */
    private SaleReceipt saveReferencesAndSet() {
        SaleReceipt receipt = SaleReceiptCreator.entity();
        Customer customer = customerRepository.save(receipt.getCustomer());
        receipt.setCustomer(customer);
        List<ProductMovements> productSales = ProductMovementCreator.entityList();

        for(ProductMovements productSale: productSales) {
            arrangeProduct(productSale);
        }

        receipt.setProductSaleList(productSales);

        return receipt;
    }

    private void arrangeProduct(ProductMovements productSaleEntity){
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
    }




}
