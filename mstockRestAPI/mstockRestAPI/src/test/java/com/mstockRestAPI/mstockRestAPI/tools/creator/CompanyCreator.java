package com.mstockRestAPI.mstockRestAPI.tools.creator;

import com.mstockRestAPI.mstockRestAPI.dto.CompanyDto;
import com.mstockRestAPI.mstockRestAPI.entity.Company;
import net.bytebuddy.utility.RandomString;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class CompanyCreator {
    private static Company company;
    private static  final Timestamp createdDate = Timestamp.valueOf("2023-12-03 17:48:52.083725");
    private static final Random random = new Random();

    public static List<Company> createCompanyEntities(){
        List<Company> companies = new ArrayList<>();

        for(int i = 0; i < 5; i++){
            byte by = (byte) (i % 2);
            Company company = Company.builder()
                    .companyName(RandomString.make(10))
                    .isActive(by)
                    .createdDate(createdDate)
                    .updatedDate(createdDate)
                    .build();
            companies.add(company);
        }
        return companies;
    }

    public static Company createCompanyEntity(){
        return Company.builder()
                .companyName(RandomString.make(10))
                .isActive((byte)1)
                .createdDate(createdDate)
                .updatedDate(createdDate)
                .build();
    }


    public static List<CompanyDto> createListOfCompanyDto(){
        List<CompanyDto> companies = new ArrayList<>();

        for(int i = 0; i < 5; i++){
            byte active = (byte) (i % 2);
            CompanyDto company = CompanyDto.builder()
                    .companyName(RandomString.make(10))
                    .isActive(active)
                    .updatedDate(createdDate)
                    .build();
            companies.add(company);
        }
        return companies;
    }

    public static CompanyDto createCompanyDto(){
        return CompanyDto.builder()
                .companyName(RandomString.make(10))
                .isActive((byte)1)
                .updatedDate(createdDate)
                .build();
    }


}
