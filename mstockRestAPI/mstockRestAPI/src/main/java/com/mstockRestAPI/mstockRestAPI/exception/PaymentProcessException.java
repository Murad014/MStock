package com.mstockRestAPI.mstockRestAPI.exception;


import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@Getter
@ResponseStatus(value = HttpStatus.BAD_REQUEST)
public class PaymentProcessException extends RuntimeException{

    private final String className;
    private final String paymentType;
    private final String message;

    public PaymentProcessException(String className, String paymentType, String message) {
        super(String.format("ClassName: %s, PaymentType: %s, Message: %s", className, paymentType, message));
        this.className = className;
        this.paymentType = paymentType;
        this.message = message;
    }

}