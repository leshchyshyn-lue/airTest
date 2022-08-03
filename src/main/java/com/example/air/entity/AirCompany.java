package com.example.air.entity;


import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;


@Entity
@Table(name = "air_company")
public class AirCompany {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "air_company_name")
    private String name;

    @Column(name = "company_type")
    private String companyType;

    @Column(name = "founded_at")
    private LocalDateTime foundedAt;

    @JsonIgnore
    @OneToMany(mappedBy = "airCompany")
    private List<Airplane> airplanes;

    @JsonIgnore
    @OneToMany(mappedBy = "airCompany")
    private List<Flight> flightsAirCompany;


    public AirCompany() {

    }

    public List<Airplane> getAirplanes() {
        return airplanes;
    }

    public void setAirplanes(List<Airplane> airplanes) {
        this.airplanes = airplanes;
    }

    public List<Flight> getFlightsAirCompany() {
        return flightsAirCompany;
    }

    public void setFlightsAirCompany(List<Flight> flightsAirCompany) {
        this.flightsAirCompany = flightsAirCompany;
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

    public String getCompanyType() {
        return companyType;
    }

    public void setCompanyType(String companyType) {
        this.companyType = companyType;
    }

    public LocalDateTime getFoundedAt() {
        return foundedAt;
    }

    public void setFoundedAt(LocalDateTime foundedAt) {
        this.foundedAt = foundedAt;
    }
}



