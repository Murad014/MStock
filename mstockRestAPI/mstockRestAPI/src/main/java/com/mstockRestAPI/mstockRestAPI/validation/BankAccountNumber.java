package com.mstockRestAPI.mstockRestAPI.validation;

import com.mstockRestAPI.mstockRestAPI.validation.impl.BankAccountNumberValidator;
import com.mstockRestAPI.mstockRestAPI.validation.impl.UniqueBarcodeValidator;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ ElementType.FIELD })
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = BankAccountNumberValidator.class)
public @interface BankAccountNumber {
    String message() default "Invalid Bank Account Number.";
    boolean isUnique() default false;
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}