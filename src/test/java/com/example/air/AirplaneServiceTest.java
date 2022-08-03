package com.example.air;


import com.example.air.entity.AirCompany;
import com.example.air.entity.Airplane;
import com.example.air.repository.AirplaneRepository;
import com.example.air.service.AirCompanyService;
import com.example.air.service.AirplaneService;
import com.example.air.exception.AirCompanyNotFoundException;
import com.example.air.exception.AirplaneNotFoundException;
import com.example.air.exception.AirplaneWithThisNameAlreadyExistsException;
import com.example.air.exception.AirplaneWithThisSerialNumberAlreadyExistsException;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class AirplaneServiceTest {
    private final static Long ID = 1L;
    private final static Long AIR_COMPANY_ID = 2L;
    private final static String NAME = "NAME";
    private final static Long FACTORY_SERIAL_NUMBER = 111L;

    @InjectMocks
    private AirplaneService airplaneService;

    @Mock
    private AirCompanyService airCompanyService;

    @Mock
    private AirplaneRepository airplaneRepository;

    @Test
    public void testFindAll() {
        Airplane airplane = new Airplane();
        airplane.setId(ID);

        List<Airplane> airplanes = new ArrayList<>();
        airplanes.add(airplane);

        when(airplaneRepository.findAll()).thenReturn(airplanes);
        List<Airplane> result = airplaneService.findAllAirplanes();

        assertEquals(airplanes, result);
    }

    @Test
    public void testFindByIdSuccess() throws AirplaneNotFoundException {
        Airplane airplane = new Airplane();
        airplane.setId(ID);

        when(airplaneRepository.findById(airplane.getId())).thenReturn(Optional.of(airplane));

        Airplane result = airplaneService.findAirplaneById(airplane.getId());

        assertEquals(airplane, result);
    }

    @Test
    public void testFindByIdFailAirplaneNotFoundException() {
        Airplane airplane = new Airplane();
        airplane.setId(ID);

        when(airplaneRepository.findById(airplane.getId())).thenReturn(Optional.empty());

        assertThrows(AirplaneNotFoundException.class, () -> airplaneService.findAirplaneById(airplane.getId()));
    }

    @Test
    public void saveAirplaneTestSuccess() throws AirplaneWithThisNameAlreadyExistsException, AirplaneWithThisSerialNumberAlreadyExistsException {
        Airplane before = new Airplane();
        before.setName(NAME);
        before.setFactorySerialNumber(FACTORY_SERIAL_NUMBER);

        Airplane after = new Airplane();
        after.setName(NAME);
        after.setId(ID);
        before.setFactorySerialNumber(FACTORY_SERIAL_NUMBER);

        when(airplaneRepository.findByName(before.getName())).thenReturn(null);
        when(airplaneRepository.findByFactorySerialNumber(before.getFactorySerialNumber())).thenReturn(null);
        when(airplaneRepository.save(before)).thenReturn(after);

        Airplane result = airplaneService.saveAirplane(before);

        assertEquals(after, result);
    }

    @Test
    public void testSaveAirplaneFailAirplaneWithThisNameAlreadyExists() {
        Airplane airplane = new Airplane();
        airplane.setName(NAME);

        when(airplaneRepository.findByName(airplane.getName())).thenReturn(airplane);

        assertThrows(AirplaneWithThisNameAlreadyExistsException.class, () -> airplaneService.saveAirplane(airplane));
    }

    @Test
    public void testSaveAirPlaneAirplaneWithThisSerialNumberAlreadyExists() {
        Airplane airplane = new Airplane();
        airplane.setFactorySerialNumber(FACTORY_SERIAL_NUMBER);

        when(airplaneRepository.findByFactorySerialNumber(airplane.getFactorySerialNumber())).thenReturn(airplane);

        assertThrows(AirplaneWithThisSerialNumberAlreadyExistsException.class, () -> airplaneService.saveAirplane(airplane));
    }

    @Test
    public void testUpdateAirplaneSuccess() throws AirplaneWithThisNameAlreadyExistsException, AirplaneNotFoundException, AirplaneWithThisSerialNumberAlreadyExistsException {
        Airplane before = new Airplane();
        before.setName(NAME);
        before.setId(ID);
        before.setFactorySerialNumber(FACTORY_SERIAL_NUMBER);

        Airplane after = new Airplane();
        after.setName(NAME);
        after.setId(ID);
        after.setFactorySerialNumber(FACTORY_SERIAL_NUMBER);

        when(airplaneRepository.findById(before.getId())).thenReturn(Optional.of(before));
        when(airplaneRepository.findByFactorySerialNumber(before.getFactorySerialNumber())).thenReturn(null);
        when(airplaneRepository.findByName(before.getName())).thenReturn(null);
        when(airplaneRepository.save(before)).thenReturn(after);

        Airplane result = airplaneService.updateAirplane(before, before.getId());

        assertEquals(after, result);
    }

    @Test
    public void testUpdateAirplaneFailAirplaneWithThisNameAlreadyExists() {
        Airplane airplane = new Airplane();
        airplane.setName(NAME);

        when(airplaneRepository.findById(airplane.getId())).thenReturn(Optional.of(airplane));
        when(airplaneRepository.findByName(airplane.getName())).thenReturn(airplane);

        assertThrows(AirplaneWithThisNameAlreadyExistsException.class, () -> airplaneService.updateAirplane(airplane, airplane.getId()));
    }

    @Test
    public void testUpdateAirplaneFailAirplaneWithThisSerialNumberAlreadyExists() {
        Airplane airplane = new Airplane();
        airplane.setFactorySerialNumber(FACTORY_SERIAL_NUMBER);

        when(airplaneRepository.findById(airplane.getId())).thenReturn(Optional.of(airplane));
        when(airplaneRepository.findByFactorySerialNumber(airplane.getFactorySerialNumber())).thenReturn(airplane);

        assertThrows(AirplaneWithThisSerialNumberAlreadyExistsException.class, () -> airplaneService.updateAirplane(airplane, airplane.getId()));
    }

    @Test
    public void testDeleteByIdSuccess() {
        Airplane airplane = new Airplane();
        airplane.setId(ID);

        when(airplaneRepository.findById(airplane.getId())).thenReturn(Optional.of(airplane));
        assertDoesNotThrow(() -> airplaneService.deleteAirplaneById(airplane.getId()));
    }

    @Test
    public void testChangeAirCompanyIdSuccess() throws AirplaneNotFoundException, AirCompanyNotFoundException {
        Airplane before = new Airplane();
        before.setId(ID);

        AirCompany airCompany = new AirCompany();
        airCompany.setId(AIR_COMPANY_ID);

        Airplane after = new Airplane();
        after.setId(ID);
        after.setAirCompany(airCompany);

        when(airplaneRepository.findById(before.getId())).thenReturn(Optional.of(before));
        when(airCompanyService.findAirCompanyById(airCompany.getId())).thenReturn(airCompany);
        when(airplaneRepository.save(before)).thenReturn(after);

        Airplane result = airplaneService.changeAirCompanyIdOfTheAirPlane(before.getId(), airCompany.getId());

        assertEquals(result, after);
    }

    @Test
    public void testChangeAirCompanyIdFailAirCompanyNotFoundException() throws AirCompanyNotFoundException {
        AirCompany airCompany = new AirCompany();
        airCompany.setId(AIR_COMPANY_ID);

        Airplane airplane = new Airplane();
        airCompany.setId(ID);
        airplane.setAirCompany(airCompany);

        when(airplaneRepository.findById(airplane.getId())).thenReturn(Optional.of(airplane));
        when(airCompanyService.findAirCompanyById(airCompany.getId())).thenReturn(null);

        assertThrows(AirCompanyNotFoundException.class, () -> airplaneService.changeAirCompanyIdOfTheAirPlane(airplane.getId(), airCompany.getId()));
    }


}
