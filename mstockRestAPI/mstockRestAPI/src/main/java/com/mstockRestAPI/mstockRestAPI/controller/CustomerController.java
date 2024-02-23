package com.mstockRestAPI.mstockRestAPI.controller;

import com.mstockRestAPI.mstockRestAPI.dto.CustomerDto;
import com.mstockRestAPI.mstockRestAPI.entity.Customer;
import com.mstockRestAPI.mstockRestAPI.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/customer")
public class CustomerController {
    private final CustomerService customerService;

    @Autowired
    public CustomerController(CustomerService customerService){
        this.customerService = customerService;
    }

    @GetMapping
    public ResponseEntity<List<CustomerDto>> getAll(){
        return new ResponseEntity<>(
                customerService.getAll(),
                HttpStatus.OK
        );
    }

    @PostMapping
    public ResponseEntity<CustomerDto> add(CustomerDto customerDto){
        CustomerDto customerSave = customerService.add(customerDto);
        return new ResponseEntity<>(
                customerSave,
                HttpStatus.CREATED
        );
    }

    @PutMapping("/{id}")
    public ResponseEntity<CustomerDto> update(@PathVariable("id") Long customerId,
                                              CustomerDto customerDto){
        CustomerDto updateCustomer = customerService.update(customerId, customerDto);
        return new ResponseEntity<>(
                updateCustomer,
                HttpStatus.OK
        );
    }

    @GetMapping("/{id}")
    public ResponseEntity<CustomerDto> getById(@PathVariable("id") Long customerId){
        return new ResponseEntity<>(
                customerService.getById(customerId),
                HttpStatus.OK
        );
    }


    @GetMapping("/idCardNumber/{idCardNumber}")
    public ResponseEntity<CustomerDto> getByCustomerId(@PathVariable("idCardNumber") String idCardNumber){
        return new ResponseEntity<>(
                customerService.getCustomerByIdCardNumber(idCardNumber),
                HttpStatus.OK
        );
    }


}
