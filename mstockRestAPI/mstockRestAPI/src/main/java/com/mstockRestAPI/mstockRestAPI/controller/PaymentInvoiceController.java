package com.mstockRestAPI.mstockRestAPI.controller;

import com.mstockRestAPI.mstockRestAPI.dto.PaymentInvoiceDto;
import com.mstockRestAPI.mstockRestAPI.service.PaymentInvoiceService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/paymentInvoice")
public class PaymentInvoiceController {

    private final PaymentInvoiceService paymentInvoiceService;

    @Autowired
    public PaymentInvoiceController(PaymentInvoiceService paymentInvoiceService) {
        this.paymentInvoiceService = paymentInvoiceService;
    }

    @PostMapping
    public ResponseEntity<PaymentInvoiceDto> add(@Valid @RequestBody PaymentInvoiceDto paymentInvoiceDto){
        return new ResponseEntity<>(paymentInvoiceService.add(paymentInvoiceDto), HttpStatus.CREATED);
    }

}
