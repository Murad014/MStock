package com.mstockRestAPI.mstockRestAPI.repository;

import com.mstockRestAPI.mstockRestAPI.dto.CompanyDto;
import com.mstockRestAPI.mstockRestAPI.entity.Company;
import static org.junit.jupiter.api.Assertions.*;

import com.mstockRestAPI.mstockRestAPI.tools.creator.CompanyCreator;
import com.mstockRestAPI.mstockRestAPI.tools.creator.ProductCategoryCreator;
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

    private List<Company> companies;
    private Company company;

    @BeforeEach
    public void beforeEach(){
        company = CompanyCreator.createCompanyEntity();
        companies = CompanyCreator.createCompanyEntities();
        companyRepository.deleteAll();
    }

    @Test
    @DisplayName("Save company")
    @Order(1)
    public void givenCompanyObject_whenSave_thenReturnSaveCompany(){

        // Act
        Company savedCompany = companyRepository.save(company);

        // Assert
        assertions(company, savedCompany);
    }

    @Test
    @DisplayName("Update company")
    @Order(2)
    public void givenCompanyObject_whenUpdated_thenReturnCompanyObject(){
        // Arrange
        String companyName = "CocaCola";
        Long updatedId = 1L;
        Byte isActive = 1;

        // Act
        Company savedCompany = companyRepository.save(company);

        // Arrange
        savedCompany.setId(updatedId);
        savedCompany.setCompanyName(companyName);
        savedCompany.setIsActive(isActive);

        // Act
        Company updatedCompany = companyRepository.save(company);

        // Assets
        assertions(savedCompany, updatedCompany);

    }

    @Test
    @DisplayName("Get company by Id")
    @Order(3)
    public void givenId_whenFind_thenReturnCompany(){
        Company savedCompany = companyRepository.save(company);

        // Act
        Company companyFromDB = companyRepository.findById(savedCompany.getId()).orElse(null);

        // Assert
        assertions(savedCompany, companyFromDB);

    }

    @Test
    @DisplayName("Get all companies")
    @Order(4)
    public void whenFindAll_ThenReturnListOfCompanies(){
        // Arrange
         companyRepository.saveAll(companies);

        // Act
        List<Company> companiesFromDB = companyRepository.findAll();

        // Assert
        int expectedCompaniesCount = companies.size();

        assertNotNull(companiesFromDB);
        assertFalse(companiesFromDB.isEmpty());
        assertEquals(expectedCompaniesCount, companiesFromDB.size());
    }

    @Test
    @DisplayName("Find company by company name")
    @Order(5)
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
    @Order(6)
    public void givenId_whenFind_thenReturnCompanyObject(){
        // Arrange
        companyRepository.save(company);

        // Act
        Company companyFromDB = companyRepository.findById(company.getId())
                .orElse(null);

        // Asserts
        assertions(company, companyFromDB);
    }

    @Test
    @DisplayName("Check exists company by Name")
    @Order(7)
    public void givenCompanyName_whenExists_returnBoolean(){
        // Arrange
        Company companyFromDB = companyRepository.save(company);
        String notExists = "unknown";

        // Act
        boolean existsCompany = companyRepository.existsByCompanyName(companyFromDB.getCompanyName());
        boolean notExistsCompany = companyRepository.existsByCompanyName(notExists);

        // Assert
        assertTrue(existsCompany);
        assertFalse(notExistsCompany);
    }


    private void assertions(Company expected, Company actual){
        assertNotNull(actual);
        assertEquals(expected.getId(), actual.getId());
        assertEquals(expected.getCompanyName(), actual.getCompanyName());
        assertEquals(expected.getIsActive(), actual.getIsActive());
        assertEquals(expected.getCreatedDate(), actual.getCreatedDate());
        assertEquals(expected.getUpdatedDate(), actual.getUpdatedDate());

    }




}
