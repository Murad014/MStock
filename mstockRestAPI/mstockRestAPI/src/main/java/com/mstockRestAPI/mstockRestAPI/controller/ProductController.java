package com.mstockRestAPI.mstockRestAPI.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mstockRestAPI.mstockRestAPI.dto.ProductDto;
import com.mstockRestAPI.mstockRestAPI.entity.Product;
import com.mstockRestAPI.mstockRestAPI.exception.SqlProcessException;
import com.mstockRestAPI.mstockRestAPI.payload.ImageFileList;
import com.mstockRestAPI.mstockRestAPI.service.ProductService;
import com.mstockRestAPI.mstockRestAPI.validation.ValidImageFile;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/api/v1/product/")
public class ProductController {

    private final ProductService productService;

    @Autowired
    public ProductController(ProductService productService){
        this.productService = productService;
    }

    @PostMapping
    public ResponseEntity<ProductDto> add(@Valid @RequestBody ProductDto productDto) throws SqlProcessException {
        ProductDto saveProduct = productService.add(productDto);
        return new ResponseEntity<>(
                saveProduct,
                HttpStatus.CREATED);
    }

    @PostMapping("{productId}/pictures")
    public ResponseEntity<ProductDto> addPictures(@PathVariable("productId") Long productId,
                                                  @RequestParam("pictures") List<MultipartFile> pictures){
        return new ResponseEntity<>(
                productService.addPictures(productId, pictures),
                HttpStatus.CREATED
        );
    }

    @PutMapping
    public ResponseEntity<ProductDto> update(@Valid @RequestBody ProductDto productDto) throws SqlProcessException {
        ProductDto saveProduct = productService.update(productDto);
        return new ResponseEntity<>(
                saveProduct,
                HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<List<ProductDto>> getAll(){ // Temporarily was not written pagination and sort
        return new ResponseEntity<>(
                productService.getAll(),
                HttpStatus.OK
        );
    }

    @GetMapping("/active/{isActive}")
    public ResponseEntity<List<ProductDto>> getByIActive(@PathVariable("isActive") byte isActive){
        return new ResponseEntity<>(
                productService.getAllAndIsActive(isActive),
                HttpStatus.OK
        );
    }

    @GetMapping("name/{productName}")
    public ResponseEntity<List<ProductDto>> getByProductNameAndIsActive(
            @PathVariable("productName") String productName,
            @RequestParam(value = "isActive", defaultValue = "1") Byte isActive
    ){
        return new ResponseEntity<>(
                productService.getListByProductNameAndIsActive(productName, isActive),
                HttpStatus.OK
        );
    }

    @GetMapping("{productBarcode}")
    public ResponseEntity<ProductDto> getByBarcode(@PathVariable("productBarcode") String productBarcode,
                                                   @RequestParam(defaultValue = "1") byte isActive){
        return new ResponseEntity<>(
                productService.getByBarcodeAndIsActive(productBarcode, isActive),
                HttpStatus.OK
        );
    }



}
