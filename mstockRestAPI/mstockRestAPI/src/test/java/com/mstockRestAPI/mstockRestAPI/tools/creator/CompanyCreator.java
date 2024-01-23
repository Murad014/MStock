package com.mstockRestAPI.mstockRestAPI.tools.creator;

import com.mstockRestAPI.mstockRestAPI.dto.CompanyDto;
import com.mstockRestAPI.mstockRestAPI.entity.Company;
import net.bytebuddy.utility.RandomString;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class CompanyCreator {
    private static Company company;
    private static  final Timestamp createdDate = Timestamp.valueOf("2023-12-03 17:48:52.083725");

    private static final int COMPANY_ENTITY_LENGTH = 10;
    public static List<Company> createCompanyEntities(){

        return Stream.generate(CompanyCreator:: createCompanyEntity)
                .limit(COMPANY_ENTITY_LENGTH)
                .toList();
    }

    public static Company createCompanyEntity(){
        Byte isActive = (byte) (Math.random() * 2);

        return Company.builder()
                .companyName(RandomString.make(10))
                .isActive(isActive)
                .build();
    }


    public static List<CompanyDto> createListOfCompanyDto(){
        return Stream.generate(CompanyCreator:: createCompanyDto)
                .limit(COMPANY_ENTITY_LENGTH)
                .toList();
    }

    public static CompanyDto createCompanyDto(){
        return CompanyDto.builder()
                .companyName(RandomString.make(10))
                .isActive((byte)1)
                .build();
    }


}
