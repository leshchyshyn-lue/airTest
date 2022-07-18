package com.example.air;


import com.example.air.entity.AirCompany;
import com.example.air.entity.Flight;
import com.example.air.repository.AirCompanyRepository;
import com.example.air.repository.FlightRepository;
import com.example.air.service.AirCompanyService;
import com.example.air.util.AirCompanyNotFoundException;
import com.example.air.util.AirCompanyWithThisNameAlreadyExists;
import com.example.air.util.ThisCompanyHasNoFlightsWithThisStatus;
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
public class AirCompanyServiceTest {

    private final static Long ID = 1L;
    private final static Long FLIGHT_ID = 3L;

    private final static String AIR_COMPANY_NAME = "name";

    private final static String COMPANY_TYPE = "type";

    private final static LocalDateTime FOUNDED_AT = LocalDateTime.now();

    @InjectMocks
    private AirCompanyService airCompanyService;

    @Mock
    private AirCompanyRepository airCompanyRepository;

    @Mock
    private FlightRepository flightRepository;

    @Test
    public void testFindAll() {
        AirCompany airCompany = new AirCompany();

        List<AirCompany> airCompanyList = new ArrayList<>();
        airCompanyList.add(airCompany);

        when(airCompanyRepository.findAll()).thenReturn(airCompanyList);
        List<AirCompany> result = airCompanyService.findAll();
        assertEquals(airCompanyList, result);
    }

    @Test
    public void testFindByIdSuccess() throws AirCompanyNotFoundException {
        AirCompany airCompany = new AirCompany();
        airCompany.setId(ID);

        when(airCompanyRepository.findById(airCompany.getId())).thenReturn(Optional.of(airCompany));

        AirCompany result = airCompanyService.findById(airCompany.getId());

        assertEquals(airCompany, result);
    }

    @Test
    public void testFindByIdFailAirCompanyNotFoundException() {
        AirCompany airCompany = new AirCompany();
        airCompany.setId(ID);

        when(airCompanyRepository.findById(airCompany.getId())).thenReturn(Optional.empty());

        assertThrows(AirCompanyNotFoundException.class, () -> airCompanyService.findById(airCompany.getId()));
    }

    @Test
    public void testSaveAirCompanySuccess() throws AirCompanyWithThisNameAlreadyExists {
        AirCompany before = new AirCompany();
        before.setAirCompanyName(AIR_COMPANY_NAME);

        AirCompany after = new AirCompany();
        after.setAirCompanyName(AIR_COMPANY_NAME);
        after.setId(ID);

        when(airCompanyRepository.save(before)).thenReturn(after);
        when(airCompanyRepository.findByAirCompanyName(before.getAirCompanyName())).thenReturn(null);

        AirCompany result = airCompanyService.saveAirCompany(before);

        assertEquals(after, result);
        assertEquals(after.getAirCompanyName(), result.getAirCompanyName());
    }

    @Test
    public void testSaveAirCompanyFailAirCompanyWithThisNameAlreadyExists() {
        AirCompany airCompany = new AirCompany();
        airCompany.setAirCompanyName(AIR_COMPANY_NAME);

        when(airCompanyRepository.findByAirCompanyName(airCompany.getAirCompanyName())).thenReturn(airCompany);

        assertThrows(AirCompanyWithThisNameAlreadyExists.class, () -> airCompanyService.saveAirCompany(airCompany));
    }

    @Test
    public void testUpdateAirCompanySuccess() throws AirCompanyNotFoundException, AirCompanyWithThisNameAlreadyExists {
        AirCompany before = new AirCompany();
        before.setId(ID);
        before.setAirCompanyName(AIR_COMPANY_NAME);
        before.setCompanyType(COMPANY_TYPE);
        before.setFoundedAt(FOUNDED_AT);

        AirCompany after = new AirCompany();
        after.setId(ID);
        after.setAirCompanyName(AIR_COMPANY_NAME);
        after.setCompanyType(COMPANY_TYPE);
        after.setFoundedAt(FOUNDED_AT);

        when(airCompanyRepository.findById(before.getId())).thenReturn(Optional.of(before));
        when(airCompanyRepository.findByAirCompanyName(before.getAirCompanyName())).thenReturn(null);
        when(airCompanyRepository.save(before)).thenReturn(after);

        AirCompany result = airCompanyService.updateAirCompany(before, before.getId());
        assertEquals(result.getId(), after.getId());
        assertEquals(result.getCompanyType(), after.getCompanyType());
        assertEquals(result.getAirCompanyName(), after.getAirCompanyName());
        assertEquals(result.getFoundedAt(), after.getFoundedAt());
    }

    @Test
    public void testUpdateAirCompanyIdFailAirCompanyWithThisNameAlreadyExists() {
        AirCompany airCompany = new AirCompany();
        airCompany.setId(ID);
        airCompany.setAirCompanyName(AIR_COMPANY_NAME);

        when(airCompanyRepository.findById(airCompany.getId())).thenReturn(Optional.of(airCompany));
        when(airCompanyRepository.findByAirCompanyName(airCompany.getAirCompanyName())).thenReturn(airCompany);

        assertThrows(AirCompanyWithThisNameAlreadyExists.class, () -> airCompanyService.updateAirCompany(airCompany, airCompany.getId()));
    }

    @Test
    public void testDeleteByIdSuccess() {
        AirCompany airCompany = new AirCompany();
        airCompany.setId(ID);

        when(airCompanyRepository.findById(airCompany.getId())).thenReturn(Optional.of(airCompany));
        assertDoesNotThrow(() -> airCompanyService.deleteById(airCompany.getId()));
    }

    @Test
    public void testFindCompanyAndFlightSuccess() throws ThisCompanyHasNoFlightsWithThisStatus, AirCompanyNotFoundException {
        List<Flight> flights = new ArrayList<>();

        AirCompany airCompanyAfter = new AirCompany();

        Flight flight = new Flight();
        flight.setId(FLIGHT_ID);
        flight.setStatus(Status.PENDING);
        flight.setAirCompanyId(airCompanyAfter);
        flights.add(flight);

        airCompanyAfter.setAirCompanyName(AIR_COMPANY_NAME);
        airCompanyAfter.setFlightsAirCompany(flights);

        when(flightRepository.findByStatus(flight.getStatus())).thenReturn(flights);

        when(airCompanyRepository.findById(airCompanyAfter.getId())).thenReturn(Optional.of(airCompanyAfter));

        List<AirCompany> result = airCompanyService.findCompanyAndFlight(flight.getStatus());

        for (AirCompany a : result) {
            assertEquals(a.getAirCompanyName(), airCompanyAfter.getAirCompanyName());
            assertArrayEquals(a.getFlightsAirCompany().toArray(), airCompanyAfter.getFlightsAirCompany().toArray());
        }
    }

    @Test
    public void testFindCompanyAndFlightFailThisCompanyHasNoFlightsWithThisStatus() {
        Flight flight = new Flight();
        flight.setStatus(Status.ACTIVE);

        when(flightRepository.findByStatus(flight.getStatus())).thenReturn(Collections.EMPTY_LIST);

        assertThrows(ThisCompanyHasNoFlightsWithThisStatus.class, () -> airCompanyService.findCompanyAndFlight(flight.getStatus()));
    }

}
