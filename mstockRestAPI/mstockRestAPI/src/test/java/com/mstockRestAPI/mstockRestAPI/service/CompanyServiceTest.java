package com.mstockRestAPI.mstockRestAPI.service;


import com.mstockRestAPI.mstockRestAPI.dto.CompanyDto;
import com.mstockRestAPI.mstockRestAPI.dto.converter.Converter;
import com.mstockRestAPI.mstockRestAPI.entity.Company;
import com.mstockRestAPI.mstockRestAPI.repository.CompanyRepository;
import com.mstockRestAPI.mstockRestAPI.service.impl.CompanyServiceImpl;
import org.aspectj.lang.annotation.Before;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;

import java.sql.Timestamp;

@SpringBootTest
public class CompanyServiceTest {

    @Mock
    private Converter converter;

    @Mock
    private CompanyRepository companyRepository;

    @InjectMocks
    private CompanyServiceImpl companyService;

    private Company companyEntity;
    private CompanyDto companyDto;

    private final Timestamp createdDate = Timestamp.valueOf("2023-12-03 17:48:52.083725");

    public void setupTestData(){

        this.companyEntity = Company.builder()
                .id(1L)
                .companyName("Nexus")
                .createdDate(createdDate)
                .updatedDate(createdDate)
                .isActive((byte)1)
                .build();

        this.companyDto =  CompanyDto.builder()
                .id(1L)
                .companyName("Nexus")
                .updatedDate(createdDate)
                .isActive((byte)1)
                .build();

    }

    @BeforeEach
    public void beforeEach(){
        setupTestData();
    }

    @Test
    @DisplayName("Save Company")
    public void givenCompanyObject_whenSave_thenReturnCompanyObject(){
        // Arrange
        Mockito.when(converter.mapToEntity(companyDto, Company.class))
                .thenReturn(companyEntity);
        Mockito.when(converter.mapToDto(companyEntity, CompanyDto.class))
                .thenReturn(companyDto);
        Mockito.when(companyRepository.save(companyEntity)).thenReturn(companyEntity);

        // Act
        CompanyDto companySaved = companyService.add(companyDto);

        // Asserts
        Assertions.assertEquals(companyDto.getCompanyName(), companySaved.getCompanyName());
        Assertions.assertEquals(companyDto.getIsActive(), companySaved.getIsActive());
        Assertions.assertEquals(companyDto.getUpdatedDate(), companySaved.getUpdatedDate());

    }










}
