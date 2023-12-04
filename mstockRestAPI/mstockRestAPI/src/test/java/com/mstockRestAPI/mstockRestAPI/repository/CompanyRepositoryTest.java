package com.mstockRestAPI.mstockRestAPI.repository;

import com.mstockRestAPI.mstockRestAPI.dto.CompanyDto;
import com.mstockRestAPI.mstockRestAPI.entity.Company;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;

import java.sql.Timestamp;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

@SpringBootTest
@TestPropertySource(locations = "/application-test.properties",
properties = "server.port=8081")
public class CompanyRepositoryTest {

    @Autowired
    @Mock
    private CompanyRepository companyRepository;

    @InjectMocks
    private ModelMapper modelMapper;

    private final List<Company> companies = new ArrayList<>();

    private Company company;

    private final Timestamp createdDate = Timestamp.valueOf("2023-12-03 17:48:52.083725");

    @BeforeEach
    public void setupTestData(){

        this.company = Company.builder()
                .id(1L)
                .companyName("Nexus")
                .createdDate(createdDate)
                .updatedDate(createdDate)
                .isActive((byte)1)
                .build();

        companies.add(company);
        companies.add(
                Company.builder()
                        .companyName("Royal")
                        .createdDate(createdDate)
                        .updatedDate(createdDate)
                        .isActive((byte) 0)
                        .build()
        );
    }

    @Test
    @DisplayName("Save company")
    public void givenCompanyObject_whenSave_thenReturnSaveCompany(){

        // Act
        Company savedCompany = companyRepository.save(company);

        // Assert
        assertEquals(company.getCompanyName(), savedCompany.getCompanyName());
        assertEquals(company.getIsActive(), savedCompany.getIsActive());
        assertEquals(company.getCreatedDate(), savedCompany.getCreatedDate());
        assertEquals(company.getUpdatedDate(), savedCompany.getUpdatedDate());
    }

    @Test
    @DisplayName("Get company by Id")
    public void givenId_whenFind_thenReturnCompany(){
        // Arrange
        Company savedCompany = companyRepository.save(company);

        // Act
        Company companyFromDB = companyRepository.findById(savedCompany.getId()).orElse(null);

        // Assert
        assert companyFromDB != null;
        assertEquals(savedCompany.getId(), companyFromDB.getId());
        assertEquals(savedCompany.getCompanyName(), companyFromDB.getCompanyName());
        assertEquals(savedCompany.getUpdatedDate(), companyFromDB.getUpdatedDate());
        assertEquals(savedCompany.getCreatedDate(), companyFromDB.getCreatedDate());
        assertEquals(savedCompany.getIsActive(), companyFromDB.getIsActive());

    }

    @Test
    @DisplayName("Get all companies")
    public void whenFindAll_ThenReturnListOfCompanies(){
        // Arrange
        for(Company company: companies){
            companyRepository.save(company);
        }

        // Act
        List<Company> companiesFromDB = companyRepository.findAll();

        // Assert
        int expectedCompaniesCount = 2;
        assertEquals(expectedCompaniesCount, companiesFromDB.size());
        for(int i = 0; i < companies.size(); i++){
            assertEquals(companies.get(i).getCompanyName(), companiesFromDB.get(i).getCompanyName());
            assertEquals(companies.get(i).getIsActive(), companiesFromDB.get(i).getIsActive());
            assertEquals(companies.get(i).getCreatedDate(), companiesFromDB.get(i).getCreatedDate());
            assertEquals(companies.get(i).getUpdatedDate(), companiesFromDB.get(i).getUpdatedDate());
        }
    }

    @Test
    @DisplayName("Find company by company name")
    public void givenCompanyName_whenFind_returnCompanyObject(){
        // Arrange
        companyRepository.save(company);

        // Act
        Company companyFromDBByName = companyRepository.findByCompanyName("Nexus");
        Company companyThatNotExist = companyRepository.findByCompanyName("Unknown");

        // Assert
        assert companyFromDBByName != null;
        assertEquals(company.getCompanyName(), companyFromDBByName.getCompanyName(),
                "Company name could not find.");
        assertNull(companyThatNotExist);

        System.out.println(companyFromDBByName);
    }

    @Test
    @DisplayName("Check exists company by Name")
    public void givenCompanyName_whenExists_returnBoolean(){
        // Arrange
        Company companyFromDB = companyRepository.save(company);

        // Act
        String notExists = "unknown";
        boolean existsCompany = companyRepository.existsByCompanyName(companyFromDB.getCompanyName());
        boolean notExistsCompany = companyRepository.existsByCompanyName(notExists);

        // Assert
        assertTrue(existsCompany);
        assertFalse(notExistsCompany);
    }






}
