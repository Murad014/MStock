package com.mstockRestAPI.mstockRestAPI.service;


import com.mstockRestAPI.mstockRestAPI.dto.CompanyDto;
import com.mstockRestAPI.mstockRestAPI.dto.converter.Converter;
import com.mstockRestAPI.mstockRestAPI.entity.Company;
import com.mstockRestAPI.mstockRestAPI.exception.ResourceNotFoundException;
import com.mstockRestAPI.mstockRestAPI.repository.CompanyRepository;
import com.mstockRestAPI.mstockRestAPI.service.impl.CompanyServiceImpl;
import com.mstockRestAPI.mstockRestAPI.tools.creator.CompanyCreator;
import org.aspectj.lang.annotation.Before;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.*;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.mockito.Mockito.*;
import org.springframework.boot.test.context.SpringBootTest;

import java.sql.Timestamp;
import java.util.List;

@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class CompanyServiceTest {

    @Mock
    private Converter converter;

    @Mock
    private CompanyRepository companyRepository;

    @InjectMocks
    private CompanyServiceImpl companyService;

    private final Company companyEntity = CompanyCreator.createCompanyEntity();
    private final CompanyDto companyDto = CompanyCreator.createCompanyDto();


    private final Timestamp createdDate = Timestamp.valueOf("2023-12-03 17:48:52.083725");



    @Test
    @DisplayName("Save Company")
    @Order(1)
    public void givenCompanyObject_whenSave_thenReturnCompanyObject(){
      //  System.out.println(companyDto);
        // Arrange
        when(converter.mapToEntity(companyDto, Company.class))
                .thenReturn(companyEntity);
        when(converter.mapToDto(companyEntity, CompanyDto.class))
                .thenReturn(companyDto);
        when(companyRepository.save(companyEntity)).thenReturn(companyEntity);

        // Act
        CompanyDto companySaved = companyService.add(companyDto);

        // Asserts
        assertEquals(companyDto.getCompanyName(), companySaved.getCompanyName());
        assertEquals(companyDto.getIsActive(), companySaved.getIsActive());
        assertEquals(companyDto.getUpdatedDate(), companySaved.getUpdatedDate());
    }

    @Test
    @DisplayName("Find Company by Id")
    @Order(2)
    public void givenId_whenFind_thenReturnCompanyObject(){
        Long id = 1L;
        // Arrange
        when(converter.mapToEntity(companyDto, Company.class)).thenReturn(companyEntity);
        when(converter.mapToDto(companyEntity, CompanyDto.class)).thenReturn(companyDto);
        when(companyRepository.findById(id)).thenReturn(java.util.Optional.ofNullable(companyEntity));

        // Act
        CompanyDto companyFromDB = companyService.getCompanyById(id);

        // Assert
        assertNotNull(companyFromDB);
        assertEquals(companyDto.getCompanyName(), companyFromDB.getCompanyName());
        assertEquals(companyDto.getId(), companyFromDB.getId());
        assertEquals(companyDto.getIsActive(), companyFromDB.getIsActive());
    }

    @Test
    @DisplayName("Update Company by Id if there is")
    @Order(3)
    public void givenId_whenFindAndUpdate_thenReturnUpdateCompanyObject(){
        Long notExistID = 100L;
        Long existId = 1L;

        // Arrange
        when(converter.mapToEntity(companyDto, Company.class)).thenReturn(companyEntity);
        when(converter.mapToDto(companyEntity, CompanyDto.class)).thenReturn(companyDto);
        when(companyRepository.save(companyEntity)).thenReturn(companyEntity);
        when(companyRepository.findById(existId)).thenReturn(java.util.Optional.of(companyEntity));

        // Act
        companyDto.setId(1L);
        CompanyDto companyDtoUpdated = companyService.update(companyDto.getId(), companyDto);

        // Arrange
        assertNotNull(companyDtoUpdated);
        assertEquals(existId, companyDtoUpdated.getId());
        assertEquals(companyDto.getCompanyName(), companyDtoUpdated.getCompanyName());
        assertEquals(companyDto.getIsActive(), companyDtoUpdated.getIsActive());
        assertEquals(companyDto.getUpdatedDate(), companyDtoUpdated.getUpdatedDate());

        assertThrows(ResourceNotFoundException.class, () -> {
            companyService.update(notExistID, companyDto);
        }, "If there is not company in DB then should be throw exception.");
    }










}
