package com.mstockRestAPI.mstockRestAPI.validation;

import com.mstockRestAPI.mstockRestAPI.validation.impl.ValidImageFileValidator;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = ValidImageFileValidator.class)
@Target({ElementType.FIELD, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
public @interface ValidImageFile {

    String message() default "Invalid image file. Only JPEG, JPG, and PNG files are allowed.";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}