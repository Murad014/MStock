package com.mstockRestAPI.mstockRestAPI.repository;

import com.mstockRestAPI.mstockRestAPI.dto.CompanyDto;
import com.mstockRestAPI.mstockRestAPI.entity.Company;
import static org.junit.jupiter.api.Assertions.*;

import com.mstockRestAPI.mstockRestAPI.tools.creator.CompanyCreator;
import org.junit.jupiter.api.*;
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
import java.util.Optional;

@SpringBootTest
@TestPropertySource(locations = "/application-test.properties",
properties = "server.port=8081")
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class CompanyRepositoryTest {

    @Autowired
    private CompanyRepository companyRepository;

    private final static List<Company> companies = CompanyCreator.createCompanyEntities();
    private final static Company company = CompanyCreator.createCompanyEntity();

    @Test
    @DisplayName("Save company")
    @Order(7)
    public void givenCompanyObject_whenSave_thenReturnSaveCompany(){

        // Act
        Company savedCompany = companyRepository.save(company);

        // Assert
        assertNotNull(savedCompany);
        assertEquals(company.getCompanyName(), savedCompany.getCompanyName());
        assertEquals(company.getIsActive(), savedCompany.getIsActive());
        assertEquals(company.getCreatedDate(), savedCompany.getCreatedDate());
        assertEquals(company.getUpdatedDate(), savedCompany.getUpdatedDate());
    }

    @Test
    @DisplayName("Update company")
    @Order(6)
    public void givenCompanyObject_whenUpdated_thenReturnCompanyObject(){
        // Arrange
        Company company2 = CompanyCreator.createCompanyEntity();
        String companyName = "CocaCola";
        Long updatedId = 1L, savedId = 0L;
        Byte isActive = 1;
        company2.setId(savedId);
        companyRepository.save(company);
        company2.setId(updatedId);
        company2.setCompanyName(companyName);
        company2.setIsActive(isActive);

        // Act
        Company updatedCompany = companyRepository.save(company2);

        // Assets
        assertNotNull(updatedCompany);
        assertEquals(companyName, updatedCompany.getCompanyName());
        assertEquals(isActive, updatedCompany.getIsActive());
        assertEquals(updatedId, updatedCompany.getId());

    }

    @Test
    @DisplayName("Get company by Id")
    @Order(2)
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
    @Order(1)
    public void whenFindAll_ThenReturnListOfCompanies(){
        // Arrange
         companyRepository.saveAll(companies);

        // Act
        List<Company> companiesFromDB = companyRepository.findAll();
        // Assert
        int expectedCompaniesCount = companies.size();
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
    @Order(3)
    public void givenCompanyName_whenFind_returnCompanyObject(){
        // Arrange
        companyRepository.save(company);

        // Act
        Company companyFromDBByName = companyRepository.findByCompanyName(company.getCompanyName());
        Company companyThatNotExist = companyRepository.findByCompanyName("Unknown");

        // Assert
        assert companyFromDBByName != null;
        assertEquals(company.getCompanyName(), companyFromDBByName.getCompanyName(),
                "Company name could not find.");
        assertNull(companyThatNotExist);
    }

    @Test
    @DisplayName("Find company by id")
    @Order(4)
    public void givenId_whenFind_thenReturnCompanyObject(){
        // Arrange
        companyRepository.save(company);

        // Act
        Company companyFromDB = companyRepository.findById(company.getId())
                .orElse(null);

        // Asserts
        assertNotNull(companyFromDB);
        assertEquals(companyFromDB.getId(), companyFromDB.getId());
        assertEquals(companyFromDB.getCompanyName(), companyFromDB.getCompanyName());
        assertEquals(companyFromDB.getUpdatedDate(), companyFromDB.getUpdatedDate());
        assertEquals(companyFromDB.getCreatedDate(), companyFromDB.getCreatedDate());
        assertEquals(companyFromDB.getIsActive(), companyFromDB.getIsActive());
    }

    @Test
    @DisplayName("Check exists company by Name")
    @Order(5)
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
