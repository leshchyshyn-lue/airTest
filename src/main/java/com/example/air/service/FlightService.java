package com.example.air.service;

import com.example.air.Status;
import com.example.air.entity.Flight;
import com.example.air.repository.FlightRepository;
import com.example.air.util.FlightNotFoundException;
import com.example.air.util.NoFlightWithThisStatus;
import com.example.air.util.NoOneFlightNow;
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

    public List<Flight> findAll() throws NoOneFlightNow {
        List<Flight> flights = flightRepository.findAll();
        if (flights.isEmpty()) {
            throw new NoOneFlightNow("No one flight now");
        }
        return flights;

    }

    public Flight findById(Long id) throws FlightNotFoundException {
        return flightRepository.findById(id)
                .orElseThrow(() -> new FlightNotFoundException("No flight with this ID was found"));
    }

    public Flight saveFlight(Flight flight) {
        flight.setStatus(Status.PENDING);
        return flightRepository.save(flight);
    }

    public Flight updateFlight(Flight newFlight, Long id) throws FlightNotFoundException {
        Flight oldFlight = findById(id);
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

    public void deleteById(Long id) throws FlightNotFoundException {
        findById(id);
        flightRepository.deleteById(id);
    }

    public Flight changeStatus(Status status, Long id) throws FlightNotFoundException {
        Flight flightFind = findById(id);
        flightFind.setStatus(status);
        if (status.equals(Status.DELAYED)) {
            flightFind.setDelayStartedAt(LocalDateTime.now());
        } else if (status.equals(Status.COMPLETED)) {
            flightFind.setEndedAt(LocalDateTime.now());
        }
        return flightRepository.save(flightFind);
    }

    public List<Flight> findFlightActive() throws NoFlightWithThisStatus {
        List<Flight> byStatus = flightRepository.findFlightActive(Status.ACTIVE);
        if (byStatus.isEmpty()) {
            throw new NoFlightWithThisStatus("No flight with this status");
        }
        return byStatus;
    }

    public List<Flight> findAllWithStatusComplete() throws NoFlightWithThisStatus {
        List<Flight> flights = flightRepository.findAllWithStatusComplete(Status.COMPLETED);
        if (flights.isEmpty()) {
            throw new NoFlightWithThisStatus("No flight with status" + "COMPLETE");
        }
        return flights;
    }


}
