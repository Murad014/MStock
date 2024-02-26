package com.mstockRestAPI.mstockRestAPI.repository;


import com.mstockRestAPI.mstockRestAPI.dto.PaymentInvoiceDto;
import com.mstockRestAPI.mstockRestAPI.entity.PaymentInvoice;
import com.mstockRestAPI.mstockRestAPI.tools.creator.PaymentInvoiceCreator;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
@TestPropertySource(locations = "/application-test.properties",
        properties = "server.port=8081")
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class PaymentInvoiceRepositoryTest {

    @Autowired
    private PaymentInvoiceRepository paymentInvoiceRepository;

    private PaymentInvoiceDto dto;
    private PaymentInvoice entity;

    @BeforeEach
    public void beforeEach(){
        entity = PaymentInvoiceCreator.entity();
        dto = PaymentInvoiceCreator.dto();
    }

    @Test
    @DisplayName("Add payment")
    @Transactional
    @Order(1)
    public void givenEntity_whenAdd_thenReturnEntity(){
        // Save
        PaymentInvoice saveEntity = paymentInvoiceRepository.save(entity);

        // Assert
        assertNotNull(saveEntity);
        assertThat(saveEntity)
                .usingRecursiveComparison()
                .isEqualTo(entity);
    }




}
