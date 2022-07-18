package com.example.air.util;


import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND)
public class NoFlightWithThisStatus extends Exception {
    public NoFlightWithThisStatus(String message) {
        super(message);
    }
}
