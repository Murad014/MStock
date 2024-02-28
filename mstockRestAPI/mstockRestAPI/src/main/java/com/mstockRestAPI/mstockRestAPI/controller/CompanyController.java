package com.mstockRestAPI.mstockRestAPI.controller;


import com.mstockRestAPI.mstockRestAPI.dto.CompanyDto;
import com.mstockRestAPI.mstockRestAPI.entity.Company;
import com.mstockRestAPI.mstockRestAPI.service.CompanyService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@RestController
@RequestMapping("/api/v1/company")
public class CompanyController {
    private final CompanyService companyService;
    @Autowired
    public CompanyController(CompanyService companyService){
        this.companyService = companyService;
    }


    @PostMapping("/")
    public ResponseEntity<CompanyDto> addCompany(@Valid @RequestBody CompanyDto companyDto){
        CompanyDto companyDtoSaved = companyService.add(companyDto);
        return new ResponseEntity<>(companyDtoSaved, HttpStatus.CREATED);
    }

    @PutMapping("/")
    public ResponseEntity<CompanyDto> updateCompany(@Valid @RequestBody CompanyDto companyDto){
        CompanyDto companyDtoUpdate = companyService.update(companyDto.getId(), companyDto);
        return new ResponseEntity<>(companyDtoUpdate, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<CompanyDto> getCompanyById(@PathVariable("id") Long companyId){
        CompanyDto companyFromDB = companyService.getCompanyById(companyId);
        return new ResponseEntity<>(companyFromDB, HttpStatus.OK);
    }

    @GetMapping("/")
    public ResponseEntity<List<CompanyDto>> getAllCompanies(){
        List<CompanyDto> allCompanies = companyService.getAllCompanies();
        return new ResponseEntity<>(allCompanies, HttpStatus.OK);
    }

    @GetMapping("/active")
    public ResponseEntity<List<CompanyDto>> getAllCompaniesWhereIsActive(@RequestParam Byte isActive) {
        List<CompanyDto> companies = companyService.getAllCompaniesWhereIsActive(isActive);
        return new ResponseEntity<>(companies, HttpStatus.OK);
    }






}
