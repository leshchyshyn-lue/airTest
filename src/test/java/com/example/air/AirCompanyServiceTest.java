package com.example.air;


import com.example.air.entity.AirCompany;
import com.example.air.entity.Flight;
import com.example.air.repository.AirCompanyRepository;
import com.example.air.repository.FlightRepository;
import com.example.air.service.AirCompanyService;
import com.example.air.exception.AirCompanyNotFoundException;
import com.example.air.exception.AirCompanyWithThisNameAlreadyExistsException;
import com.example.air.exception.ThisCompanyHasNoFlightsWithThisStatusException;
import com.example.air.util.Status;
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
    public void testFindAllAircompaniesSuccess() {
        AirCompany airCompany = new AirCompany();

        List<AirCompany> airCompanyList = new ArrayList<>();
        airCompanyList.add(airCompany);

        when(airCompanyRepository.findAll()).thenReturn(airCompanyList);
        List<AirCompany> result = airCompanyService.findAllAirCompany();
        assertEquals(airCompanyList, result);
    }

    @Test
    public void testFindAirCompanyByIdSuccess() throws AirCompanyNotFoundException {
        AirCompany airCompany = new AirCompany();
        airCompany.setId(ID);

        when(airCompanyRepository.findById(airCompany.getId())).thenReturn(Optional.of(airCompany));

        AirCompany result = airCompanyService.findAirCompanyById(airCompany.getId());

        assertEquals(airCompany, result);
    }

    @Test
    public void testFindAirCompanyByIdFailAirCompanyNotFoundException() {
        AirCompany airCompany = new AirCompany();
        airCompany.setId(ID);

        when(airCompanyRepository.findById(airCompany.getId())).thenReturn(Optional.empty());

        assertThrows(AirCompanyNotFoundException.class, () -> airCompanyService.findAirCompanyById(airCompany.getId()));
    }

    @Test
    public void testSaveAirCompanySuccess() throws AirCompanyWithThisNameAlreadyExistsException {
        AirCompany before = new AirCompany();
        before.setName(AIR_COMPANY_NAME);

        AirCompany after = new AirCompany();
        after.setName(AIR_COMPANY_NAME);
        after.setId(ID);

        when(airCompanyRepository.save(before)).thenReturn(after);
        when(airCompanyRepository.findByName(before.getName())).thenReturn(null);

        AirCompany result = airCompanyService.saveAirCompany(before);

        assertEquals(after, result);
        assertEquals(after.getName(), result.getName());
    }

    @Test
    public void testSaveAirCompanyFailAirCompanyWithThisNameAlreadyExists() {
        AirCompany airCompany = new AirCompany();
        airCompany.setName(AIR_COMPANY_NAME);

        when(airCompanyRepository.findByName(airCompany.getName())).thenReturn(airCompany);

        assertThrows(AirCompanyWithThisNameAlreadyExistsException.class, () -> airCompanyService.saveAirCompany(airCompany));
    }

    @Test
    public void testUpdateAirCompanySuccess() throws AirCompanyNotFoundException, AirCompanyWithThisNameAlreadyExistsException {
        AirCompany before = new AirCompany();
        before.setId(ID);
        before.setName(AIR_COMPANY_NAME);
        before.setCompanyType(COMPANY_TYPE);
        before.setFoundedAt(FOUNDED_AT);

        AirCompany after = new AirCompany();
        after.setId(ID);
        after.setName(AIR_COMPANY_NAME);
        after.setCompanyType(COMPANY_TYPE);
        after.setFoundedAt(FOUNDED_AT);

        when(airCompanyRepository.findById(before.getId())).thenReturn(Optional.of(before));
        when(airCompanyRepository.findByName(before.getName())).thenReturn(null);
        when(airCompanyRepository.save(before)).thenReturn(after);

        AirCompany result = airCompanyService.updateAirCompany(before, before.getId());
        assertEquals(result.getId(), after.getId());
        assertEquals(result.getCompanyType(), after.getCompanyType());
        assertEquals(result.getName(), after.getName());
        assertEquals(result.getFoundedAt(), after.getFoundedAt());
    }

    @Test
    public void testUpdateAirCompanyIdFailAirCompanyWithThisNameAlreadyExists() {
        AirCompany airCompany = new AirCompany();
        airCompany.setId(ID);
        airCompany.setName(AIR_COMPANY_NAME);

        when(airCompanyRepository.findById(airCompany.getId())).thenReturn(Optional.of(airCompany));
        when(airCompanyRepository.findByName(airCompany.getName())).thenReturn(airCompany);

        assertThrows(AirCompanyWithThisNameAlreadyExistsException.class, () -> airCompanyService.updateAirCompany(airCompany, airCompany.getId()));
    }

    @Test
    public void testDeleteByIdSuccess() {
        AirCompany airCompany = new AirCompany();
        airCompany.setId(ID);

        when(airCompanyRepository.findById(airCompany.getId())).thenReturn(Optional.of(airCompany));
        assertDoesNotThrow(() -> airCompanyService.deleteAirCompanyById(airCompany.getId()));
    }

    @Test
    public void testfindTheCompanyAndItsFlightsByStatusSuccess() {
        List<Flight> flights = new ArrayList<>();
        List<AirCompany> airCompanies = new ArrayList<>();
        AirCompany airCompanyAfter = new AirCompany();
        airCompanies.add(airCompanyAfter);
        Flight flight = new Flight();
        flight.setId(FLIGHT_ID);
        flight.setStatus(Status.PENDING);
        flight.setAirCompany(airCompanyAfter);
        flights.add(flight);

        airCompanyAfter.setName(AIR_COMPANY_NAME);
        airCompanyAfter.setFlightsAirCompany(flights);

        when(flightRepository.findByStatusAndByCompanyId(flight.getStatus(), airCompanyAfter)).thenReturn(flights);

        when(airCompanyService.findAllAirCompany()).thenReturn(airCompanies);

        List<AirCompany> result = airCompanyService.findTheCompanyAndItsFlightsByStatus(flight.getStatus());

        for (AirCompany a : result) {
            assertEquals(a.getName(), airCompanyAfter.getName());
            assertArrayEquals(a.getFlightsAirCompany().toArray(), airCompanyAfter.getFlightsAirCompany().toArray());
        }
    }

    @Test
    public void testFindByAirCompanyNameFailAirCompanyWithThisNameAlreadyExistsException(){
        AirCompany airCompany = new AirCompany();
        airCompany.setId(ID);
        airCompany.setName(AIR_COMPANY_NAME);

        when(airCompanyRepository.findByName(airCompany.getName())).thenReturn(airCompany);

        assertThrows(AirCompanyWithThisNameAlreadyExistsException.class , () -> airCompanyService.findByAirCompanyName(airCompany));
    }

}
