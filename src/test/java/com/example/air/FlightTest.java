package com.example.air;


import com.example.air.entity.Flight;
import com.example.air.repository.FlightRepository;
import com.example.air.service.FlightService;
import com.example.air.util.FlightNotFoundException;
import com.example.air.util.NoFlightWithThisStatus;
import com.example.air.util.NoOneFlightNow;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class FlightTest {
    private final static long FLIGHT_ID = 3L;
    private final static LocalDateTime CREATE_AT = LocalDateTime.now();

    @InjectMocks
    private FlightService flightService;

    @Mock
    private FlightRepository flightRepository;


    @Test
    public void testFindAllSuccess() throws NoOneFlightNow {
        Flight flight = new Flight();
        flight.setId(FLIGHT_ID);

        List<Flight> flights = new ArrayList<>();
        flights.add(flight);

        when(flightRepository.findAll()).thenReturn(flights);

        List<Flight> result = flightService.findAll();

        assertEquals(flights, result);
    }

    @Test
    public void testFindAllFailNoOneFlightNow() {
        List<Flight> flights = new ArrayList<>();

        when(flightRepository.findAll()).thenReturn(flights);

        assertThrows(NoOneFlightNow.class, () -> flightService.findAll());
    }

    @Test
    public void testFindByIdSuccess() throws FlightNotFoundException {
        Flight flight = new Flight();
        flight.setId(FLIGHT_ID);

        when(flightRepository.findById(flight.getId())).thenReturn(Optional.of(flight));

        Flight result = flightService.findById(flight.getId());

        assertEquals(flight, result);
    }

    @Test
    public void testFindByIdFailFlightNotFoundException() {
        Flight flight = new Flight();
        flight.setId(FLIGHT_ID);

        when(flightRepository.findById(flight.getId())).thenReturn(Optional.empty());

        assertThrows(FlightNotFoundException.class, () -> flightService.findById(flight.getId()));
    }

    @Test
    public void testSaveFlightSuccess() {
        Flight before = new Flight();

        Flight after = new Flight();
        after.setId(FLIGHT_ID);
        after.setStatus(Status.PENDING);

        when(flightRepository.save(before)).thenReturn(after);

        Flight result = flightService.saveFlight(before);

        assertEquals(after, result);
    }

    @Test
    public void testUpdateFlightSuccess() throws FlightNotFoundException {
        Flight before = new Flight();
        before.setId(FLIGHT_ID);
        before.setStatus(Status.PENDING);
        before.setCreatedAt(CREATE_AT);

        Flight after = new Flight();
        after.setId(FLIGHT_ID);
        after.setStatus(Status.PENDING);
        after.setCreatedAt(CREATE_AT);

        when(flightRepository.findById(before.getId())).thenReturn(Optional.of(before));
        when(flightService.updateFlight(before, before.getId())).thenReturn(after);

        Flight result = flightService.updateFlight(before, before.getId());

        assertEquals(after.getCreatedAt(), result.getCreatedAt());
        assertEquals(after, result);
    }

    @Test
    public void testUpdateFlightFailFlightNotFoundException() {
        Flight flight = new Flight();
        flight.setId(FLIGHT_ID);
        flight.setStatus(Status.PENDING);

        when(flightRepository.findById(flight.getId())).thenReturn(Optional.empty());

        assertThrows(FlightNotFoundException.class, () -> flightService.updateFlight(flight, flight.getId()));
    }

    @Test
    public void testDeleteByIdSuccess() {
        Flight flight = new Flight();
        flight.setId(FLIGHT_ID);

        when(flightRepository.findById(flight.getId())).thenReturn(Optional.of(flight));

        assertDoesNotThrow(() -> flightService.deleteById(flight.getId()));
    }

    @Test
    public void testChangeStatusSuccess() throws FlightNotFoundException {
        Flight del = new Flight();
        del.setStatus(Status.DELAYED);
        del.setId(FLIGHT_ID);

        Flight delAfter = new Flight();
        delAfter.setId(FLIGHT_ID);
        delAfter.setStatus(Status.DELAYED);
        delAfter.setDelayStartedAt(LocalDateTime.now());

        when(flightRepository.findById(del.getId())).thenReturn(Optional.of(del));
        when(flightRepository.save(del)).thenReturn(delAfter);

        Flight result = flightService.changeStatus(del.getStatus(), del.getId());

        assertEquals(delAfter, result);

        ////

        Flight compl = new Flight();
        compl.setStatus(Status.COMPLETED);
        compl.setId(FLIGHT_ID);

        Flight complAfter = new Flight();
        complAfter.setId(FLIGHT_ID);
        complAfter.setStatus(Status.COMPLETED);
        complAfter.setEndedAt(LocalDateTime.now());

        when(flightRepository.findById(compl.getId())).thenReturn(Optional.of(compl));
        when(flightRepository.save(compl)).thenReturn(complAfter);

        Flight result2 = flightService.changeStatus(compl.getStatus(), compl.getId());

        assertEquals(complAfter, result2);
    }

    @Test
    public void testFindFlightActiveSuccess() throws NoFlightWithThisStatus {
        List<Flight> flights = new ArrayList<>();
        Flight flight = new Flight();
        flight.setStatus(Status.ACTIVE);
        flights.add(flight);

        when(flightRepository.findFlightActive(flight.getStatus())).thenReturn(flights);

        List<Flight> result = flightService.findFlightActive();

        assertArrayEquals(flights.toArray(), result.toArray());
    }

    @Test
    public void testFindFlightActiveFailNoFlightWithThisStatus() {
        Flight flight = new Flight();
        flight.setStatus(Status.ACTIVE);

        when(flightRepository.findFlightActive(flight.getStatus())).thenReturn(Collections.EMPTY_LIST);

        assertThrows(NoFlightWithThisStatus.class, () -> flightService.findFlightActive());
    }

    @Test
    public void testFindAllWithStatusCompleteSuccess() throws NoFlightWithThisStatus {
        List<Flight> flights = new ArrayList<>();
        Flight flight = new Flight();
        flight.setStatus(Status.COMPLETED);
        flights.add(flight);

        when(flightRepository.findAllWithStatusComplete(flight.getStatus())).thenReturn(flights);

        List<Flight> result = flightService.findAllWithStatusComplete();

        assertArrayEquals(flights.toArray(), result.toArray());
    }

    @Test
    public void testFindAllWithStatusCompleteFailNoFlightWithThisStatus() {
        Flight flight = new Flight();
        flight.setStatus(Status.COMPLETED);

        when(flightRepository.findAllWithStatusComplete(flight.getStatus())).thenReturn(Collections.EMPTY_LIST);

        assertThrows(NoFlightWithThisStatus.class, () -> flightService.findAllWithStatusComplete());
    }

}
