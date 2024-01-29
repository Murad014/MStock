package com.mstockRestAPI.mstockRestAPI.validation;

import com.mstockRestAPI.mstockRestAPI.validation.impl.UniqueBarcodeValidator;
import com.mstockRestAPI.mstockRestAPI.validation.impl.UniqueCompanyNameValidator;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ ElementType.FIELD })
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = UniqueBarcodeValidator.class)
public @interface UniqueBarcode {
    String message() default "Already exist";
    boolean isUnique() default false;
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}