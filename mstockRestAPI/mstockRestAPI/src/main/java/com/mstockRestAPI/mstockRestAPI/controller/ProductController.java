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
                HttpStatus.CREATED);
    }



    @GetMapping
    public ResponseEntity<List<ProductDto>> getAll(){
        return new ResponseEntity<>(
                productService.getAll(),
                HttpStatus.OK
        );
    }



}
