package com.example.air.service;

import com.example.air.util.Status;
import com.example.air.entity.Flight;
import com.example.air.repository.FlightRepository;
import com.example.air.exception.FlightNotFoundException;
import com.example.air.exception.NoFlightWithThisStatusException;
import com.example.air.exception.NoOneFlightNowException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class FlightService {
    private final FlightRepository flightRepository;

    @Autowired
    public FlightService(FlightRepository flightRepository) {
        this.flightRepository = flightRepository;
    }

    public List<Flight> findAllFlights() throws NoOneFlightNowException {
        List<Flight> flights = flightRepository.findAll();
        if (flights.isEmpty()) {
            throw new NoOneFlightNowException("No one flight now");
        }
        return flights;

    }

    public Flight findFlightById(Long id) throws FlightNotFoundException {
        return flightRepository.findById(id)
                .orElseThrow(() -> new FlightNotFoundException("No flight with ID "+ id +" was found"));
    }

    public Flight saveFlight(Flight flight) {
        flight.setStatus(Status.PENDING);
        return flightRepository.save(flight);
    }

    public Flight updateFlight(Flight newFlight, Long id) throws FlightNotFoundException {
        Flight oldFlight = findFlightById(id);
        oldFlight.setCreatedAt(newFlight.getCreatedAt());
        oldFlight.setEstimatedFlightTime(newFlight.getEstimatedFlightTime());
        oldFlight.setDistance(newFlight.getDistance());
        oldFlight.setDelayStartedAt(newFlight.getDelayStartedAt());
        oldFlight.setDepartureCountry(newFlight.getDepartureCountry());
        oldFlight.setDestinationCountry(newFlight.getDestinationCountry());
        oldFlight.setEndedAt(newFlight.getEndedAt());
        oldFlight.setStatus(newFlight.getStatus());
        return flightRepository.save(oldFlight);
    }

    public void deleteFlightById(Long id) throws FlightNotFoundException {
        findFlightById(id);
        flightRepository.deleteById(id);
    }

    public Flight changeFlightStatus(Status status, Long id) throws FlightNotFoundException {
        Flight flightFind = findFlightById(id);
        flightFind.setStatus(status);
        if (status.equals(Status.DELAYED)) {
            flightFind.setDelayStartedAt(LocalDateTime.now());
        } else if (status.equals(Status.COMPLETED)) {
            flightFind.setEndedAt(LocalDateTime.now());
        }
        return flightRepository.save(flightFind);
    }

    public List<Flight> findFlightsWithStatusActive() throws NoFlightWithThisStatusException {
        List<Flight> flights = flightRepository.findFlightsWithStatusActive(Status.ACTIVE);
        checkingIfEmpty(flights);
        return flights;
    }

    public List<Flight> findAllFlightsWithStatusComplete() throws NoFlightWithThisStatusException {
        List<Flight> flights = flightRepository.findAllFlightsWithStatusComplete(Status.COMPLETED);
        checkingIfEmpty(flights);
        return flights;
    }

    public void checkingIfEmpty(List<Flight> flights) throws NoFlightWithThisStatusException {
        if (flights.isEmpty()) {
            throw new NoFlightWithThisStatusException("No flight with this status");
        }
    }


}
