package com.mstockRestAPI.mstockRestAPI.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mstockRestAPI.mstockRestAPI.dto.CreditOfCustomersDto;
import com.mstockRestAPI.mstockRestAPI.dto.CustomerDto;
import com.mstockRestAPI.mstockRestAPI.entity.CreditOfCustomers;
import com.mstockRestAPI.mstockRestAPI.entity.Customer;
import com.mstockRestAPI.mstockRestAPI.service.CreditsOfCustomersService;
import com.mstockRestAPI.mstockRestAPI.tools.creator.CreditOfCustomersCreator;
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

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@WebMvcTest(
        controllers =  CustomerCreditsController.class,
        excludeAutoConfiguration = {SecurityAutoConfiguration.class}
)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class CustomerCreditsControllerTest {
    @MockBean
    private CreditsOfCustomersService creditsOfCustomersService;

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;

    private CreditOfCustomersDto creditOfCustomersDto;
    private CreditOfCustomers creditOfCustomers;

    private List<CreditOfCustomersDto> creditOfCustomersDtoList;
    private List<CreditOfCustomers> creditOfCustomersList;
    private String endPoint = "/api/v1/credits";

    @BeforeEach
    public void beforeEach(){
        this.creditOfCustomers = CreditOfCustomersCreator.entity();
        this.creditOfCustomersList = CreditOfCustomersCreator.entityList();
        this.creditOfCustomersDto = CreditOfCustomersCreator.dto();
        this.creditOfCustomersDtoList = CreditOfCustomersCreator.dtoList();
    }

    @Test
    @DisplayName("Add by customer card id number")
    @Order(1)
    public void givenEntityAndCustomerCardIdNumber_whenAdd_thenReturnEntity() throws Exception {
        String customerIdNumber = "12ASD221";
        creditOfCustomersDto.getCustomer().setIdCardNumber(customerIdNumber);
        when(creditsOfCustomersService.addByCustomerIdCardNumber(any(CreditOfCustomersDto.class), anyString()))
                .thenReturn(creditOfCustomersDto);

        ResultActions result = mockMvc.perform(
                post(endPoint+"?customerIdCardNumber="+customerIdNumber)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(creditOfCustomersDto))
        );

        assertions(result, status().isCreated());
        verify(creditsOfCustomersService, times(1))
                .addByCustomerIdCardNumber(any(CreditOfCustomersDto.class), anyString());
    }

    @Test
    @DisplayName("Fetch by Id")
    @Order(2)
    public void givenCreditId_whenFind_thenReturnDto() throws Exception {
        Long creditId = 12L;
        when(creditsOfCustomersService.fetchById(creditId))
                .thenReturn(creditOfCustomersDto);

        ResultActions result = mockMvc.perform(
                get(endPoint + "/" + creditId)
        );

        assertions(result, status().isOk());
        verify(creditsOfCustomersService, times(1))
                .fetchById(anyLong());
    }


    private void assertions(ResultActions result, ResultMatcher resultMatcher) throws Exception {

        String id = "$.id";
        String givenAmount = "$.givenAmount";
        String totalAmount = "$.totalAmount";
        String percentageFee = "$.percentageFee";
        String commissionAmount = "$.commissionAmount";
        String monthlyInstallment = "$.monthlyInstallment";
        String creditMonths = "$.creditMonths";
        String finePercentage = "$.finePercentage";
        String closed = "$.closed";

        result.andExpect(resultMatcher)
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath(id).value(creditOfCustomersDto.getId()))
                .andExpect(jsonPath(givenAmount).value(creditOfCustomersDto.getGivenAmount()))
                .andExpect(jsonPath(totalAmount).value(creditOfCustomersDto.getTotalAmount()))
                .andExpect(jsonPath(percentageFee).value(creditOfCustomersDto.getPercentageFee()))
                .andExpect(jsonPath(commissionAmount).value(String.valueOf(creditOfCustomersDto.getCommissionAmount())))
                .andExpect(jsonPath(monthlyInstallment).value(String.valueOf(creditOfCustomersDto.getMonthlyInstallment())))
                .andExpect(jsonPath(creditMonths).value(String.valueOf(creditOfCustomersDto.getCreditMonths())))
                .andExpect(jsonPath(finePercentage).value(String.valueOf(creditOfCustomersDto.getFinePercentage())))
                .andExpect(jsonPath(closed).value(String.valueOf(creditOfCustomersDto.getClosed())));

    }

}
