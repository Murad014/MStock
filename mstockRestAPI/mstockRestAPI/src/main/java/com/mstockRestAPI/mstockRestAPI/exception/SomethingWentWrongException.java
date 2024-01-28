package com.mstockRestAPI.mstockRestAPI.exception;

public class SomethingWentWrongException extends RuntimeException{
    public SomethingWentWrongException(String message){
        super(message);
    }
}
