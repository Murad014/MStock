package com.mstockRestAPI.mstockRestAPI.service;


import com.mstockRestAPI.mstockRestAPI.dto.CompanyDto;
import com.mstockRestAPI.mstockRestAPI.dto.converter.Converter;
import com.mstockRestAPI.mstockRestAPI.entity.Company;
import com.mstockRestAPI.mstockRestAPI.exception.ResourceNotFoundException;
import com.mstockRestAPI.mstockRestAPI.repository.CompanyRepository;
import com.mstockRestAPI.mstockRestAPI.service.impl.CompanyServiceImpl;
import com.mstockRestAPI.mstockRestAPI.tools.creator.CompanyCreator;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.*;
import org.junit.platform.commons.util.StringUtils;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.mockito.Mockito.*;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;

import java.sql.Timestamp;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@TestPropertySource(locations = "/application-test.properties")
public class CompanyServiceTest {
    @Mock
    private Converter converter;
    @Mock
    private CompanyRepository companyRepository;
    @InjectMocks
    private CompanyServiceImpl companyService;

    private Company companyEntity;
    private List<Company> companyEntityList;
    private CompanyDto companyDto;
    private List<CompanyDto> companyDtoList;
    private final Timestamp createdDate = Timestamp.valueOf("2023-12-03 17:48:52.083725");


    @BeforeEach
    public void beforeEach(){
        companyDto = CompanyCreator.createCompanyDto();
        companyEntity = CompanyCreator.createCompanyEntity();
        companyDtoList = CompanyCreator.createListOfCompanyDto();
        companyEntityList = CompanyCreator.createCompanyEntities();

        companyRepository.deleteAll();
    }

    @Test
    @DisplayName("Save Company")
    @Order(1)
    public void givenCompanyObject_whenSave_thenReturnCompanyObject(){

        // Arrange
        when(converter.mapToEntity(companyDto, Company.class))
                .thenReturn(companyEntity);
        when(converter.mapToDto(companyEntity, CompanyDto.class))
                .thenReturn(companyDto);
        when(companyRepository.save(companyEntity)).thenReturn(companyEntity);

        // Act
        CompanyDto companySaved = companyService.add(companyDto);

        // Asserts
        assertions(companyDto, companySaved);

    }

    @Test
    @DisplayName("Find Company by Id")
    @Order(2)
    public void givenId_whenFind_thenReturnCompanyObject(){
        Long id = 1L;
        // Arrange
        when(converter.mapToEntity(companyDto, Company.class))
                .thenReturn(companyEntity);
        when(converter.mapToDto(companyEntity, CompanyDto.class))
                .thenReturn(companyDto);
        when(companyRepository.findById(id)).thenReturn(java.util.Optional.ofNullable(companyEntity));

        // Act
        CompanyDto companyFromDB = companyService.getCompanyById(id);

        // Assert
        assertions(companyDto, companyFromDB);
    }

    @Test
    @DisplayName("Update Company by Id if there is and vice versa")
    @Order(3)
    public void givenId_whenFindAndUpdate_thenReturnUpdateCompanyObject(){
        Long notExistID = 100L;
        Long existId = 1L;

        // Arrange
        when(converter.mapToEntity(companyDto, Company.class))
                .thenReturn(companyEntity);
        when(converter.mapToDto(companyEntity, CompanyDto.class))
                .thenReturn(companyDto);
        when(companyRepository.save(companyEntity))
                .thenReturn(companyEntity);
        when(companyRepository.findById(existId))
                .thenReturn(java.util.Optional.of(companyEntity));

        // Act
        companyDto.setId(1L);
        CompanyDto companyDtoUpdated = companyService.update(companyDto.getId(), companyDto);

        // Assert
        assertions(companyDto, companyDtoUpdated);
        assertThrows(ResourceNotFoundException.class, () -> {
            companyService.update(notExistID, companyDto);
        }, "If there is not company in DB then should be throw exception.");

        // Verify
        verify(converter, times(1)).mapToDto(any(), any());

    }

