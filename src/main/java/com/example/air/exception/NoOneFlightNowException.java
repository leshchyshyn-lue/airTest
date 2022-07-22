package com.example.air.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND)
public class NoOneFlightNowException extends Exception {
    public NoOneFlightNowException(String message) {
        super(message);
    }
}
