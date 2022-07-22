package com.example.air.exception;


import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND)
public class NoFlightWithThisStatusException extends Exception {
    public NoFlightWithThisStatusException(String message) {
        super(message);
    }
}
