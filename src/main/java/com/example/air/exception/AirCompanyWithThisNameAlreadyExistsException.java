package com.example.air.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;
@ResponseStatus(value = HttpStatus.BAD_REQUEST)
public class AirCompanyWithThisNameAlreadyExistsException extends Exception {
    public AirCompanyWithThisNameAlreadyExistsException(String message){
        super(message);
    }
}
