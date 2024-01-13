package com.mstockRestAPI.mstockRestAPI.repository;

import com.mstockRestAPI.mstockRestAPI.entity.Company;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CompanyRepository extends JpaRepository<Company, Long> {
    Company findByCompanyName(String companyName);
    boolean existsByCompanyName(String companyName);
    List<Company> findByIsActive(Byte isActive);
}
