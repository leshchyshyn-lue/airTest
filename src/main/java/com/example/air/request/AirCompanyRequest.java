package com.example.air.request;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;

public class AirCompanyRequest {

    @NotEmpty(message = "notEmpty")
    @NotNull(message = "notNull")
    @Size(min = 3, max = 20, message = "Name should be between 3 and 20")
    private String airCompanyName;

    @NotEmpty(message = "notEmpty")
    @NotNull(message = "notNull")
    private String companyType;

    @NotEmpty(message = "notEmpty")
    @NotNull(message = "notNull")
    private LocalDateTime foundedAt;

    public AirCompanyRequest() {

    }

    public String getAirCompanyName() {
        return airCompanyName;
    }

    public void setAirCompanyName(String airCompanyName) {
        this.airCompanyName = airCompanyName;
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
