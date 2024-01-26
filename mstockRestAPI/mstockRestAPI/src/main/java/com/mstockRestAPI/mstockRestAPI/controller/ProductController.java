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
    private final ObjectMapper objectMapper;

    @Autowired
    public ProductController(ProductService productService,
                             ObjectMapper objectMapper){
        this.objectMapper = objectMapper;
        this.productService = productService;
    }

    @PostMapping(/*consumes = {MediaType.MULTIPART_FORM_DATA_VALUE},
            produces = {MediaType.APPLICATION_JSON_VALUE}*/)
    public ResponseEntity<ProductDto> add(@Valid @ModelAttribute("productDto") ProductDto productDto,
                                          @Valid @RequestParam("productPicture") List<MultipartFile> productPictures
    ) throws JsonProcessingException, SqlProcessException {

        // ProductDto productDto = objectMapper.readValue(productDtoString, ProductDto.class);
        ProductDto saveProduct = productService.add(productDto, productPictures);

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
