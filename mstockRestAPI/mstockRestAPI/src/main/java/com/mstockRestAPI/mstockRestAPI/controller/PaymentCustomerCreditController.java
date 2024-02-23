package com.mstockRestAPI.mstockRestAPI.controller;

import com.mstockRestAPI.mstockRestAPI.dto.PaymentCustomerCreditDto;
import com.mstockRestAPI.mstockRestAPI.entity.PaymentCustomerCredit;
import com.mstockRestAPI.mstockRestAPI.repository.PaymentCustomerCreditRepository;
import com.mstockRestAPI.mstockRestAPI.service.PaymentCustomerCreditService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/paymentCredit")
public class PaymentCustomerCreditController {

    private final PaymentCustomerCreditService paymentCustomerCreditService;

    @Autowired
    public PaymentCustomerCreditController(PaymentCustomerCreditService paymentCustomerCreditService){
        this.paymentCustomerCreditService = paymentCustomerCreditService;
    }

    @PostMapping("/credit/{creditId}")
    public ResponseEntity<PaymentCustomerCreditDto> add(
            @PathVariable("creditId") Long creditId,
            @RequestBody PaymentCustomerCreditDto paymentCustomerCreditDto
    ){

        return new ResponseEntity<>(
                paymentCustomerCreditService.add(creditId, paymentCustomerCreditDto),
                HttpStatus.CREATED
        );
    }
    




}
