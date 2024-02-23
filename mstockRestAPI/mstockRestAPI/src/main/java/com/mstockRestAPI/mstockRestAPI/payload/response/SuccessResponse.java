package com.mstockRestAPI.mstockRestAPI.payload.response;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

@Getter
@Builder
@ToString
public class SuccessResponse {
    private final String message;

    public SuccessResponse(String message){
        this.message = message;

    }

}
