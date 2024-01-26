package com.mstockRestAPI.mstockRestAPI.validation.impl;

import com.mstockRestAPI.mstockRestAPI.validation.ValidImageFile;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.springframework.web.multipart.MultipartFile;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;

public class ValidImageFileValidator implements ConstraintValidator<ValidImageFile, MultipartFile> {

    private static final List<String> ALLOWED_EXTENSIONS = Arrays.asList("jpg", "jpeg", "png");

    @Override
    public void initialize(ValidImageFile constraintAnnotation) {
    }

    @Override
    public boolean isValid(MultipartFile file, ConstraintValidatorContext context) {
        if (file == null || file.isEmpty()) {
            return true; // Not required, so empty file is considered valid
        }

        String fileExtension = getFileExtension(Objects.requireNonNull(file.getOriginalFilename()));
        return fileExtension != null && ALLOWED_EXTENSIONS.contains(fileExtension.toLowerCase());
    }

    private String getFileExtension(String fileName) {
        int dotIndex = fileName.lastIndexOf('.');
        return (dotIndex == -1) ? null : fileName.substring(dotIndex + 1);
    }
}