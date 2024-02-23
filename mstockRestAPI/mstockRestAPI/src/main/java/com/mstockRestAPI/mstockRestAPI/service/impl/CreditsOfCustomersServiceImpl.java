package com.mstockRestAPI.mstockRestAPI.service.impl;

import com.mstockRestAPI.mstockRestAPI.dto.CreditOfCustomersDto;
import com.mstockRestAPI.mstockRestAPI.dto.CustomerDto;
import com.mstockRestAPI.mstockRestAPI.entity.CreditOfCustomers;
import com.mstockRestAPI.mstockRestAPI.entity.Customer;
import com.mstockRestAPI.mstockRestAPI.exception.ResourceNotFoundException;
import com.mstockRestAPI.mstockRestAPI.payload.converter.Converter;
import com.mstockRestAPI.mstockRestAPI.repository.CreditsOfCustomersRepository;
import com.mstockRestAPI.mstockRestAPI.repository.CustomerRepository;
import com.mstockRestAPI.mstockRestAPI.service.CreditsOfCustomersService;
import jakarta.persistence.Convert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CreditsOfCustomersServiceImpl implements CreditsOfCustomersService {
    private final CreditsOfCustomersRepository creditsOfCustomersRepository;
    private final CustomerRepository customerRepository;
    private final Converter converter;

    @Autowired
    public CreditsOfCustomersServiceImpl(CreditsOfCustomersRepository creditsOfCustomersRepository,
                                         CustomerRepository customerRepository,
                                         Converter converter){
        this.creditsOfCustomersRepository = creditsOfCustomersRepository;
        this.customerRepository = customerRepository;
        this.converter = converter;
    }
    @Override
    public CreditOfCustomersDto addByCustomerIdCardNumber(CreditOfCustomersDto creditOfCustomersDto,
                                                          String customerIdCardNumber) {
        // Convert to Entity
        CreditOfCustomers creditOfCustomersEntity = converter.mapToEntity(creditOfCustomersDto,
                CreditOfCustomers.class);

        // Find Customer first
        Customer customerFromDB = customerRepository.findByIdCardNumber(customerIdCardNumber);
        if(customerFromDB == null)
            throw new ResourceNotFoundException("Customer", "idCardNumber", customerIdCardNumber);

        // Then Find, so You are able to add credit after set customer
        creditOfCustomersEntity.setCustomer(customerFromDB);
        CreditOfCustomers saved = creditsOfCustomersRepository.save(creditOfCustomersEntity);

        return converter.mapToDto(saved, CreditOfCustomersDto.class);
    }

    @Override
    public CreditOfCustomersDto addByCustomerId(CreditOfCustomersDto creditOfCustomersDto, Long customerId) {
        // Convert to Entity
        CreditOfCustomers creditOfCustomersEntity = converter.mapToEntity(creditOfCustomersDto,
                CreditOfCustomers.class);

        // Find Customer first
        Customer customerFromDB = customerRepository.findById(customerId).orElseThrow(
                () -> new ResourceNotFoundException("Customer", "id", customerId.toString())
        );

        // Then Find, so You are able to add credit after set customer
        creditOfCustomersEntity.setCustomer(customerFromDB);
        CreditOfCustomers saved = creditsOfCustomersRepository.save(creditOfCustomersEntity);

        return converter.mapToDto(saved, CreditOfCustomersDto.class);
    }



    @Override
    public Boolean closeCreditByCustomerIdCardNumberAndCreditId(Long creditId) {
        // Find Credit by id
        CreditOfCustomers findCredit = creditsOfCustomersRepository.findById(creditId)
                .orElseThrow(
                        () -> new ResourceNotFoundException("Credit", "id", creditId.toString())
                );

        // When Find then Set close
        findCredit.setClosed(true);

        // Then Save
        CreditOfCustomers savedClosedCredit = creditsOfCustomersRepository.save(findCredit);

        return true;
    }

    @Override
    public CreditOfCustomersDto fetchById(Long creditId) {
        CreditOfCustomers creditFromDB = creditsOfCustomersRepository.findById(creditId)
                .orElseThrow(() -> new ResourceNotFoundException("CreditOfCustomers", "id", creditId.toString()));

        return converter.mapToDto(creditFromDB, CreditOfCustomersDto.class);
    }


}
