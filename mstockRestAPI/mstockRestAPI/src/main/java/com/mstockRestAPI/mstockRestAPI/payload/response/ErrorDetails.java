package com.mstockRestAPI.mstockRestAPI.payload.response;

import lombok.Getter;
import lombok.ToString;

import java.util.Date;

@Getter
@ToString
public class ErrorDetails {
    private final Date timestamp;
    private final String message;
    private final String details;

    public ErrorDetails(Date timestamp, String message, String details){
        this.timestamp = timestamp;
        this.message = message;
        this.details = details;
    }
}
