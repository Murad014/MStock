package com.mstockRestAPI.mstockRestAPI.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.sql.SQLException;

@Getter
@ResponseStatus(value = HttpStatus.BAD_REQUEST)
public class SqlProcessException extends SQLException {

    private final String entity;
    private final String processName;
    private final String reason;

    public SqlProcessException(String entity, String processName, String reason) {
        super(String.format("Error occurred in %s entity. Process: %s, Reason: %s.",
                entity, processName, reason));
        this.entity = entity;
        this.processName = processName;
        this.reason = reason;
    }

}