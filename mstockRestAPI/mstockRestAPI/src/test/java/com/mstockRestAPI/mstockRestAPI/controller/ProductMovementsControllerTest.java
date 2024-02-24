package com.mstockRestAPI.mstockRestAPI.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mstockRestAPI.mstockRestAPI.dto.PaymentCustomerCreditDto;
import com.mstockRestAPI.mstockRestAPI.dto.PaymentExtraInfoDto;
import com.mstockRestAPI.mstockRestAPI.dto.ProductMovementListDto;
import com.mstockRestAPI.mstockRestAPI.dto.SaleReceiptDto;
import com.mstockRestAPI.mstockRestAPI.enums.PaymentType;
import com.mstockRestAPI.mstockRestAPI.service.PaymentCustomerCreditService;
import com.mstockRestAPI.mstockRestAPI.service.ProductMovementsService;
import com.mstockRestAPI.mstockRestAPI.tools.creator.*;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.ResultMatcher;

import java.math.BigDecimal;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@WebMvcTest(
        controllers =  ProductMovementsController.class,
        excludeAutoConfiguration = {SecurityAutoConfiguration.class}
)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class ProductMovementsControllerTest {
    @MockBean
    private ProductMovementsService productMovementsService;
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;

    private ProductMovementListDto productMovementListDto;
    private final String endPoint = "/api/v1/productMovements";

    @BeforeEach
    public void beforeEach(){

    }

    @Test
    @DisplayName("Add sale or return product movement")
    @Order(1)
    public void givenEntity_thenAdd_returnEntity() throws Exception {
        SaleReceiptDto saleReceiptDto = SaleReceiptCreator.dto();
        ProductMovementListDto send = ProductMovementListDto.builder()
                .paymentExtraInfo(PaymentExtraInfoCreator.createDto(
                    BigDecimal.TEN,
                    BigDecimal.ONE,
                    BigDecimal.ONE,
                    PaymentType.CASH
                ))
                .paymentType(PaymentType.CASH)
                .customer(CustomerCreator.dto())
                .receiptComment("Test comment")
                .productsList(ProductMovementCreator.dtoList())
                .build();

        when(productMovementsService.addAllSalesOrReturnsProducts(any()))
                .thenReturn(saleReceiptDto);

        ResultActions result = mockMvc.perform(
                post(endPoint+"/saleOrReturn")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(send))
        ).andExpect(status().isCreated())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$").isNotEmpty());


        verify(productMovementsService, times(1))
                .addAllSalesOrReturnsProducts(any());
    }








}