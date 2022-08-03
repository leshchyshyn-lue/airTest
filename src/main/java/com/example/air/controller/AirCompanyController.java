package com.example.air.controller;

import com.example.air.entity.Flight;
import com.example.air.response.AirCompanyAndItsFlights;
import com.example.air.response.FlightResponse;
import com.example.air.response.FlightResponseForAirConpanyAndFlights;
import com.example.air.util.Status;
import com.example.air.entity.AirCompany;
import com.example.air.exception.AirCompanyNotFoundException;
import com.example.air.exception.AirCompanyWithThisNameAlreadyExistsException;
import com.example.air.request.AirCompanyRequest;
import com.example.air.response.AirCompanyResponse;
import com.example.air.service.AirCompanyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/aircompanies")
public class AirCompanyController {

    private final AirCompanyService airCompanyService;
    private final FlightController flightController;

    @Autowired
    public AirCompanyController(AirCompanyService airCompanyService, FlightController flightController) {
        this.airCompanyService = airCompanyService;
        this.flightController = flightController;
    }

    public AirCompany convertToAirCompany(AirCompanyRequest airCompanyRequest) {
        AirCompany airCompany = new AirCompany();

        airCompany.setName(airCompanyRequest.getAirCompanyName());
        airCompany.setCompanyType(airCompanyRequest.getCompanyType());
        airCompany.setFoundedAt(airCompanyRequest.getFoundedAt());
        return airCompany;
    }

    public AirCompanyResponse convertToResponse(AirCompany airCompany) {
        AirCompanyResponse airCompanyResponse = new AirCompanyResponse();

        airCompanyResponse.setId(airCompany.getId());

        airCompanyResponse.setCompanyType(airCompany.getCompanyType());
        airCompanyResponse.setName(airCompany.getName());
        airCompanyResponse.setFoundedAt(airCompany.getFoundedAt());
        return airCompanyResponse;
    }

    public FlightResponseForAirConpanyAndFlights convertFlights(FlightResponse response) {
        FlightResponseForAirConpanyAndFlights flights = new FlightResponseForAirConpanyAndFlights();

        flights.setId(response.getId());
        flights.setStatus(response.getStatus());

        flights.setCreatedAt(response.getCreatedAt());
        flights.setEstimatedFlightTime(response.getEstimatedFlightTime());
        flights.setDistance(response.getDistance());
        flights.setDelayStartedAt(response.getDelayStartedAt());
        flights.setDepartureCountry(response.getDepartureCountry());
        flights.setDestinationCountry(response.getDestinationCountry());
        flights.setEndedAt(response.getEndedAt());
        return flights;
    }

    public List<AirCompanyAndItsFlights> convertToResponseAirCompaniesAndFlights(List<AirCompany> airCompany) {
        List<AirCompanyAndItsFlights> response = new ArrayList<>();
        for (AirCompany a : airCompany) {
            List<FlightResponseForAirConpanyAndFlights> flights = new ArrayList<>();
            for (Flight f : a.getFlightsAirCompany()) {
                flights.add(convertFlights(flightController.convertToResponse(f)));
            }
            AirCompanyAndItsFlights airCompanyAndItsFlights = new AirCompanyAndItsFlights();
            airCompanyAndItsFlights.setName(a.getName());
            airCompanyAndItsFlights.setFlights(flights);
            response.add(airCompanyAndItsFlights);
        }
        return response;
    }



    @GetMapping
    public ResponseEntity<List<AirCompany>> findAllAirCompanies() {
        return ResponseEntity.status(HttpStatus.OK).body(airCompanyService.findAllAirCompany());
    }

    @GetMapping("/{id}")
    public ResponseEntity<AirCompany> findAirCompanyById(@PathVariable("id") Long id) throws AirCompanyNotFoundException {
        return ResponseEntity.status(HttpStatus.OK).body(airCompanyService.findAirCompanyById(id));
    }

    @PostMapping
    public ResponseEntity<AirCompanyResponse> saveAirCompany(@RequestBody @Valid AirCompanyRequest request) throws AirCompanyWithThisNameAlreadyExistsException {
        AirCompanyResponse response = convertToResponse(airCompanyService.saveAirCompany(convertToAirCompany(request)));
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @PutMapping("/{id}")
    public ResponseEntity<AirCompanyResponse> updateAirCompany(@RequestBody @Valid AirCompanyRequest request,
                                                               @PathVariable("id") Long id) throws AirCompanyNotFoundException, AirCompanyWithThisNameAlreadyExistsException {
        AirCompanyResponse response = convertToResponse(airCompanyService.updateAirCompany(convertToAirCompany(request), id));
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @DeleteMapping("/{id}")
    public void deleteAirCompanyById(@PathVariable("id") Long id) throws AirCompanyNotFoundException {
        airCompanyService.deleteAirCompanyById(id);
    }

    @GetMapping("/flights/{status}")
    public ResponseEntity<List<AirCompanyAndItsFlights>> findTheCompanyAndItsFlightsByStatus(@PathVariable("status") Status status) {
        List<AirCompany> response = airCompanyService.findTheCompanyAndItsFlightsByStatus(status);
        return ResponseEntity.status(HttpStatus.OK).body(convertToResponseAirCompaniesAndFlights(response));
    }


}
