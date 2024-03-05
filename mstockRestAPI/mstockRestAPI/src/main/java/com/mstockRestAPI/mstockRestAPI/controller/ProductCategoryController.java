package com.mstockRestAPI.mstockRestAPI.controller;

import com.mstockRestAPI.mstockRestAPI.dto.ProductCategoryDto;
import com.mstockRestAPI.mstockRestAPI.payload.response.SuccessResponse;
import com.mstockRestAPI.mstockRestAPI.service.ProductCategoriesService;
import jakarta.validation.Valid;
import org.apache.coyote.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/productCategory/")
public class ProductCategoryController {

    private final ProductCategoriesService productCategoriesService;

    @Autowired
    public ProductCategoryController(ProductCategoriesService productCategoriesService){
        this.productCategoriesService = productCategoriesService;
    }

    @PostMapping()
    public ResponseEntity<ProductCategoryDto> add(@Valid @RequestBody ProductCategoryDto productCategoryDto){
        return new ResponseEntity<>(
                productCategoriesService.add(productCategoryDto),
                HttpStatus.CREATED);
    }

    @PutMapping("{id}")
    public ResponseEntity<ProductCategoryDto> update(@PathVariable("id") Long productId,
            @Valid @RequestBody ProductCategoryDto productCategoryDto){

        return new ResponseEntity<>(
                productCategoriesService.update(productId, productCategoryDto),
                HttpStatus.OK
                );
    }

    @GetMapping()
    public ResponseEntity<List<ProductCategoryDto>> getAll(){
        return new ResponseEntity<>(
                productCategoriesService.getAll(),
                HttpStatus.OK
        );
    }

    @GetMapping("{id}")
    public ResponseEntity<ProductCategoryDto> getById(@PathVariable("id") Long id){
        return new ResponseEntity<>(
                productCategoriesService.getById(id),
                HttpStatus.OK
        );
    }

    @GetMapping("categoryName/{categoryName}")
    public ResponseEntity<ProductCategoryDto> getByCategoryName(@PathVariable("categoryName") String categoryName){
        return new ResponseEntity<>(
                productCategoriesService.findByCategoryName(categoryName),
                HttpStatus.OK
        );

    }

    @PutMapping("{categoryId}/isActive/{isActive}")
    public ResponseEntity<SuccessResponse> updateCategoryIsActiveById(
            @PathVariable("categoryId") Long categoryId,
            @PathVariable("isActive") Byte isActive

    ){
        return new ResponseEntity<>(
                productCategoriesService.updateIsActiveById(categoryId, isActive),
                HttpStatus.OK
        );
    }

    @GetMapping("isActive/{isActive}")
    public ResponseEntity<List<ProductCategoryDto>> getWhereIsActive(@PathVariable Byte isActive){
        return new ResponseEntity<>(
                productCategoriesService.findByIsActive(isActive),
                HttpStatus.OK
        );
    }

}
