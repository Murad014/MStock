package com.mstockRestAPI.mstockRestAPI.exception;

public class FileUploadException extends RuntimeException {
    private final String fileName;
    private final String reason;

    public FileUploadException(String fileName, String reason) {
        super(String.format("When try upload file `%s` error occurred. Reason: %s",
                fileName, reason));
        this.fileName = fileName;
        this.reason = reason;
    }
}
