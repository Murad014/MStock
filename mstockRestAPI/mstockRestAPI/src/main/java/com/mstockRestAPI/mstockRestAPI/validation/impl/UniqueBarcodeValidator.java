package com.mstockRestAPI.mstockRestAPI.validation.impl;

import com.mstockRestAPI.mstockRestAPI.service.ProductBarcodeService;
import com.mstockRestAPI.mstockRestAPI.validation.UniqueBarcode;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.springframework.beans.factory.annotation.Autowired;


public class UniqueBarcodeValidator implements ConstraintValidator<UniqueBarcode, String> {
    private final ProductBarcodeService productBarcodeService;

    public UniqueBarcodeValidator(ProductBarcodeService productBarcodeService){
        this.productBarcodeService = productBarcodeService;
    }

    @Override
    public void initialize(UniqueBarcode constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
    }

    @Override
    public boolean isValid(String barcode, ConstraintValidatorContext constraintValidatorContext) {
        boolean existSameBarcode = productBarcodeService.existsByBarcode(barcode);
        return !existSameBarcode;
    }
}
