package com.mstockRestAPI.mstockRestAPI.controller;

import com.mstockRestAPI.mstockRestAPI.dto.BankCardAccountDto;
import com.mstockRestAPI.mstockRestAPI.entity.BankCardAccount;
import com.mstockRestAPI.mstockRestAPI.payload.response.SuccessResponse;
import com.mstockRestAPI.mstockRestAPI.service.BankCardAccountService;
import jakarta.validation.Valid;
import org.apache.coyote.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/bankCardAccount")
public class BankCardAccountController {

    public final BankCardAccountService bankCardAccountService;

    @Autowired
    public BankCardAccountController(
            BankCardAccountService bankCardAccountService
    ){
        this.bankCardAccountService = bankCardAccountService;
    }

    @PostMapping
    public ResponseEntity<BankCardAccountDto> add(
            @Valid @RequestBody BankCardAccountDto bankCardAccountDto){

        return new ResponseEntity<>(
                bankCardAccountService.add(bankCardAccountDto),
                HttpStatus.CREATED
        );

    }

    @PutMapping("/bankCardAccountNumber/{accountNumber}")
    public ResponseEntity<SuccessResponse> makeDeActive(
            @PathVariable("accountNumber") String bankAccountNumber
            ){

        bankCardAccountService.makeDeActive(bankAccountNumber);

        return new ResponseEntity<>(
                SuccessResponse.builder().message("true").build(),
                HttpStatus.OK
        );

    }

}
