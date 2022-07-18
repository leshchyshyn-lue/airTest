package com.example.air.util;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND)
public class AirCompanyNotFoundException extends Exception{
    public AirCompanyNotFoundException(String message){
        super(message);
    }
}
