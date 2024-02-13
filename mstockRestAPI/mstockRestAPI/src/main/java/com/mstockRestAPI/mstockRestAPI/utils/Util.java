package com.mstockRestAPI.mstockRestAPI.utils;

import com.mstockRestAPI.mstockRestAPI.exception.FileUploadException;
import org.apache.tika.Tika;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
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

    public static boolean isValidBankAccountNumber(String bankAccountNumber){
        String regex = "[0-9]+";

        return ((bankAccountNumber == null || bankAccountNumber.isEmpty()) ||
                (bankAccountNumber.matches(regex) && bankAccountNumber.length() == 16));

    }

    public static String makeBankCardNumberBeautiful(String bankAccountNumber){
        StringBuilder beautifulBankAccountNumber = new StringBuilder();

        for(int i = 0; i < bankAccountNumber.length(); i++){
            beautifulBankAccountNumber.append(bankAccountNumber.charAt(i));
            if( i+1 != 16 && (i+1) % 4 == 0 )
                beautifulBankAccountNumber.append("-");
        }

        return beautifulBankAccountNumber.toString();
    }



    public static long generateReceiptNumber() {
        LocalDateTime now = LocalDateTime.now();

        int year = now.getYear() % 100; // Last two digits of the year
        int month = now.getMonthValue(); // Month
        int day = now.getDayOfMonth(); // Day
        int hour = now.getHour(); // Hour
        int minute = now.getMinute(); // Minute
        int second = now.getSecond(); // Second
        int millisecond = now.getNano() / 1000000; // Millisecond

        return (year * 1000000000000L) + (month * 10000000000L) + (day * 100000000L) +
                (hour * 1000000) + (minute * 10000) + (second * 100) + millisecond;
    }
}
