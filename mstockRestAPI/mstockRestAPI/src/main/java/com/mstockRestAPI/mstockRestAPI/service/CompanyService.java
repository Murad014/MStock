package com.mstockRestAPI.mstockRestAPI.service;

import com.mstockRestAPI.mstockRestAPI.dto.CompanyDto;
import org.springframework.stereotype.Service;

import java.util.Iterator;
import java.util.List;
import java.util.Set;

public interface CompanyService {
    List<CompanyDto> addAll(List<CompanyDto> companyDtoList);
    CompanyDto add(CompanyDto companyDto);
    CompanyDto update(Long companyId, CompanyDto companyDto);
    List<CompanyDto> getAllCompanies();
    CompanyDto getCompanyById(Long companyId);

    boolean existsByCompanyName(String name);
    List<CompanyDto> getAllCompaniesWhereIsActive(Byte isActive);

}
