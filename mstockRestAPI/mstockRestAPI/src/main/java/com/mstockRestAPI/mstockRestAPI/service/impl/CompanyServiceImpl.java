package com.mstockRestAPI.mstockRestAPI.service.impl;

import com.mstockRestAPI.mstockRestAPI.dto.CompanyDto;
import com.mstockRestAPI.mstockRestAPI.dto.converter.Converter;
import com.mstockRestAPI.mstockRestAPI.entity.Company;
import com.mstockRestAPI.mstockRestAPI.exception.ResourceNotFoundException;
import com.mstockRestAPI.mstockRestAPI.repository.CompanyRepository;
import com.mstockRestAPI.mstockRestAPI.service.CompanyService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
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
    public List<CompanyDto> addAll(List<CompanyDto> companyDtoList) {
        List<Company> convertDtoToEntity = companyDtoList.stream()
                .map(c -> converter.mapToEntity(c, Company.class))
                .toList();

        List<Company> saveAll = companyRepository.saveAll(convertDtoToEntity);

        return saveAll.stream()
                .map(c ->
                        converter.mapToDto(c, CompanyDto.class)).toList();
    }

    @Override
    public CompanyDto add(CompanyDto companyDto) {
        Company company = converter.mapToEntity(companyDto, Company.class);
        Company companySaved = companyRepository.save(company);

        return converter.mapToDto(companySaved, CompanyDto.class);
    }

    @Override
    public CompanyDto update(Long companyId, CompanyDto companyDto) {
        companyRepository.findById(companyId).orElseThrow(
                () -> new ResourceNotFoundException("Company", "id", companyId.toString()));
        companyDto.setId(companyId);
        Company companyToUpdate = companyRepository.save(converter.mapToEntity(companyDto, Company.class));

        return converter.mapToDto(companyToUpdate, CompanyDto.class);
    }

    @Override
    public List<CompanyDto> getAllCompanies() {
        return companyRepository.findAll().stream().map(c ->
                converter.mapToDto(c, CompanyDto.class)).toList();
    }

    @Override
    public CompanyDto getCompanyById(Long companyId) {
        Company company = companyRepository.findById(companyId).orElseThrow(() ->
                new ResourceNotFoundException("Company", "id", companyId.toString()));

        return converter.mapToDto(company, CompanyDto.class);
    }

    @Override
    public boolean existsByCompanyName(String name) {
        return companyRepository.existsByCompanyName(name);
    }

    @Override
    public List<CompanyDto> getAllCompaniesWhereIsActive(Byte isActive) {
        List<Company> fetchAll = companyRepository.findByIsActive(isActive);

        return fetchAll
                .stream().map(
                        c -> converter.mapToDto(c, CompanyDto.class)
                ).toList();
    }


}
