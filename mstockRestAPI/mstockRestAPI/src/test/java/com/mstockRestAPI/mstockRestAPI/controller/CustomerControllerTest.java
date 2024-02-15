package com.mstockRestAPI.mstockRestAPI.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mstockRestAPI.mstockRestAPI.dto.CustomerDto;
import com.mstockRestAPI.mstockRestAPI.dto.ProductDto;
import com.mstockRestAPI.mstockRestAPI.entity.Customer;
import com.mstockRestAPI.mstockRestAPI.service.CustomerService;
import com.mstockRestAPI.mstockRestAPI.tools.creator.CustomerCreator;
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

@WebMvcTest(
        controllers =  CustomerController.class,
        excludeAutoConfiguration = {SecurityAutoConfiguration.class}
)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class CustomerControllerTest {
    @MockBean
    private CustomerService customerService;
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;

    private CustomerDto customerDto;
    private Customer customer;
    private List<CustomerDto> customerDtoList;
    private List<Customer> customerList;
    private String endPoint = "/api/v1/customer";

    @BeforeEach
    public void beforeEach(){
        this.customer = CustomerCreator.entity();
        this.customerList = CustomerCreator.entityList();
        this.customerDto = CustomerCreator.dto();
        this.customerDtoList = CustomerCreator.dtoList();
    }

    @Test
    @DisplayName("Get All")
    @Order(1)
    public void givenEntity_whenAdd_thenReturnEntity() throws Exception{
        for(int i = 0; i < customerDtoList.size(); i++)
            customerDtoList.get(i)
                    .setId((long)i+1);

        when(customerService.getAll())
                .thenReturn(customerDtoList);

        ResultActions result = mockMvc.perform(get(endPoint));

        // Convert
        List<CustomerDto> responseBodyList = convertResultToList(result);

        // Assert
        assertNotNull(result);
        assertFalse(responseBodyList.isEmpty());
        assertEquals(customerDtoList.size(), responseBodyList.size());

        // Verify
        verify(customerService, times(1))
                .getAll();
    }

    @Test
    @DisplayName("Update")
    @Order(2)
    public void givenDto_whenUpdate_thenReturnDto() throws Exception{
        Long customerId = 1L;
        customerDto.setId(customerId);
        when(customerService.update(anyLong(), any(CustomerDto.class)))
                .thenReturn(customerDto);

        ResultActions result = mockMvc.perform(
                put(endPoint + "/" + 1L)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(customerDto))
        );

        assertions(result, status().isOk());
        verify(customerService, times(1))
                .update(anyLong(), any(CustomerDto.class));

    }

    @Test
    @DisplayName("Add")
    @Order(3)
    public void givenDto_whenAdd_thenReturnDto() throws Exception{
        Long customerId = 1L;
        customerDto.setId(customerId);
        when(customerService.add(any(CustomerDto.class)))
                .thenReturn(customerDto);

        ResultActions result = mockMvc.perform(
                post(endPoint)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(customerDto))
        );

        assertions(result, status().isCreated());
        verify(customerService, times(1))
                .add(any(CustomerDto.class));

    }

    @Test
    @DisplayName("Get By Id")
    @Order(4)
    public void givenId_whenGive_thenReturnDto() throws Exception{
        when(customerService.getById(anyLong())).thenReturn(customerDto);

        ResultActions result = mockMvc.perform(
                get(endPoint + "/" + 1L)
        );

        assertions(result, status().isOk());
        verify(customerService, times(1))
                .getById(anyLong());
    }




    private void assertions(ResultActions result, ResultMatcher resultMatcher) throws Exception {

        String id = "$.id";
        String customerName = "$.name";
        String surname = "$.surname";
        String email = "$.email";
        String isActive = "$.isActive";

        result.andExpect(resultMatcher)
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath(id).value(customerDto.getId()))
                .andExpect(jsonPath(customerName).value(customerDto.getName()))
                .andExpect(jsonPath(surname).value(customerDto.getSurname()))
                .andExpect(jsonPath(email).value(customerDto.getEmail()))
                .andExpect(jsonPath(isActive).value(String.valueOf(customerDto.getIsActive())));

    }


}
