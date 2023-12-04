package com.mstockRestAPI.mstockRestAPI.service;

import com.mstockRestAPI.mstockRestAPI.dto.CompanyDto;

import java.util.Iterator;
import java.util.List;
import java.util.Set;

public interface CompanyService {
    CompanyDto add(CompanyDto companyDto);
    CompanyDto update(Long companyId, CompanyDto companyDto);
    List<CompanyDto> getAllCompanies();
    CompanyDto getCompanyById(Long companyId);
    CompanyDto existsByCompanyName(String name);
}
