package com.mstockRestAPI.mstockRestAPI.controller;

import com.mstockRestAPI.mstockRestAPI.dto.InvoiceItemDto;
import com.mstockRestAPI.mstockRestAPI.service.InvoiceItemService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/invoiceItem")
public class InvoiceItemController {

    private final InvoiceItemService invoiceItemService;

    @Autowired
    public InvoiceItemController(InvoiceItemService invoiceItemService){
        this.invoiceItemService = invoiceItemService;
    }


    @PostMapping("/bulkAdd")
    public ResponseEntity<List<InvoiceItemDto>> addAll(@Valid @RequestBody List<InvoiceItemDto> invoiceItemDtoList){
        return new ResponseEntity<>(
                invoiceItemService.addAll(invoiceItemDtoList),
                HttpStatus.CREATED
        );
    }

    @PostMapping
    public ResponseEntity<InvoiceItemDto> add(@Valid @RequestBody InvoiceItemDto invoiceItemDto){
        return new ResponseEntity<>(
                invoiceItemService.add(invoiceItemDto),
                HttpStatus.CREATED
        );
    }

    @PutMapping("/{invoiceItemId}")
    public ResponseEntity<InvoiceItemDto> updateById(@PathVariable("invoiceItemId") Long id,
                                                     @Valid @RequestBody InvoiceItemDto invoiceItemDto){

        return new ResponseEntity<>(
                invoiceItemService.updateById(id,invoiceItemDto),
                HttpStatus.OK
        );
    }

    @PutMapping("/{invoiceItemId}/deActive")
    public ResponseEntity<Boolean> makeDeActive(@PathVariable("invoiceItemId") Long id){

        return new ResponseEntity<>(
                invoiceItemService.makeDeActiveById(id),
                HttpStatus.OK
        );
    }

}
