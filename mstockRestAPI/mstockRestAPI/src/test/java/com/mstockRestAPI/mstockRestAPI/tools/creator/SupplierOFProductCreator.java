package com.mstockRestAPI.mstockRestAPI.tools.creator;

import com.mstockRestAPI.mstockRestAPI.dto.SupplierOfProductDto;
import com.mstockRestAPI.mstockRestAPI.entity.SupplierOfProduct;
import net.bytebuddy.utility.RandomString;

import java.util.List;
import java.util.Random;
import java.util.stream.Stream;

public class SupplierOFProductCreator {

    public static SupplierOfProduct entity(){
        Byte isActive = (byte) (Math.random() * 2);
        return SupplierOfProduct.builder()
                .name(RandomString.make(7))
                .surname(RandomString.make(10))
                .email(RandomString.make(5) + "@test.com")
                .phone(RandomString.make(5))
                .description(RandomString.make(50))
                .address(RandomString.make(20))
                .company(CompanyCreator.createCompanyEntity())
                .isActive(isActive)
                .build();
    }
    public static SupplierOfProductDto dto(){
        Byte isActive = (byte) (Math.random() * 2);
        return SupplierOfProductDto.builder()
                .name(RandomString.make(7))
                .surname(RandomString.make(10))
                .email(RandomString.make(5) + "@test.com")
                .phone(RandomString.make(5))
                .description(RandomString.make(50))
                .address(RandomString.make(20))
                .company(CompanyCreator.createCompanyEntity())
                .isActive(isActive)
                .build();
    }

    public static List<SupplierOfProductDto> dtoList(){
        return Stream.generate(SupplierOFProductCreator::dto)
                .limit(3)
                .toList();
    }

    public static List<SupplierOfProduct> entityList(){
        return Stream.generate(SupplierOFProductCreator::entity)
                .limit(3)
                .toList();
    }




}
