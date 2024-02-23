package com.mstockRestAPI.mstockRestAPI.controller;

import com.mstockRestAPI.mstockRestAPI.dto.ProductMovementListDto;
import com.mstockRestAPI.mstockRestAPI.dto.SaleReceiptDto;
import com.mstockRestAPI.mstockRestAPI.service.ProductMovementsService;
import org.apache.coyote.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/productMovements")
public class ProductMovementsController {

    private final ProductMovementsService productMovementsService;

    @Autowired
    public ProductMovementsController(ProductMovementsService productMovementsService){
        this.productMovementsService = productMovementsService;
    }

    @PostMapping("/saleOrReturn")
    public ResponseEntity<SaleReceiptDto> saleOrReturn(
            @RequestBody ProductMovementListDto productMovementListDto
    ){
        SaleReceiptDto saleReceiptDto =
                productMovementsService.addAllSalesOrReturnsProducts(productMovementListDto);

        return new ResponseEntity<>(saleReceiptDto, HttpStatus.CREATED);
    }
}
