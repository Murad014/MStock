package com.mstockRestAPI.mstockRestAPI.converter;

import com.mstockRestAPI.mstockRestAPI.dto.CompanyDto;
import com.mstockRestAPI.mstockRestAPI.payload.converter.Converter;
import com.mstockRestAPI.mstockRestAPI.entity.Company;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.modelmapper.ModelMapper;
import org.springframework.boot.test.context.SpringBootTest;

import java.sql.Timestamp;
import java.time.LocalDateTime;

@SpringBootTest
public class ConverterTest {

    @Mock
    private ModelMapper modelMapper;

    @InjectMocks
    private Converter converter;

    private Company companyEntity;
    private CompanyDto companyDto;
    private final LocalDateTime createdDate = LocalDateTime.now();

    private void setupTestData(){

        this.companyEntity = new Company();
        this.companyEntity.setId(1L);
        this.companyEntity.setCompanyName("Nexus");
        this.companyEntity.setCreatedDate(createdDate);
        this.companyEntity.setUpdatedDate(createdDate);
        this.companyEntity.setIsActive((byte)1);

        this.companyDto = CompanyDto.builder()
                .id(1L)
                .companyName("Royal")
                .isActive((byte)0)
                .build();


    }

    @BeforeEach
    public void beforeEach(){
        setupTestData();
    }

    @Test
    @DisplayName("Convert DTO to Entity")
    public void givenDTO_whenConvert_thenReturnEntity(){
        // Act
        Mockito.when(modelMapper.map(companyDto, Company.class)).thenReturn(companyEntity);
        Company company = converter.mapToEntity(companyDto, Company.class);

        // Assert
        assertEquals(companyEntity.getCompanyName(), company.getCompanyName());
        assertEquals(companyEntity.getCreatedDate(), company.getCreatedDate());
        assertEquals(companyEntity.getIsActive(), company.getIsActive());
        assertEquals(companyEntity.getUpdatedDate(), company.getUpdatedDate());
    }

    @Test
    @DisplayName("Convert Entity to DTO")
    public void givenEntity_whenConvert_thenReturnDto(){
        // Act
        Mockito.when(modelMapper.map(companyEntity, CompanyDto.class)).thenReturn(companyDto);
        CompanyDto companyDto = converter.mapToEntity(companyEntity, CompanyDto.class);

        // Assert
        assertEquals(companyDto.getCompanyName(), companyDto.getCompanyName());
        assertEquals(companyDto.getIsActive(), companyDto.getIsActive());

    }




}
