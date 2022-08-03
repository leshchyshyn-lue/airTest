package com.example.air.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND)
public class AirplaneNotFoundException extends Exception{
    public AirplaneNotFoundException(String message){
        super(message);
    }
}
