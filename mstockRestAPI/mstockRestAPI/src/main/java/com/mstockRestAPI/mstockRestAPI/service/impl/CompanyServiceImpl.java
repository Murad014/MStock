package com.mstockRestAPI.mstockRestAPI.service.impl;

import com.mstockRestAPI.mstockRestAPI.dto.CompanyDto;
import com.mstockRestAPI.mstockRestAPI.dto.converter.Converter;
import com.mstockRestAPI.mstockRestAPI.entity.Company;
import com.mstockRestAPI.mstockRestAPI.repository.CompanyRepository;
import com.mstockRestAPI.mstockRestAPI.service.CompanyService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public class CompanyServiceImpl implements CompanyService {

    private final CompanyRepository companyRepository;
    private final Converter converter;

    @Autowired
    public CompanyServiceImpl(CompanyRepository companyRepository,
                              Converter converter){
        this.companyRepository = companyRepository;
        this.converter = converter;

    }
    @Override
    public CompanyDto add(CompanyDto companyDto) {
        Company company = converter.mapToEntity(companyDto, Company.class);
        Company companySaved = companyRepository.save(company);

        return converter.mapToDto(companySaved, CompanyDto.class);
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
