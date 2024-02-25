package com.mstockRestAPI.mstockRestAPI.repository;

import com.mstockRestAPI.mstockRestAPI.dto.InvoiceDto;
import com.mstockRestAPI.mstockRestAPI.entity.Invoice;
import com.mstockRestAPI.mstockRestAPI.entity.SupplierOfProduct;
import com.mstockRestAPI.mstockRestAPI.tools.creator.InvoiceCreator;
import com.mstockRestAPI.mstockRestAPI.tools.creator.SupplierOFProductCreator;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.InstanceOfAssertFactories.stream;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@TestPropertySource(locations = "/application-test.properties")
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class InvoiceRepositoryTest {
    @Autowired
    private InvoiceRepository invoiceRepository;

    private Invoice entity;
    private List<Invoice> entityList;

    @BeforeEach
    public void beforeEach(){
        entity = InvoiceCreator.entity();
        entityList = InvoiceCreator.entityList();
    }

    @Test
    @DisplayName("Add")
    @Transactional
    @Order(1)
    public void givenEntity_whenAdd_thenReturnEntity(){
        // Save
        Invoice add = invoiceRepository.save(entity);

        // Assert
        assertNotNull(add);
        assertThat(add)
                .usingRecursiveComparison()
                .isEqualTo(entity);
    }

    @Test
    @DisplayName("Update")
    @Transactional
    @Order(2)
    public void givenEntity_whenUpdate_thenReturnEntity(){
        // Save
        Invoice add = invoiceRepository.save(entity);

        // Find
        Invoice fromDB = invoiceRepository.findById(add.getId()).orElse(null);

        assertNotNull(fromDB);
        // Setter
        SupplierOfProduct supplier = SupplierOFProductCreator.entity();
        fromDB.setSupplier(supplier);
        Invoice update = invoiceRepository.save(fromDB);

        // Assert
        assertNotNull(add);
        assertThat(update)
                .usingRecursiveComparison()
                .isEqualTo(fromDB);
    }

    @Test
    @DisplayName("Find All")
    @Transactional
    @Order(3)
    public void returnAllInvoices(){
        // Save All
        List<Invoice> invoiceList = invoiceRepository.saveAll(entityList);

        // Fetch All
        List<Invoice> saveAll = invoiceRepository.findAll();

        // Assert
        assertNotNull(saveAll);
        assertFalse(saveAll.isEmpty());
        assertEquals(invoiceList.size(), saveAll.size());
    }

    @DisplayName("Fetch where activities")
    @ParameterizedTest
    @ValueSource(bytes = {0, 1})
    @Transactional
    @Order(4)
    public void givenActiveType_whenFind_returnList(byte isActive){
        List<Invoice> allByActives = entityList.stream()
                .filter(invoice -> invoice.getIsActive() == isActive)
                .toList();

        // Save All
        List<Invoice> saveAll = invoiceRepository.saveAll(entityList);

        // Fetch Where is Active
        List<Invoice> fetchByActive = invoiceRepository.findByIsActive(isActive);

        // Assert
        assertNotNull(fetchByActive);
        assertFalse(fetchByActive.isEmpty());
        assertEquals(allByActives.size(), fetchByActive.size());
        assertFalse(fetchByActive.stream()
                .anyMatch(invoice -> invoice.getIsActive() != isActive));
    }

    @Test
    @DisplayName("Fetch by Id")
    @Order(5)
    @Transactional
    public void givenId_whenFind_thenReturnEntity(){
        // Save
        Invoice save = invoiceRepository.save(entity);

        // Find by id
        Invoice findById = invoiceRepository.findById(save.getId()).orElse(null);

        // Assert
        assertNotNull(findById);
        assertThat(findById)
                .usingRecursiveComparison()
                .withStrictTypeChecking()
                .isEqualTo(save);
    }

    @Test
    @DisplayName("Fetch by Invoice Code")
    @Order(6)
    @Transactional
    public void givenInvoiceCode_whenFind_thenReturnEntity(){
        // Save
        Invoice save = invoiceRepository.save(entity);

        // Find by id
        Invoice findById = invoiceRepository.findByInvoiceCode(save.getInvoiceCode());

        // Assert
        assertNotNull(findById);
        assertThat(findById)
                .usingRecursiveComparison()
                .withStrictTypeChecking()
                .isEqualTo(save);
    }


}
