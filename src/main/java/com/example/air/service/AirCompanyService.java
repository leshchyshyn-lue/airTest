package com.example.air.service;


import com.example.air.Status;
import com.example.air.entity.AirCompany;
import com.example.air.entity.Flight;
import com.example.air.repository.AirCompanyRepository;
import com.example.air.repository.FlightRepository;
import com.example.air.util.AirCompanyNotFoundException;
import com.example.air.util.AirCompanyWithThisNameAlreadyExists;
import com.example.air.util.ThisCompanyHasNoFlightsWithThisStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class AirCompanyService {
    private final AirCompanyRepository airCompanyRepository;
    private final FlightRepository flightRepository;

    @Autowired
    public AirCompanyService(AirCompanyRepository airCompanyRepository, FlightRepository flightRepository) {
        this.airCompanyRepository = airCompanyRepository;
        this.flightRepository = flightRepository;
    }

    public List<AirCompany> findAll() {
        return airCompanyRepository.findAll();
    }

    public AirCompany findById(Long id) throws AirCompanyNotFoundException {
        return airCompanyRepository.findById(id)
                .orElseThrow(() -> new AirCompanyNotFoundException("No air company with this ID was found"));
    }

    public AirCompany saveAirCompany(AirCompany airCompany) throws AirCompanyWithThisNameAlreadyExists {
        AirCompany byName = airCompanyRepository.findByAirCompanyName(airCompany.getAirCompanyName());
        if (byName != null) {
            throw new AirCompanyWithThisNameAlreadyExists("Air company with this name already exists");
        }
        return airCompanyRepository.save(airCompany);
    }

    public AirCompany updateAirCompany(AirCompany newAirCompany, Long id) throws AirCompanyNotFoundException, AirCompanyWithThisNameAlreadyExists {
        AirCompany oldAirCompany = findById(id);
        AirCompany byName = airCompanyRepository.findByAirCompanyName(newAirCompany.getAirCompanyName());
        if (byName != null) {
            throw new AirCompanyWithThisNameAlreadyExists("Air company with this name already exists");
        }
        oldAirCompany.setAirCompanyName(newAirCompany.getAirCompanyName());
        oldAirCompany.setCompanyType(newAirCompany.getCompanyType());
        oldAirCompany.setFoundedAt(newAirCompany.getFoundedAt());
        return airCompanyRepository.save(oldAirCompany);
    }

    public void deleteById(Long id) throws AirCompanyNotFoundException {
        findById(id);
        airCompanyRepository.deleteById(id);
    }

    public List<AirCompany> findCompanyAndFlight(Status status) throws ThisCompanyHasNoFlightsWithThisStatus, AirCompanyNotFoundException {
        List<AirCompany> response = new ArrayList<>();
        List<Flight> flights = flightRepository.findByStatus(status);
        if (flights.isEmpty()) {
            throw new ThisCompanyHasNoFlightsWithThisStatus("This company has no flights with this status");
        }
        for (Flight f : flights) {
            AirCompany byId = airCompanyRepository.findById(f.getAirCompanyId()
                            .getId())
                    .orElseThrow(() -> new AirCompanyNotFoundException("This flight don't have a company"));
            AirCompany airCompany = new AirCompany();
            airCompany.setAirCompanyName(byId.getAirCompanyName());
            airCompany.setFlightsAirCompany(byId.getFlightsAirCompany());
            response.add(airCompany);
        }
        return response;
    }


}
