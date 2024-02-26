package com.mstockRestAPI.mstockRestAPI.controller;

import com.mstockRestAPI.mstockRestAPI.dto.InvoiceDto;
import com.mstockRestAPI.mstockRestAPI.entity.Invoice;
import com.mstockRestAPI.mstockRestAPI.service.InvoiceService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/invoice")
public class InvoiceController {
    private final InvoiceService invoiceService;

    @Autowired
    public InvoiceController(InvoiceService invoiceService){
        this.invoiceService = invoiceService;
    }

    @PostMapping
    public ResponseEntity<InvoiceDto> add(@Valid @RequestBody InvoiceDto invoice){
        return new ResponseEntity<>(invoiceService.add(invoice), HttpStatus.CREATED);
    }

    @PutMapping("/{invoiceId}")
    public ResponseEntity<InvoiceDto> update(
            @PathVariable("invoiceId") Long invoiceId,
            @Valid @RequestBody InvoiceDto invoiceDto){

        return new ResponseEntity<>(
                invoiceService.update(invoiceId, invoiceDto),
                HttpStatus.OK
        );
    }

    @GetMapping
    public ResponseEntity<List<InvoiceDto>> fetchAll(){

        return new ResponseEntity<>(
                invoiceService.fetchAll(),
                HttpStatus.OK
        );
    }

    @GetMapping("/isActive/{isActive}")
    public ResponseEntity<List<InvoiceDto>> fetchByIsActive(@PathVariable("isActive") Byte isActive){
        return new ResponseEntity<>(
                invoiceService.fetchByIsActive(isActive),
                HttpStatus.OK);
    }

    @GetMapping("/invoiceId/{invoiceId}")
    public ResponseEntity<InvoiceDto> fetchByInvoiceId(@PathVariable("invoiceId") Long invoiceId){
        return new ResponseEntity<>(
                invoiceService.fetchById(invoiceId),
                HttpStatus.OK);
    }

    @GetMapping("/invoiceCode/{invoiceCode}")
    public ResponseEntity<InvoiceDto> fetchByInvoiceId(@PathVariable("invoiceCode") String invoiceCode){
        return new ResponseEntity<>(
                invoiceService.fetchByInvoiceCode(invoiceCode),
                HttpStatus.OK);
    }






}
