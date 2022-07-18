package com.example.air.request;


import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

public class FlightRequest {

    @NotEmpty(message = "notEmpty")
    @NotNull(message = "notNull")
    private String departureCountry;

    @NotEmpty(message = "notEmpty")
    @NotNull(message = "notNull")
    private String destinationCountry;

    @NotEmpty(message = "notEmpty")
    @NotNull(message = "notNull")
    private double distance;

    @NotEmpty(message = "notEmpty")
    @NotNull(message = "notNull")
    private LocalDateTime estimatedFlightTime;

    public FlightRequest() {

    }

    public String getDepartureCountry() {
        return departureCountry;
    }

    public void setDepartureCountry(String departureCountry) {
        this.departureCountry = departureCountry;
    }

    public String getDestinationCountry() {
        return destinationCountry;
    }

    public void setDestinationCountry(String destinationCountry) {
        this.destinationCountry = destinationCountry;
    }

    public double getDistance() {
        return distance;
    }

    public void setDistance(double distance) {
        this.distance = distance;
    }

    public LocalDateTime getEstimatedFlightTime() {
        return estimatedFlightTime;
    }

    public void setEstimatedFlightTime(LocalDateTime estimatedFlightTime) {
        this.estimatedFlightTime = estimatedFlightTime;
    }
}
