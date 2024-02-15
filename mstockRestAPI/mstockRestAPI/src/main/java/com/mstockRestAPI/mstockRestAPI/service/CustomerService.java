package com.mstockRestAPI.mstockRestAPI.service;

import com.mstockRestAPI.mstockRestAPI.dto.CustomerDto;
import com.mstockRestAPI.mstockRestAPI.entity.Customer;

import java.util.List;

public interface CustomerService {

    CustomerDto add(CustomerDto customerDto);
    CustomerDto update(Long customerId, CustomerDto customerDto);
    List<CustomerDto> getAll();
    CustomerDto getById(Long customerId);



}
