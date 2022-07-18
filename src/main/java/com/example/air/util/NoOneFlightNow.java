package com.example.air.util;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND)
public class NoOneFlightNow extends Exception {
    public NoOneFlightNow(String message) {
        super(message);
    }
}
