package com.example.air.util;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND)
public class ThisCompanyHasNoFlightsWithThisStatus extends Exception{
    public ThisCompanyHasNoFlightsWithThisStatus(String message){
        super(message);
    }
}
