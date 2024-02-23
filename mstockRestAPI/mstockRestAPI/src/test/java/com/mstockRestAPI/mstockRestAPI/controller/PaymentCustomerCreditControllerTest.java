package com.mstockRestAPI.mstockRestAPI.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mstockRestAPI.mstockRestAPI.dto.CreditOfCustomersDto;
import com.mstockRestAPI.mstockRestAPI.dto.PaymentCustomerCreditDto;
import com.mstockRestAPI.mstockRestAPI.entity.CreditOfCustomers;
import com.mstockRestAPI.mstockRestAPI.entity.PaymentCustomerCredit;
import com.mstockRestAPI.mstockRestAPI.service.CreditsOfCustomersService;
import com.mstockRestAPI.mstockRestAPI.service.PaymentCustomerCreditService;
import com.mstockRestAPI.mstockRestAPI.tools.creator.CreditOfCustomersCreator;
import com.mstockRestAPI.mstockRestAPI.tools.creator.PaymentCustomerCreditCreator;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.ResultMatcher;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@WebMvcTest(
        controllers =  PaymentCustomerCreditController.class,
        excludeAutoConfiguration = {SecurityAutoConfiguration.class}
)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class PaymentCustomerCreditControllerTest {
    @MockBean
    private PaymentCustomerCreditService paymentCustomerCreditService;
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;

    private PaymentCustomerCreditDto paymentCustomerCreditDto;
    private final String endPoint = "/api/v1/paymentCredit";

    @BeforeEach
    public void beforeEach(){
        this.paymentCustomerCreditDto = PaymentCustomerCreditCreator.dto();
    }

    @Test
    @DisplayName("Add by customer card id number")
    @Order(1)
    public void givenEntityAndCustomerCardIdNumber_whenAdd_thenReturnEntity() throws Exception {
        Long creditId = 12L;

        when(paymentCustomerCreditService.add(any(), any()))
                .thenReturn(paymentCustomerCreditDto);

        ResultActions result = mockMvc.perform(
                post(endPoint+"/credit/"+creditId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(paymentCustomerCreditDto))
        );

        assertions(result, status().isCreated());
        verify(paymentCustomerCreditService, times(1))
                .add(eq(creditId), any());
    }

    private void assertions(ResultActions result, ResultMatcher resultMatcher) throws Exception {

        String id = "$.id";
        String givenAmount = "$.paymentAmount";

        result.andExpect(resultMatcher)
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath(id).value(paymentCustomerCreditDto.getId()))
                .andExpect(jsonPath(givenAmount).value(paymentCustomerCreditDto.getPaymentAmount()));

    }


}
