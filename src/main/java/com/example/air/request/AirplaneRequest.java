package com.example.air.request;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class AirplaneRequest {

    @NotEmpty(message = "notEmpty")
    @NotNull(message = "notNull")
    @Size(min = 3, max = 20, message = "Name should be between 3 and 20")
    private String name;

    @NotEmpty(message = "notEmpty")
    @NotNull(message = "notNull")
    private Long factorySerialNumber;

    @NotEmpty(message = "notEmpty")
    @NotNull(message = "notNull")
    private Long numberOfFlights;

    @NotEmpty(message = "notEmpty")
    @NotNull(message = "notNull")
    private double flightDistance;

    @NotEmpty(message = "notEmpty")
    @NotNull(message = "notNull")
    private Long fuelCapacity;

    @NotEmpty(message = "notEmpty")
    @NotNull(message = "notNull")
    private String type;

    public AirplaneRequest() {

    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getFactorySerialNumber() {
        return factorySerialNumber;
    }

    public void setFactorySerialNumber(Long factorySerialNumber) {
        this.factorySerialNumber = factorySerialNumber;
    }

    public Long getNumberOfFlights() {
        return numberOfFlights;
    }

    public void setNumberOfFlights(Long numberOfFlights) {
        this.numberOfFlights = numberOfFlights;
    }

    public double getFlightDistance() {
        return flightDistance;
    }

    public void setFlightDistance(double flightDistance) {
        this.flightDistance = flightDistance;
    }

    public Long getFuelCapacity() {
        return fuelCapacity;
    }

    public void setFuelCapacity(Long fuelCapacity) {
        this.fuelCapacity = fuelCapacity;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}

