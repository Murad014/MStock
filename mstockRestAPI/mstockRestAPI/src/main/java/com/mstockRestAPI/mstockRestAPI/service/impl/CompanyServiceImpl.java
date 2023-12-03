package com.mstockRestAPI.mstockRestAPI.service.impl;

import com.mstockRestAPI.mstockRestAPI.dto.CompanyDto;
import com.mstockRestAPI.mstockRestAPI.entity.Company;
import com.mstockRestAPI.mstockRestAPI.helper.Helper;
import com.mstockRestAPI.mstockRestAPI.repository.CompanyRepository;
import com.mstockRestAPI.mstockRestAPI.service.CompanyService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public class CompanyServiceImpl implements CompanyService {

    private final CompanyRepository companyRepository;
    @Autowired
    public CompanyServiceImpl(CompanyRepository companyRepository){
        this.companyRepository = companyRepository;

    }
    @Override
    public CompanyDto add(CompanyDto companyDto) {
        Company company = Helper.mapToEntity(companyDto, Company.class);

        return Helper.mapToDto(
                companyRepository.save(company),
                CompanyDto.class);
    }

    @Override
    public CompanyDto update(CompanyDto companyDto) {
        return null;
    }

    @Override
    public List<CompanyDto> getAllCompanies() {
        return null;
    }

    @Override
    public CompanyDto getCompanyById(Long id) {
        return null;
    }

    @Override
    public CompanyDto existsByCompanyName(String name) {
        return null;
    }




}
