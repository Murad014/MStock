package com.mstockRestAPI.mstockRestAPI.service.impl;

import com.mstockRestAPI.mstockRestAPI.dto.SupplierOfProductDto;
import com.mstockRestAPI.mstockRestAPI.entity.Company;
import com.mstockRestAPI.mstockRestAPI.entity.SupplierOfProduct;
import com.mstockRestAPI.mstockRestAPI.exception.ResourceNotFoundException;
import com.mstockRestAPI.mstockRestAPI.payload.converter.Converter;
import com.mstockRestAPI.mstockRestAPI.repository.CompanyRepository;
import com.mstockRestAPI.mstockRestAPI.repository.SupplierOfProductRepository;
import com.mstockRestAPI.mstockRestAPI.service.SupplierOfProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SupplierOfProductServiceImpl implements SupplierOfProductService {

    private final SupplierOfProductRepository supplierOfProductRepository;
    private final CompanyRepository companyRepository;
    private final Converter converter;

    @Autowired
    public SupplierOfProductServiceImpl(SupplierOfProductRepository supplierOfProductRepository,
                                        CompanyRepository companyRepository,
                                        Converter converter) {
        this.supplierOfProductRepository = supplierOfProductRepository;
        this.companyRepository = companyRepository;
        this.converter = converter;
    }


    @Override
    public SupplierOfProductDto add(SupplierOfProductDto supplierOfProductDto) {
        // Find company and Set
        Company companyFromDB = findCompanyById(supplierOfProductDto.getCompanyId());
        SupplierOfProduct supplier = converter.mapToEntity(supplierOfProductDto, SupplierOfProduct.class);
        supplier.setCompany(companyFromDB);

        // Save
        return converter.mapToDto(supplierOfProductRepository.save(supplier), SupplierOfProductDto.class);
    }

    @Override
    public SupplierOfProductDto update(Long supplierId, SupplierOfProductDto supplierOfProductDto) {
        // Find Supplier
        SupplierOfProduct supplierFromDB = supplierOfProductRepository.findById(supplierId)
                .orElseThrow(() -> new ResourceNotFoundException("SupplierOfProduct", "id", supplierId.toString()));

        // Find Company
        Company companyFromDB = findCompanyById(supplierOfProductDto.getCompanyId());

        supplierFromDB.setCompany(companyFromDB);
        supplierFromDB.setName(supplierOfProductDto.getName());
        supplierFromDB.setSurname(supplierOfProductDto.getSurname());
        supplierFromDB.setEmail(supplierOfProductDto.getEmail());
        supplierFromDB.setAddress(supplierOfProductDto.getAddress());
        supplierFromDB.setPhone(supplierOfProductDto.getPhone());
        supplierFromDB.setComment(supplierOfProductDto.getComment());
        supplierFromDB.setIsActive(supplierOfProductDto.getIsActive());


        return converter.mapToDto(supplierOfProductRepository.save(supplierFromDB),
                SupplierOfProductDto.class);
    }

    @Override
    public List<SupplierOfProductDto> fetchAll() {
        return supplierOfProductRepository.findAll().stream().map(
                supplierOfProduct -> converter.mapToDto(supplierOfProduct, SupplierOfProductDto.class)
        ).toList();
    }

    @Override
    public SupplierOfProductDto fetchById(Long supplierId) {
        SupplierOfProduct supplierFromDB = supplierOfProductRepository.findById(supplierId)
                .orElseThrow(() -> new ResourceNotFoundException("SupplierOfProduct", "id", supplierId.toString()));

        return converter.mapToDto(supplierFromDB, SupplierOfProductDto.class);
    }


/*                               PRIVATE METHODS                             */
    private Company findCompanyById(Long companyId){
        return companyRepository.findById(companyId).orElseThrow(
                () -> new ResourceNotFoundException("Company", "id", companyId.toString())
        );

    }


}
