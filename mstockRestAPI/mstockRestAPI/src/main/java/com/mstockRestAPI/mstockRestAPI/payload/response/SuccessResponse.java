package com.mstockRestAPI.mstockRestAPI.payload.response;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

import java.time.LocalDateTime;

@Getter
@Builder
@ToString
public class SuccessResponse {
    private final String message;
    private final LocalDateTime localDateTime = LocalDateTime.now();
    public SuccessResponse(String message){
        this.message = message;
    }

}
