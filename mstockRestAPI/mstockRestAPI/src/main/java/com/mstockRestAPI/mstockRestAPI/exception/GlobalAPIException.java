package com.mstockRestAPI.mstockRestAPI.exception;

import com.mstockRestAPI.mstockRestAPI.payload.response.ErrorDetails;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

import java.sql.SQLException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
public class GlobalAPIException {

    @ExceptionHandler(SomethingWentWrongException.class)
    public ResponseEntity<ErrorDetails> handleResourceNotFoundException(SomethingWentWrongException exception,
                                                                        WebRequest webRequest){
        ErrorDetails errorDetails = new ErrorDetails(
                new Date(),
                exception.getMessage(),
                webRequest.getDescription(false)
        );

        return new ResponseEntity<>(
                errorDetails,
                HttpStatus.BAD_REQUEST
        );
    }

    @ExceptionHandler(IllegalStateException.class)
    public ResponseEntity<ErrorDetails> handleIllegalStateException(IllegalStateException exception,
                                                                        WebRequest webRequest){
        ErrorDetails errorDetails = new ErrorDetails(
                new Date(),
                exception.getMessage(),
                webRequest.getDescription(false)
        );

        return new ResponseEntity<>(
                errorDetails,
                HttpStatus.BAD_REQUEST
        );
    }

    @ExceptionHandler(FileUploadException.class)
    public ResponseEntity<ErrorDetails> handleResourceNotFoundException(FileUploadException exception,
                                                                        WebRequest webRequest){
        ErrorDetails errorDetails = new ErrorDetails(
                new Date(),
                exception.getMessage(),
                webRequest.getDescription(false)
        );

        return new ResponseEntity<ErrorDetails>(
                errorDetails,
                HttpStatus.BAD_REQUEST
        );
    }

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ErrorDetails> handleResourceNotFoundException(ResourceNotFoundException exception,
                                                                        WebRequest webRequest){
        ErrorDetails errorDetails = new ErrorDetails(
                new Date(),
                exception.getMessage(),
                webRequest.getDescription(false)
        );

        return new ResponseEntity<ErrorDetails>(
                errorDetails,
                HttpStatus.NOT_FOUND
        );
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Object> handleMethodArgumentNotValidException(MethodArgumentNotValidException exception,
                                                                        WebRequest webRequest){
        Map<String, String> errors = new HashMap<>();
        exception.getBindingResult().getAllErrors().forEach((error) -> {
            String fieldName = ((FieldError)error).getField();
            String message = error.getDefaultMessage();
            errors.put(fieldName, message);
        });

        return new ResponseEntity<>(
                errors,
                HttpStatus.BAD_REQUEST
        );
    }

    @ExceptionHandler(SQLException.class)
    public ResponseEntity<Object> handleSQLException(SQLException exception, WebRequest webRequest) {
        String staticMessage = "Error occurred in SQL process";
        ErrorDetails errorDetails = new ErrorDetails(
                new Date(),
                staticMessage,
                webRequest.getDescription(false)
        );

        return new ResponseEntity<>(
                errorDetails,
                HttpStatus.BAD_REQUEST
        );
    }

    @ExceptionHandler(SqlProcessException.class)
    public ResponseEntity<Object> handleSQLException(SqlProcessException exception, WebRequest webRequest) {
        String message = exception.getMessage();
        ErrorDetails errorDetails = new ErrorDetails(
                new Date(),
                message,
                webRequest.getDescription(false)
        );

        return new ResponseEntity<>(
                errorDetails,
                HttpStatus.BAD_REQUEST
        );
    }
}