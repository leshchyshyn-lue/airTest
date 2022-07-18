package com.example.air.controller;

import com.example.air.Status;
import com.example.air.entity.AirCompany;
import com.example.air.request.AirCompanyRequest;
import com.example.air.response.AirCompanyResponse;
import com.example.air.service.AirCompanyService;
import com.example.air.util.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/airCompany")
public class AirCompanyController {

    public final AirCompanyService airCompanyService;

    @Autowired
    public AirCompanyController(AirCompanyService airCompanyService) {
        this.airCompanyService = airCompanyService;
    }

    public AirCompany convertToAirCompany(AirCompanyRequest airCompanyRequest) {
        AirCompany airCompany = new AirCompany();

        airCompany.setAirCompanyName(airCompanyRequest.getAirCompanyName());
        airCompany.setCompanyType(airCompanyRequest.getCompanyType());
        airCompany.setFoundedAt(airCompanyRequest.getFoundedAt());
        return airCompany;
    }

    public AirCompanyResponse convertToResponse(AirCompany airCompany) {
        AirCompanyResponse airCompanyResponse = new AirCompanyResponse();

        airCompanyResponse.setId(airCompany.getId());

        airCompanyResponse.setCompanyType(airCompany.getCompanyType());
        airCompanyResponse.setAirCompanyName(airCompany.getAirCompanyName());
        airCompanyResponse.setFoundedAt(airCompany.getFoundedAt());
        return airCompanyResponse;
    }

    @GetMapping("/all")
    public ResponseEntity<List<AirCompany>> findAll() {
        return ResponseEntity.status(HttpStatus.OK).body(airCompanyService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<AirCompany> findById(@PathVariable("id") Long id) throws AirCompanyNotFoundException {
        return ResponseEntity.status(HttpStatus.OK).body(airCompanyService.findById(id));
    }

    @PostMapping
    public ResponseEntity<AirCompanyResponse> saveAirCompany(@RequestBody @Valid AirCompanyRequest request) throws AirCompanyWithThisNameAlreadyExists {
        AirCompanyResponse response = convertToResponse(airCompanyService.saveAirCompany(convertToAirCompany(request)));
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<AirCompanyResponse> updateAirCompany(@RequestBody @Valid AirCompanyRequest request,
                                                               @PathVariable("id") Long id) throws AirCompanyNotFoundException, AirCompanyWithThisNameAlreadyExists {
        AirCompanyResponse response = convertToResponse(airCompanyService.updateAirCompany(convertToAirCompany(request), id));
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @DeleteMapping("/{id}")
    public void deleteById(@PathVariable("id") Long id) throws AirCompanyNotFoundException {
        airCompanyService.deleteById(id);
    }

    @GetMapping("/{status}/companyAndFlight")
    public ResponseEntity<List<AirCompany>> findCompanyAndFlight(@PathVariable Status status) throws ThisCompanyHasNoFlightsWithThisStatus, AirCompanyNotFoundException, FlightNotFoundException {
        List<AirCompany> response = airCompanyService.findCompanyAndFlight(status);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }



}
