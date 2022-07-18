package com.example.air.util;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST)
public class AirplaneWithThisSerialNumberAlreadyExists extends Exception{
    public AirplaneWithThisSerialNumberAlreadyExists(String message){
        super(message);
    }
}