    @Test
    @DisplayName("Add all companies")
    @Order(4)
    public void givenListCompanies_whenSaveAll_returnCompanyListDto(){
        // When
        mockConverterMapping();
        when(companyRepository.saveAll(any()))
                .thenReturn(companyEntityList);
        when(companyRepository.findAll())
                .thenReturn(companyEntityList);

        // Act
        List<CompanyDto> savedList = companyService.addAll(companyDtoList);

        // Verify
        verify(companyRepository, times(1)).saveAll(anyList());
        verify(converter, times(companyDtoList.size())).mapToEntity(any(), any());
        verify(converter, times(companyDtoList.size())).mapToDto(any(), any());

        // Assertion
        assertNotNull(savedList);
        assertFalse(savedList.isEmpty());
        assertEquals(companyDtoList.size(), savedList.size());

    }


    @Test
    @DisplayName("Get All where isActive = 1")
    @Order(5)
    public void givenIsActiveAndDeActive_whenFind_thenReturnActiveCompanies(){
        setIsActiveOfDtoAndEntityList();

        // when
        mockConverterMapping();
        List<Company> entity = companyEntityList.stream()
                .filter(c -> c.getIsActive() == 1).toList();
        when(companyRepository.findByIsActive(any()))
                .thenReturn(entity);

        // Arrange
        Byte isActive = 1;
        List<CompanyDto> dto = companyService.getAllCompaniesWhereIsActive(isActive);

        // Assert
        assertNotNull(dto);
        assertFalse(dto.isEmpty());
        assertEquals(entity.size(), dto.size());

    }

    @Test
    @DisplayName("Get All Companies")
    @Order(6)
    public void getAllCompanies_whenGet_thenReturnListOfCompaniesDto(){
        // When
        mockConverterMapping();
        when(companyRepository.findAll())
                .thenReturn(companyEntityList);

        // Act
        List<CompanyDto> getAllCompany = companyService.getAllCompanies();

        assertNotNull(getAllCompany);
        assertFalse(getAllCompany.isEmpty());
        assertEquals(companyEntityList.size(), getAllCompany.size());

    }

    @Test
    @DisplayName("Exists by company name")
    @Order(7)
    public void givenCompanyName_whenFind_thenReturnCompanyDto(){
        // When
        mockConverterMapping();
        when(companyRepository.existsByCompanyName(anyString()))
                .thenReturn(true);

        // Act
        boolean exists = companyService.existsByCompanyName("TEST");
        assertTrue(exists);
    }





    private void setIsActiveOfDtoAndEntityList(){
        for(int i = 0; i < companyEntityList.size(); i++){

            if(i % 2 == 0){
                companyEntityList.get(i).setIsActive((byte) 1);
                companyDtoList.get(i).setIsActive((byte) 1);
            }
            else {
                companyEntityList.get(i).setIsActive((byte) 0);
                companyDtoList.get(i).setIsActive((byte) 0);
            }
        }
    }


    private void mockConverterMapping() {
        when(converter.mapToDto(any(), eq(CompanyDto.class)))
                .thenAnswer(invocation -> {
                    Company companyDtoArgument = invocation.getArgument(0);
                    return convertCompanyEntityToDto(companyDtoArgument);
                });

        when(converter.mapToEntity(any(), eq(Company.class)))
                .thenAnswer(invocation -> {
                    CompanyDto companyDtoArgument = invocation.getArgument(0);
                    return convertCompanyDtoToEntity(companyDtoArgument);
                });



    }


    private void assertions(CompanyDto expected, CompanyDto actual){
        assertNotNull(actual);
        assertEquals(expected.getId(), actual.getId());
        assertEquals(expected.getCompanyName(), actual.getCompanyName());
        assertEquals(expected.getIsActive(), actual.getIsActive());
        assertEquals(expected.getUpdatedDate(), actual.getUpdatedDate());
    }

    private Company convertCompanyDtoToEntity(CompanyDto companyDto) {
        return new Company().builder()
                .companyName(companyDto.getCompanyName())
                .isActive(companyDto.getIsActive())
                .updatedDate(companyDto.getUpdatedDate())
                .build();

    }

    private CompanyDto convertCompanyEntityToDto(Company company) {
        return new CompanyDto().builder()
                .companyName(company.getCompanyName())
                .isActive(company.getIsActive())
                .updatedDate(company.getUpdatedDate())
                .build();

    }


}
