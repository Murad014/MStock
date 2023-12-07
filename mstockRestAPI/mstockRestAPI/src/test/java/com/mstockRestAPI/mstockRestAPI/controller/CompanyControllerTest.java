package com.mstockRestAPI.mstockRestAPI.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mstockRestAPI.mstockRestAPI.dto.CompanyDto;
import com.mstockRestAPI.mstockRestAPI.entity.Company;
import com.mstockRestAPI.mstockRestAPI.service.CompanyService;
import com.mstockRestAPI.mstockRestAPI.tools.creator.CompanyCreator;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.*;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.web.servlet.*;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;


import java.util.List;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import static org.mockito.ArgumentMatchers.any;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
//@WebMvcTest(controllers = CompanyControllerTest.class,
//excludeAutoConfiguration = {SecurityAutoConfiguration.class})
@WebMvcTest(controllers = CompanyController.class,
        excludeAutoConfiguration = {SecurityAutoConfiguration.class})
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class CompanyControllerTest {

    @MockBean
    private CompanyService companyService;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    // DTO
    private final CompanyDto companyDto = CompanyCreator.createCompanyDto();
    private final List<CompanyDto> companyDtoList = CompanyCreator.createListOfCompanyDto();

    // Keys
    private final String companyName = "$.companyName";
    private final String isActive = "$.isActive";
    private final String id = "$.id";

    @Test
    @DisplayName("Add Company")
    @Order(1)
    public void saveCompany_whenSaved_thenReturnCompanyDto() throws Exception {
        when(companyService.add(any(CompanyDto.class))).thenReturn(companyDto);
        // When
        String endPoint = "/api/v1/company/";
        ResultActions result = mockMvc.perform(post(endPoint)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(companyDto)));

        // Then

        result.andExpect(status().isCreated())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath(companyName).value(companyDto.getCompanyName()))
                .andExpect(jsonPath(isActive).value(companyDto.getIsActive().toString()));

        verify(companyService, times(1)).add(any(CompanyDto.class));
    }

    @Test
    @DisplayName("Update Company")
    @Order(2)
    public void updateCompany_whenUpdated_thenReturnCompanyDto() throws Exception {
        companyDto.setId(1L);
        // When
        when(companyService.add(any(CompanyDto.class))).thenReturn(companyDto);

        String endPoint = "/api/v1/company/";
        ResultActions result = mockMvc.perform(put(endPoint)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(companyDto)));

        // Then
        result.andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath(companyName).value(companyDto.getCompanyName()))
                .andExpect(jsonPath(isActive).value(companyDto.getIsActive().toString()))
                .andExpect(jsonPath(id).value(companyDto.getId()));

        verify(companyService, times(1)).add(any(CompanyDto.class));

    }

    @Test
    @DisplayName("Get Company By Id")
    @Order(3)
    public void getCompanyById_whenFind_thenReturnCompanyDto() throws Exception {
        companyDto.setId(100L);
        // When
        when(companyService.getCompanyById(any(Long.class))).thenReturn(companyDto);

        Long searchCompanyId = companyDto.getId();
        String endPoint = String.format("/api/v1/company/%d", 1);

        ResultActions result = mockMvc.perform(get(endPoint, searchCompanyId)
                        .contentType(MediaType.APPLICATION_JSON));

        // Then
        result.andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath(companyName).value(companyDto.getCompanyName()))
                .andExpect(jsonPath(isActive).value(companyDto.getIsActive().toString()))
                .andExpect(jsonPath(id).value(companyDto.getId().toString()));
    }

    @Test
    @DisplayName("Get All Companies")
    public void getAllCompanies_whenGetAll_thenReturnListOfCompanies() throws Exception {
        IntStream.range(0, companyDtoList.size())
                .forEach(i -> companyDtoList.get(i).setId((long) (i + 1)));

        // When
        when(companyService.getAllCompanies()).thenReturn(companyDtoList);
        String endPoint = "/api/v1/company/";
        ResultActions result = mockMvc.perform(get(endPoint)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(companyDtoList)));

        MvcResult mvcResult = result.andReturn();
        String responseBody = mvcResult.getResponse().getContentAsString();

        //Convert
        ObjectMapper objectMapper = new ObjectMapper();
        List<CompanyDto> responseBodyList =
                objectMapper.readValue(responseBody, new TypeReference<>() {});

        // Then
        assertNotNull(responseBody);
        assertEquals(companyDtoList.size(), responseBodyList.size());
        for(int i = 0; i < companyDtoList.size(); i++){
            result.andExpect(jsonPath("$[" + i + "].id").value(companyDtoList.get(i).getId()))
                    .andExpect(jsonPath("$[" + i + "].companyName").value(companyDtoList.get(i).getCompanyName()))
                    .andExpect(jsonPath("$[" + i + "].isActive").value(companyDtoList.get(i).getIsActive().toString()));

        }

//        result.andExpect(jsonPath("$[" + i + "].id").value(expectedCompanyDto.getId()))
//                .andExpect(jsonPath("$[" + i + "].companyName").value(expectedCompanyDto.getCompanyName()))
//                .andExpect(jsonPath("$[" + i + "].updatedDate").value(expectedCompanyDto.getUpdatedDate().toString()))
//                .andExpect(jsonPath("$[" + i + "].isActive").value(expectedCompanyDto.isActive()));



    }





}
