package com.example.air.util;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;
@ResponseStatus(value = HttpStatus.BAD_REQUEST)
public class AirCompanyWithThisNameAlreadyExists extends Exception {
    public AirCompanyWithThisNameAlreadyExists(String message){
        super(message);
    }
}
