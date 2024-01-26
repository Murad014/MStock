package com.mstockRestAPI.mstockRestAPI.utils;

import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

public class Util {
    public static String handleFileUpload(MultipartFile file, String uploadDirectory) {
        try {
            String originalFilename = file.getOriginalFilename();
            String uniqueFileName = UUID.randomUUID() + "_" + originalFilename;
            Path filePath = Paths.get(uploadDirectory, uniqueFileName);
            file.transferTo(filePath.toFile());

            return uniqueFileName;
        } catch (IOException e) {
            throw new RuntimeException("Failed to upload the file", e);
        }
    }
}
