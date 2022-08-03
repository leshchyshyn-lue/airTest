package com.example.air.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST)
public class AirplaneWithThisSerialNumberAlreadyExistsException extends Exception{
    public AirplaneWithThisSerialNumberAlreadyExistsException(String message){
        super(message);
    }
}
