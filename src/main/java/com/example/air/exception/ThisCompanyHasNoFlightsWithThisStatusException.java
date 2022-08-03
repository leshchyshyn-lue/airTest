package com.example.air.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND)
public class ThisCompanyHasNoFlightsWithThisStatusException extends Exception{
    public ThisCompanyHasNoFlightsWithThisStatusException(String message){
        super(message);
    }
}
