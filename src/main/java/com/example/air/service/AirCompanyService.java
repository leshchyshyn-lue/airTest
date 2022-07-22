package com.example.air.service;


import com.example.air.entity.AirCompany;
import com.example.air.entity.Flight;
import com.example.air.exception.AirCompanyNotFoundException;
import com.example.air.exception.AirCompanyWithThisNameAlreadyExistsException;
import com.example.air.repository.AirCompanyRepository;
import com.example.air.repository.FlightRepository;
import com.example.air.util.Status;
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

    public List<AirCompany> findAllAirCompany() {
        return airCompanyRepository.findAll();
    }

    public AirCompany findAirCompanyById(Long id) throws AirCompanyNotFoundException {
        return airCompanyRepository.findById(id)
                .orElseThrow(() -> new AirCompanyNotFoundException("No air company with ID " + id + " was found"));
    }

    public AirCompany saveAirCompany(AirCompany airCompany) throws AirCompanyWithThisNameAlreadyExistsException {
        findByAirCompanyName(airCompany);
        return airCompanyRepository.save(airCompany);
    }

    public AirCompany updateAirCompany(AirCompany newAirCompany, Long id) throws AirCompanyNotFoundException, AirCompanyWithThisNameAlreadyExistsException {
        AirCompany oldAirCompany = findAirCompanyById(id);
        findByAirCompanyName(newAirCompany);
        oldAirCompany.setName(newAirCompany.getName());
        oldAirCompany.setCompanyType(newAirCompany.getCompanyType());
        oldAirCompany.setFoundedAt(newAirCompany.getFoundedAt());
        return airCompanyRepository.save(oldAirCompany);
    }

    public void deleteAirCompanyById(Long id) throws AirCompanyNotFoundException {
        findAirCompanyById(id);
        airCompanyRepository.deleteById(id);
    }
    public void findByAirCompanyName(AirCompany airCompany) throws AirCompanyWithThisNameAlreadyExistsException {
        AirCompany name = airCompanyRepository.findByName(airCompany.getName());
        if (name != null) {
            throw new AirCompanyWithThisNameAlreadyExistsException("Air company with this name already exists");
        }
    }


    public List<AirCompany> findTheCompanyAndItsFlightsByStatus(Status status) {
        List<AirCompany> response = new ArrayList<>();
        List<AirCompany> airCompanies = findAllAirCompany();
        for (AirCompany a : airCompanies) {
            if (!a.getFlightsAirCompany().isEmpty()) {
                List<Flight> flights = flightRepository.findByStatusAndByCompanyId(status, a);
                AirCompany airCompany = new AirCompany();
                airCompany.setFlightsAirCompany(flights);
                airCompany.setName(a.getName());
                response.add(airCompany);
            }
        }
        return response;
    }

}
