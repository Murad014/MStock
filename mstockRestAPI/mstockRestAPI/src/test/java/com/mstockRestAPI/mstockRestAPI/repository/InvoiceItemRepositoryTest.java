package com.mstockRestAPI.mstockRestAPI.repository;

import com.mstockRestAPI.mstockRestAPI.dto.InvoiceItemDto;
import com.mstockRestAPI.mstockRestAPI.entity.Invoice;
import com.mstockRestAPI.mstockRestAPI.entity.InvoiceItem;
import com.mstockRestAPI.mstockRestAPI.entity.Product;
import com.mstockRestAPI.mstockRestAPI.entity.ProductCategory;
import com.mstockRestAPI.mstockRestAPI.tools.creator.InvoiceCreator;
import com.mstockRestAPI.mstockRestAPI.tools.creator.InvoiceItemCreator;
import com.mstockRestAPI.mstockRestAPI.tools.creator.ProductCreator;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;
import java.util.Objects;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@TestPropertySource(locations = "/application-test.properties")
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class InvoiceItemRepositoryTest {

    @Autowired
    private InvoiceItemRepository invoiceItemRepository;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private ProductCategoryRepository productCategoryRepository;

    @Autowired
    private InvoiceRepository invoiceRepository;

    @Autowired
    private CompanyRepository companyRepository;

    private InvoiceItem entity;
    private List<InvoiceItem> entityList;

    @BeforeEach
    public void beforeEach(){
        entity = InvoiceItemCreator.entity();
        entityList = InvoiceItemCreator.entityList();
    }

    @Test
    @DisplayName("Add Invoice Item")
    @Transactional
    @Order(1)
    public void givenEntity_thenAdd_thenReturnEntity() {
        // Add
        BigDecimal totalPrice = entity.getQuantity().multiply(entity.getPrice());
        BigDecimal calcTotal = totalPrice.setScale(2, RoundingMode.HALF_UP);
        InvoiceItem save = invoiceItemRepository.save(entity);

        // Assert
        assertNotNull(save);
        assertEquals(save.getTotalPrice(), calcTotal);
        assertThat(save)
                .usingRecursiveComparison()
                .isEqualTo(entity);
    }

    @Test
    @DisplayName("Update Invoice Item")
    @Transactional
    @Order(2)
    public void givenIdAndEntity_thenUpdate_thenReturnEntity() {
        // Add
        InvoiceItem save = invoiceItemRepository.save(entity);

        // Find By id
        InvoiceItem findToUpdate = invoiceItemRepository.findById(save.getId()).orElse(null);

        assertNotNull(findToUpdate);

        Product newProduct = ProductCreator.entity();
        findToUpdate.setProduct(newProduct);

        // Update
        InvoiceItem saveInvoiceItem = invoiceItemRepository.save(findToUpdate);

        // Assert
        assertNotNull(saveInvoiceItem);
        assertFalse(saveInvoiceItem.getProduct().getProductName().isEmpty());
        assertEquals(newProduct.getProductName(), saveInvoiceItem.getProduct().getProductName());
    }

    @Test
    @DisplayName("Fetch All")
    @Transactional
    @Order(3)
    public void fetchAll() {
        saveAll();

        // Add
        List<InvoiceItem> saveAll = invoiceItemRepository.saveAll(entityList);

        // Fetch All
        List<InvoiceItem> fetchAll = invoiceItemRepository.findAll();

        // Assert
        assertNotNull(fetchAll);
        assertFalse(fetchAll.isEmpty());
        assertEquals(saveAll.size(), fetchAll.size());
    }

    @Test
    @DisplayName("Fetch by Id")
    @Transactional
    @Order(4)
    public void fetchById(){
        // Save
        InvoiceItem save = invoiceItemRepository.save(entity);

        // Find by id
        InvoiceItem findById = invoiceItemRepository.findById(save.getId()).orElse(null);

        // Assert
        assertNotNull(findById);
        assertThat(findById)
                .usingRecursiveComparison()
                .isEqualTo(save);

    }

    @Test
    @DisplayName("Fetch By Invoice Code")
    @Transactional
    @Order(5)
    public void givenInvoiceCode_whenFind_thenReturnInvoiceItemsList(){

        // Save All
        saveAll();
        List<InvoiceItem> invoiceItemList = invoiceItemRepository.saveAll(entityList);


        // Find
        String invoiceCode = invoiceItemList.get(0).getInvoice().getInvoiceCode();
        List<InvoiceItem> pivotSearch = invoiceItemList.stream()
                .filter(invoiceItem ->
                        Objects.equals(invoiceItem.getInvoice().getInvoiceCode(), invoiceCode)).toList();

        List<InvoiceItem> findInvoiceItemsByInvoiceCode =
                invoiceItemRepository.findByInvoice_invoiceCode(invoiceCode);


        // Assert
        assertNotNull(findInvoiceItemsByInvoiceCode);
        assertFalse(findInvoiceItemsByInvoiceCode.isEmpty());
        assertEquals(pivotSearch.size(), findInvoiceItemsByInvoiceCode.size());

    }


    private void saveAll(){
        Invoice invoice = InvoiceCreator.entity();
        invoiceRepository.save(invoice);

        for(InvoiceItem invoiceItem: entityList){
            companyRepository.save(invoiceItem.getProduct().getCompany());
            productCategoryRepository.save(invoiceItem.getProduct().getCategory());
            productRepository.save(invoiceItem.getProduct());
            invoiceItem.setInvoice(invoice);

        }
    }




}
