package com.example.air.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "airplane")
public class Airplane {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "factory_serial_number")
    private Long factorySerialNumber;

    @Column(name = "number_of_flights")
    private Long numberOfFlights;

    @Column(name = "flight_distance")
    private double flightDistance;

    @Column(name = "fuel_capacity")
    private Long fuelCapacity;

    @Column(name = "type")
    private String type;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "air_company_id")
    private AirCompany airCompany;
    @JsonIgnore
    @OneToMany(mappedBy = "airplane")
    private List<Flight> flightsAirPlane;


    public Airplane() {

    }

    public List<Flight> getFlightsAirPlane() {
        return flightsAirPlane;
    }

    public void setFlightsAirPlane(List<Flight> flightsAirPlane) {
        this.flightsAirPlane = flightsAirPlane;
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

    public AirCompany getAirCompany() {
        return airCompany;
    }

    public void setAirCompany(AirCompany airCompany) {
        this.airCompany = airCompany;
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



