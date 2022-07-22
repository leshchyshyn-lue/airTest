package com.example.air.controller;


import com.example.air.entity.Airplane;
import com.example.air.request.AirplaneRequest;
import com.example.air.response.AirplaneResponse;
import com.example.air.service.AirplaneService;
import com.example.air.exception.AirCompanyNotFoundException;
import com.example.air.exception.AirplaneNotFoundException;
import com.example.air.exception.AirplaneWithThisNameAlreadyExistsException;
import com.example.air.exception.AirplaneWithThisSerialNumberAlreadyExistsException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/airplanes")
public class AirplaneController {

    private final AirplaneService airplaneService;

    public AirplaneController(AirplaneService airplaneService) {
        this.airplaneService = airplaneService;
    }

    public Airplane convertToAirplane(AirplaneRequest airplaneRequest) {
        Airplane airplane = new Airplane();

        airplane.setCreatedAt(LocalDateTime.now());
        airplane.setFlightDistance(airplaneRequest.getFlightDistance());
        airplane.setFuelCapacity(airplaneRequest.getFuelCapacity());
        airplane.setName(airplaneRequest.getName());
        airplane.setFactorySerialNumber(airplaneRequest.getFactorySerialNumber());
        airplane.setType(airplaneRequest.getType());
        airplane.setNumberOfFlights(airplaneRequest.getNumberOfFlights());
        return airplane;
    }

    public AirplaneResponse convertToResponse(Airplane airplane) {
        AirplaneResponse airplaneResponse = new AirplaneResponse();

        airplaneResponse.setId(airplane.getId());

        airplaneResponse.setCreatedAt(airplane.getCreatedAt());
        airplaneResponse.setFlightDistance(airplane.getFlightDistance());
        airplaneResponse.setFuelCapacity(airplane.getFuelCapacity());
        airplaneResponse.setName(airplane.getName());
        airplaneResponse.setFactorySerialNumber(airplane.getFactorySerialNumber());
        airplaneResponse.setType(airplane.getType());
        airplaneResponse.setNumberOfFlights(airplane.getNumberOfFlights());
        airplaneResponse.setAirCompanyId(airplane.getAirCompany());
        return airplaneResponse;
    }

    @GetMapping
    public ResponseEntity<List<Airplane>> findAllAirplanes() {
        return ResponseEntity.status(HttpStatus.OK).body(airplaneService.findAllAirplanes());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Airplane> findAirplanesById(@PathVariable("id") Long id) throws AirplaneNotFoundException {
        return ResponseEntity.status(HttpStatus.OK).body(airplaneService.findAirplaneById(id));
    }

    @PostMapping
    public ResponseEntity<AirplaneResponse> saveAirplane(@RequestBody @Valid AirplaneRequest request) throws AirplaneWithThisNameAlreadyExistsException, AirplaneWithThisSerialNumberAlreadyExistsException {
        AirplaneResponse response = convertToResponse(airplaneService.saveAirplane(convertToAirplane(request)));
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @PutMapping("/{id}")
    public ResponseEntity<AirplaneResponse> updateAirplane(@RequestBody @Valid AirplaneRequest request,
                                                           @PathVariable("id") Long id) throws AirplaneWithThisNameAlreadyExistsException, AirplaneNotFoundException, AirplaneWithThisSerialNumberAlreadyExistsException {
        AirplaneResponse response = convertToResponse(airplaneService.updateAirplane(convertToAirplane(request), id));
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @DeleteMapping("/{id}")
    public void deleteAirplaneById(@PathVariable("id") Long id) throws AirplaneNotFoundException {
        airplaneService.deleteAirplaneById(id);
    }

    @PutMapping("/airplane{airplaneId}/company/{airCompanyId}")
    public ResponseEntity<AirplaneResponse> changeAirCompanyIdOfTheAirPlane(@PathVariable Long airplaneId,
                                                               @PathVariable Long airCompanyId) throws AirplaneNotFoundException, AirCompanyNotFoundException {
        AirplaneResponse response = convertToResponse(airplaneService.changeAirCompanyIdOfTheAirPlane(airplaneId, airCompanyId));
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

}
