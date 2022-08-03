package com.example.air.controller;


import com.example.air.util.Status;
import com.example.air.entity.Flight;
import com.example.air.request.FlightRequest;
import com.example.air.response.FlightResponse;
import com.example.air.service.FlightService;
import com.example.air.exception.FlightNotFoundException;
import com.example.air.exception.NoFlightWithThisStatusException;
import com.example.air.exception.NoOneFlightNowException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/flights")
public class FlightController {

    private final FlightService flightService;

    @Autowired
    public FlightController(FlightService flightService) {
        this.flightService = flightService;
    }

    public Flight convertToFlight(FlightRequest flightRequest) {
        Flight flight = new Flight();

        flight.setCreatedAt(LocalDateTime.now());
        flight.setEstimatedFlightTime(flightRequest.getEstimatedFlightTime());
        flight.setDistance(flightRequest.getDistance());
        flight.setDelayStartedAt(LocalDateTime.now());
        flight.setDepartureCountry(flightRequest.getDepartureCountry());
        flight.setDestinationCountry(flightRequest.getDestinationCountry());
        return flight;
    }

    public FlightResponse convertToResponse(Flight flight) {
        FlightResponse flightResponse = new FlightResponse();

        flightResponse.setId(flight.getId());
        flightResponse.setStatus(flight.getStatus());

        flightResponse.setCreatedAt(flight.getCreatedAt());
        flightResponse.setEstimatedFlightTime(flight.getEstimatedFlightTime());
        flightResponse.setDistance(flight.getDistance());
        flightResponse.setDelayStartedAt(flight.getDelayStartedAt());
        flightResponse.setDepartureCountry(flight.getDepartureCountry());
        flightResponse.setDestinationCountry(flight.getDestinationCountry());
        flightResponse.setEndedAt(flight.getEndedAt());
        flightResponse.setAirCompanyId(flight.getAirCompany());
        flightResponse.setAirPlaneId(flight.getAirplane());
        return flightResponse;
    }

    @GetMapping
    public ResponseEntity<List<Flight>> findAllFlights() throws NoOneFlightNowException {
        return ResponseEntity.status(HttpStatus.OK).body(flightService.findAllFlights());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Flight> findFlightById(@PathVariable("id") Long id) throws FlightNotFoundException {
        return ResponseEntity.status(HttpStatus.OK).body(flightService.findFlightById(id));
    }

    @PostMapping
    public ResponseEntity<FlightResponse> saveFlight(@RequestBody FlightRequest request) {
        FlightResponse response = convertToResponse(flightService.saveFlight(convertToFlight(request)));
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @PutMapping("/{id}")
    public ResponseEntity<FlightResponse> updateFlight(@RequestBody FlightRequest request,
                                                       @PathVariable("id") Long id) throws FlightNotFoundException {
        FlightResponse response = convertToResponse(flightService.updateFlight(convertToFlight(request), id));
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @DeleteMapping("/{id}")
    public void deleteFlightById(@PathVariable("id") Long id) throws FlightNotFoundException {
        flightService.deleteFlightById(id);
    }

    @PutMapping("/{status}/{id}")
    public ResponseEntity<Flight> changeFlightStatus(@PathVariable("status") Status status, @PathVariable("id") Long id) throws FlightNotFoundException {
        return ResponseEntity.status(HttpStatus.OK).body(flightService.changeFlightStatus(status, id));
    }

    @GetMapping("/active")
    public ResponseEntity<List<Flight>> findFlightActive() throws NoFlightWithThisStatusException {
        return ResponseEntity.status(HttpStatus.OK).body(flightService.findFlightsWithStatusActive());
    }

    @GetMapping("/complate")
    public ResponseEntity<List<Flight>> findAllFlightWithStatusComplete() throws NoFlightWithThisStatusException {
        return ResponseEntity.status(HttpStatus.OK).body(flightService.findAllFlightsWithStatusComplete());
    }

}
