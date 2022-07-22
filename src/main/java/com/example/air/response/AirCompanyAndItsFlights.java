package com.example.air.response;

import com.example.air.entity.Flight;

import java.util.List;

public class AirCompanyAndItsFlights {

    private String name;

    private List<FlightResponseForAirConpanyAndFlights> flights;

    public AirCompanyAndItsFlights() {

    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<FlightResponseForAirConpanyAndFlights> getFlights() {
        return flights;
    }


    public void setFlights(List<FlightResponseForAirConpanyAndFlights> flights) {
        this.flights = flights;
    }
}
