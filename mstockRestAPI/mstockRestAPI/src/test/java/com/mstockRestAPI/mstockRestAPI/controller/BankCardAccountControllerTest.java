package com.mstockRestAPI.mstockRestAPI.controller;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mstockRestAPI.mstockRestAPI.dto.BankCardAccountDto;
import com.mstockRestAPI.mstockRestAPI.entity.BankCardAccount;
import com.mstockRestAPI.mstockRestAPI.service.BankCardAccountService;
import com.mstockRestAPI.mstockRestAPI.tools.creator.BankCardAccountCreator;
import org.aspectj.lang.annotation.Before;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(
        controllers =  BankCardAccountController.class,
        excludeAutoConfiguration = {SecurityAutoConfiguration.class}
)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class BankCardAccountControllerTest {

    @MockBean
    private BankCardAccountService bankCardAccountService;

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;
    private final String endPoint = "/api/v1/bankCardAccount";

    private BankCardAccountDto dto;

    @BeforeEach
    public void beforeEach(){
        dto = BankCardAccountCreator.dto();
    }
    @Test
    @DisplayName("Add")
    @Order(1)
    public void givenEntity_thenAdd_returnEntity() throws Exception {
        when(bankCardAccountService.add(any()))
                .thenReturn(dto);

        ResultActions result = mockMvc.perform(
                        post(endPoint)
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(dto))
                ).andExpect(status().isCreated())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.accountNumber").value(dto.getAccountNumber()))
                .andExpect(jsonPath("$.cardHolderName").value(dto.getCardHolderName()));

        verify(bankCardAccountService, times(1)).add(any());
    }

    @Test
    @DisplayName("Make deActive")
    @Order(2)
    public void givenAccountNumber_whenFind_thenMakeDeActive() throws Exception {

        when(bankCardAccountService.makeDeActive(any()))
                .thenReturn(true);

        ResultActions result = mockMvc.perform(
                        put(endPoint + "/bankCardAccountNumber/" + dto.getAccountNumber())
                ).andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.message").value("true"));

        verify(bankCardAccountService, times(1)).makeDeActive(any());
    }

}
