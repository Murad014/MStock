package com.mstockRestAPI.mstockRestAPI.controller;

import com.mstockRestAPI.mstockRestAPI.dto.SupplierOfProductDto;
import com.mstockRestAPI.mstockRestAPI.service.SupplierOfProductService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.function.Supplier;

@RestController
@RequestMapping("/api/v1/supplierOfProduct")
public class SupplierOfProductController {

    private final SupplierOfProductService supplierOfProductService;

    public SupplierOfProductController(SupplierOfProductService supplierOfProductService) {
        this.supplierOfProductService = supplierOfProductService;
    }

    @PostMapping
    public ResponseEntity<SupplierOfProductDto> add(
            @Valid @RequestBody SupplierOfProductDto supplierOfProductDto){
        return new ResponseEntity<>(
                supplierOfProductService.add(supplierOfProductDto),
                HttpStatus.CREATED
        );
    }

    @PutMapping("/{id}")
    public ResponseEntity<SupplierOfProductDto> update(
            @PathVariable("id") Long id,
            @Valid @RequestBody SupplierOfProductDto supplierOfProductDto){

        return new ResponseEntity<>(
                supplierOfProductService.update(id, supplierOfProductDto),
                HttpStatus.OK
        );
    }
    @GetMapping
    public ResponseEntity<List<SupplierOfProductDto>> fetchAll(){

        return new ResponseEntity<>(
                supplierOfProductService.fetchAll(),
                HttpStatus.OK
        );
    }

    @GetMapping("/{id}")
    public ResponseEntity<SupplierOfProductDto> fetchById(@PathVariable("id") Long id){

        return new ResponseEntity<>(
                supplierOfProductService.fetchById(id),
                HttpStatus.OK
        );
    }


}
