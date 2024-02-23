package com.mstockRestAPI.mstockRestAPI.service.impl;

import com.mstockRestAPI.mstockRestAPI.dto.CreditOfCustomersDto;
import com.mstockRestAPI.mstockRestAPI.dto.CustomerDto;
import com.mstockRestAPI.mstockRestAPI.entity.CreditOfCustomers;
import com.mstockRestAPI.mstockRestAPI.entity.Customer;
import com.mstockRestAPI.mstockRestAPI.exception.ResourceNotFoundException;
import com.mstockRestAPI.mstockRestAPI.payload.converter.Converter;
import com.mstockRestAPI.mstockRestAPI.repository.CustomerRepository;
import com.mstockRestAPI.mstockRestAPI.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CustomerServiceImpl implements CustomerService {

    private final CustomerRepository customerRepository;
    private final Converter converter;

    @Autowired
    public CustomerServiceImpl(
            CustomerRepository customerRepository,
            Converter converter
    ){
        this.customerRepository = customerRepository;
        this.converter = converter;
    }

    @Override
    public CustomerDto add(CustomerDto customerDto) {
        Customer saved = customerRepository.save(
                converter.mapToEntity(customerDto, Customer.class)
        );
        return converter.mapToDto(
                saved,
                CustomerDto.class
        );
    }

    @Override
    public CustomerDto update(Long customerId, CustomerDto customerDto) {
        // First check exists in DB
        Customer findCustomer = customerRepository.findById(customerId).orElseThrow(
                () -> new ResourceNotFoundException("Customer", "Id", customerId.toString()
        ));

        customerDto.setId(customerId);
        // Update
        Customer updatedCustomer = customerRepository.save(
                converter.mapToEntity(customerDto, Customer.class)
        );

        return converter.mapToDto(updatedCustomer, CustomerDto.class);
    }

    @Override
    public List<CustomerDto> getAll() {
        List<Customer> fetchAllCustomers = customerRepository.findAll();

        return fetchAllCustomers.stream().map(customer ->
                converter.mapToDto(customer, CustomerDto.class))
                .toList();
    }

    @Override
    public CustomerDto getById(Long customerId) {
        Customer findCustomer = customerRepository.findById(customerId).orElseThrow(
                () -> new ResourceNotFoundException("Customer", "Id", customerId.toString()
                ));

        return converter.mapToDto(findCustomer, CustomerDto.class);
    }

    @Override
    public List<CreditOfCustomersDto> getCreditsByCustomerId(Long customerId) {
        // Fetch
        Customer customerFromDB = customerRepository.findById(customerId)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Customer", "Id", customerId.toString()));

        return customerFromDB.getCredits().stream().map(
                credit ->
                        converter.mapToDto(credit, CreditOfCustomersDto.class)
        ).toList();
    }

    @Override
    public CustomerDto getCustomerByIdCardNumber(String idCardNumber) {
        // Fetch
        Customer customerFromDB = customerRepository.findByIdCardNumber(idCardNumber);
        if(customerFromDB == null)
            throw new ResourceNotFoundException("Customer", "idCardNumber", idCardNumber);

        return converter.mapToDto(customerFromDB, CustomerDto.class);
    }

    @Override
    public CustomerDto addCreditByIdCardNumber(String idCardNumber, CreditOfCustomersDto credit) {
        return null;
    }


}
