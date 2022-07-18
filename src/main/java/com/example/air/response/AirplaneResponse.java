package com.example.air.response;

import com.example.air.entity.AirCompany;

import java.time.LocalDateTime;

public class AirplaneResponse {
    private Long id;
    private String name;
    private Long factorySerialNumber;
    private Long numberOfFlights;
    private double flightDistance;
    private Long fuelCapacity;
    private String type;
    private LocalDateTime createdAt;
    private AirCompany airCompanyId;

    public AirplaneResponse() {

    }

    public AirCompany getAirCompanyId() {
        return airCompanyId;
    }

    public void setAirCompanyId(AirCompany airCompanyId) {
        this.airCompanyId = airCompanyId;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }
}
