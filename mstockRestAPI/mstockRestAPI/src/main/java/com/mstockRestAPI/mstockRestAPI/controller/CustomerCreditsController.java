package com.mstockRestAPI.mstockRestAPI.controller;

import com.mstockRestAPI.mstockRestAPI.dto.CreditOfCustomersDto;
import com.mstockRestAPI.mstockRestAPI.service.CreditsOfCustomersService;
import jakarta.validation.Valid;
import org.apache.coyote.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/credits")
public class CustomerCreditsController {
    private final CreditsOfCustomersService creditsOfCustomersService;

    @Autowired
    public CustomerCreditsController(CreditsOfCustomersService customersService){
        this.creditsOfCustomersService = customersService;
    }

    @GetMapping("/{creditId}")
    public ResponseEntity<CreditOfCustomersDto> fetchById(
            @PathVariable("creditId") Long creditId
    ){
        CreditOfCustomersDto creditFromDB = creditsOfCustomersService.fetchById(creditId);
        return new ResponseEntity<>(creditFromDB, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<CreditOfCustomersDto> addByIdCardNumber(
            @Valid @RequestParam("customerIdCardNumber") String customerIdCardNumber,
            @RequestBody CreditOfCustomersDto credit){

        CreditOfCustomersDto added =
                creditsOfCustomersService.addByCustomerIdCardNumber(credit, customerIdCardNumber);
        return new ResponseEntity<>(added, HttpStatus.CREATED);
    }

    @PutMapping("/{creditId}")
    public ResponseEntity<Boolean> closeCreditByCreditId(@PathVariable("creditId") Long creditId){
        return new ResponseEntity<>(creditsOfCustomersService.closeCreditByCustomerIdCardNumberAndCreditId(creditId),
                HttpStatus.OK);
    }



}