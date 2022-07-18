package com.example.air.controller;


import com.example.air.entity.Airplane;
import com.example.air.request.AirplaneRequest;
import com.example.air.response.AirplaneResponse;
import com.example.air.service.AirplaneService;
import com.example.air.util.AirCompanyNotFoundException;
import com.example.air.util.AirplaneNotFoundException;
import com.example.air.util.AirplaneWithThisNameAlreadyExists;
import com.example.air.util.AirplaneWithThisSerialNumberAlreadyExists;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/airplane")
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
        airplaneResponse.setAirCompanyId(airplane.getAirCompanyId());
        return airplaneResponse;
    }

    @GetMapping
    public ResponseEntity<List<Airplane>> findAll() {
        return ResponseEntity.status(HttpStatus.OK).body(airplaneService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Airplane> findById(@PathVariable("id") Long id) throws AirplaneNotFoundException {
        return ResponseEntity.status(HttpStatus.OK).body(airplaneService.findById(id));
    }

    @PostMapping
    public ResponseEntity<AirplaneResponse> saveAirplane(@RequestBody @Valid AirplaneRequest request) throws AirplaneWithThisNameAlreadyExists, AirplaneWithThisSerialNumberAlreadyExists {
        AirplaneResponse response = convertToResponse(airplaneService.saveAirplane(convertToAirplane(request)));
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<AirplaneResponse> updateAirplane(@RequestBody @Valid AirplaneRequest request,
                                                           @PathVariable("id") Long id) throws AirplaneWithThisNameAlreadyExists, AirplaneNotFoundException, AirplaneWithThisSerialNumberAlreadyExists {
        AirplaneResponse response = convertToResponse(airplaneService.updateAirplane(convertToAirplane(request), id));
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @DeleteMapping("/{id}")
    public void deleteById(@PathVariable("id") Long id) throws AirplaneNotFoundException {
        airplaneService.deleteById(id);
    }

    @PutMapping("/{airplaneId}/change/{airCompanyId}")
    public ResponseEntity<AirplaneResponse> changeAirCompanyId(@PathVariable Long airplaneId,
                                                               @PathVariable Long airCompanyId) throws AirplaneNotFoundException, AirCompanyNotFoundException {
        AirplaneResponse response = convertToResponse(airplaneService.changeAirCompanyId(airplaneId, airCompanyId));
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

}
