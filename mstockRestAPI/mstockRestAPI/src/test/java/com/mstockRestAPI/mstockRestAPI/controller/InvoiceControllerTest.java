package com.mstockRestAPI.mstockRestAPI.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mstockRestAPI.mstockRestAPI.dto.CustomerDto;
import com.mstockRestAPI.mstockRestAPI.dto.InvoiceDto;
import com.mstockRestAPI.mstockRestAPI.service.CustomerService;
import com.mstockRestAPI.mstockRestAPI.service.InvoiceService;
import com.mstockRestAPI.mstockRestAPI.tools.creator.InvoiceCreator;
import org.aspectj.lang.annotation.Before;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.ResultMatcher;

import java.util.List;

import static com.mstockRestAPI.mstockRestAPI.tools.utils.Util.convertResultToList;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@WebMvcTest(
        controllers =  InvoiceController.class,
        excludeAutoConfiguration = {SecurityAutoConfiguration.class}
)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class InvoiceControllerTest {
    @MockBean
    private InvoiceService invoiceService;
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;

    private InvoiceDto dto;
    private List<InvoiceDto> dtoList;
    private String endPoint = "/api/v1/invoice";

    @BeforeEach
    public void beforeEach(){
        this.dto = InvoiceCreator.dto();
        this.dtoList = InvoiceCreator.dtoList();
    }

    @Test
    @DisplayName("Add")
    @Order(1)
    public void givenDto_whenAdd_thenReturnDto() throws Exception{
        Long customerId = 1L;
        dto.setId(customerId);
        when(invoiceService.add(any(InvoiceDto.class)))
                .thenReturn(dto);

        ResultActions result = mockMvc.perform(
                post(endPoint)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto))
        );

        assertions(result, status().isCreated());
        verify(invoiceService, times(1))
                .add(any(InvoiceDto.class));

    }

    @Test
    @DisplayName("Update")
    @Order(2)
    public void givenDto_whenUpdate_thenReturnDto() throws Exception{
        Long invoiceId = 12L;
        dto.setId(invoiceId);
        when(invoiceService.update(any(), any(InvoiceDto.class)))
                .thenReturn(dto);

        ResultActions result = mockMvc.perform(
                put(endPoint+"/"+invoiceId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto))
        );

        assertions(result, status().isOk());
        verify(invoiceService, times(1))
                .update(any(), any(InvoiceDto.class));

    }

    @Test
    @DisplayName("Fetch All")
    @Order(2)
    public void fetchAll() throws Exception{
        when(invoiceService.fetchAll())
                .thenReturn(dtoList);

        ResultActions result = mockMvc.perform(
                get(endPoint));

        // Convert
        List<CustomerDto> responseBodyList = convertResultToList(result);

        // Assert
        assertNotNull(result);
        assertFalse(responseBodyList.isEmpty());
        assertEquals(dtoList.size(), responseBodyList.size());

        verify(invoiceService, times(1))
                .fetchAll();

    }

    private void assertions(ResultActions result, ResultMatcher resultMatcher) throws Exception {

        String id = "$.id";
        String customerName = "$.invoiceCode";
        String surname = "$.initialTotalAmount";

        result.andExpect(resultMatcher)
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath(id).value(dto.getId()))
                .andExpect(jsonPath(customerName).value(dto.getInvoiceCode()))
                .andExpect(jsonPath(surname).value(dto.getInitialTotalAmount()));
    }



}
