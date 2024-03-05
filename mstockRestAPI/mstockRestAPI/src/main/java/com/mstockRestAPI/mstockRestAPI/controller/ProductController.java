package com.mstockRestAPI.mstockRestAPI.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mstockRestAPI.mstockRestAPI.dto.ProductDto;
import com.mstockRestAPI.mstockRestAPI.entity.Product;
import com.mstockRestAPI.mstockRestAPI.exception.SqlProcessException;
import com.mstockRestAPI.mstockRestAPI.payload.ImageFileList;
import com.mstockRestAPI.mstockRestAPI.payload.request.ProductSearchKeys;
import com.mstockRestAPI.mstockRestAPI.payload.response.ProductResponse;
import com.mstockRestAPI.mstockRestAPI.payload.response.SuccessResponse;
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
import static com.mstockRestAPI.mstockRestAPI.cons.Constans.*;
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

    @PutMapping("{productId}")
    public ResponseEntity<ProductDto> update(
            @PathVariable("productId") Long productId,
            @Valid @RequestBody ProductDto productDto
    ) throws SqlProcessException {
        ProductDto saveProduct = productService.update(productId, productDto);
        return new ResponseEntity<>(
                saveProduct,
                HttpStatus.OK);
    }

    @PutMapping("{productId}/isActive/{isActive}")
    public ResponseEntity<SuccessResponse> updateById(
            @PathVariable("productId") Long productId,
            @PathVariable("isActive") Byte isActive
    ) throws SqlProcessException {
        return new ResponseEntity<>(
                productService.deActiveById(productId, isActive),
                HttpStatus.OK
                );
    }

    @GetMapping
    public ResponseEntity<List<ProductDto>> getAll(){ // Temporarily was not written pagination and sort
        return new ResponseEntity<>(
                productService.getAll(),
                HttpStatus.OK
        );
    }

    @GetMapping("search")
    public ResponseEntity<ProductResponse> searchProduct(
            @RequestParam(value= "pageNo", defaultValue = DEFAULT_PAGE_NUMBER, required = false) int pageNo,
            @RequestParam(value="pageSize", defaultValue = DEFAULT_PAGE_SIZE, required = false) int pageSize,
            @RequestParam(value="sortBy", defaultValue = DEFAULT_SORT_BY, required = false) String sortBy,
            @RequestParam(value="sortDir", defaultValue = DEFAULT_SORT_DIRECTION, required = false) String sortDir,
            @RequestParam(value = "barcode", required = false) String barcode,
            @RequestParam(value = "productName", required = false) String productName,
            @RequestParam(value = "category", required = false) String category,
            @RequestParam(value = "isActive", required = false) String isActive
    ){
        ProductSearchKeys productSearchKeys = ProductSearchKeys
                .builder()
                .pageNo(pageNo)
                .pageSize(pageSize)
                .sortBy(sortBy)
                .sortDir(sortDir)
                .barcode(barcode)
                .productName(productName)
                .category(category)
                .isActive(isActive)
                .build();

        return new ResponseEntity<>(productService.searchAndPagination(productSearchKeys), HttpStatus.OK);
    }

    @GetMapping("active/{isActive}")
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
