package com.mstockRestAPI.mstockRestAPI.utils;

import com.mstockRestAPI.mstockRestAPI.exception.FileUploadException;
import org.apache.tika.Tika;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;
@Component
public class Util {
    public String handleFileUpload(MultipartFile file,
                                          String uploadDirectory,
                                          String specPref) {
        try {
            String originalFilename = file.getOriginalFilename();
            String uniqueFileName = specPref + "_" + UUID.randomUUID() + "_" + originalFilename;
            Path filePath = Paths.get(uploadDirectory, uniqueFileName);
            file.transferTo(filePath.toFile());

            return uniqueFileName;
        } catch (IOException e) {
            throw new FileUploadException(file.getOriginalFilename(), "Unknown");
        }
    }


    public boolean isImageFile(MultipartFile file) {
        try {

            Tika tika = new Tika();
            String contentType = tika.detect(file.getBytes());

            return contentType != null
                    && contentType.startsWith("image/");
        } catch (IOException e) {
            throw new FileUploadException(file.getOriginalFilename(), "Unknown");

        }
    }
}
