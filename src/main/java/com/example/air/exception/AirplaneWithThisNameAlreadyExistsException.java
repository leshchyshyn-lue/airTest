package com.example.air.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST)
public class AirplaneWithThisNameAlreadyExistsException extends Exception{
    public AirplaneWithThisNameAlreadyExistsException(String message){
        super(message);
    }
}
