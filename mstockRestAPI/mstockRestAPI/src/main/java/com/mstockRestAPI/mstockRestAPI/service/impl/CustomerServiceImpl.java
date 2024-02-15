package com.mstockRestAPI.mstockRestAPI.service.impl;

import com.mstockRestAPI.mstockRestAPI.dto.CustomerDto;
import com.mstockRestAPI.mstockRestAPI.entity.Customer;
import com.mstockRestAPI.mstockRestAPI.exception.ResourceNotFoundException;
import com.mstockRestAPI.mstockRestAPI.payload.converter.Converter;
import com.mstockRestAPI.mstockRestAPI.repository.CustomerRepository;
import com.mstockRestAPI.mstockRestAPI.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

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
}
