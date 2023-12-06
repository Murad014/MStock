package com.mstockRestAPI.mstockRestAPI.controller;


import com.mstockRestAPI.mstockRestAPI.dto.CompanyDto;
import com.mstockRestAPI.mstockRestAPI.service.CompanyService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
        var response =  new ResponseEntity<>(companyDtoSaved, HttpStatus.CREATED);
        return response;
    }



}
