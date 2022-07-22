package com.example.air.service;

import com.example.air.entity.AirCompany;
import com.example.air.entity.Airplane;
import com.example.air.repository.AirplaneRepository;
import com.example.air.exception.AirCompanyNotFoundException;
import com.example.air.exception.AirplaneNotFoundException;
import com.example.air.exception.AirplaneWithThisNameAlreadyExistsException;
import com.example.air.exception.AirplaneWithThisSerialNumberAlreadyExistsException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AirplaneService {

    private final AirplaneRepository airplaneRepository;
    private final AirCompanyService airCompanyService;

    @Autowired
    public AirplaneService(AirplaneRepository airplaneRepository, AirCompanyService airCompanyService) {
        this.airplaneRepository = airplaneRepository;
        this.airCompanyService = airCompanyService;
    }

    public List<Airplane> findAllAirplanes() {
        return airplaneRepository.findAll();
    }

    public Airplane findAirplaneById(Long id) throws AirplaneNotFoundException {
        return airplaneRepository.findById(id)
                .orElseThrow(() -> new AirplaneNotFoundException("No airplane with ID "+ id +" was found"));
    }

    public Airplane saveAirplane(Airplane airplain) throws AirplaneWithThisNameAlreadyExistsException, AirplaneWithThisSerialNumberAlreadyExistsException {
        findByAirplaneName(airplain);
        findBySerialNumber(airplain);
        return airplaneRepository.save(airplain);
    }

    public Airplane updateAirplane(Airplane newAirplane, Long id) throws AirplaneNotFoundException, AirplaneWithThisNameAlreadyExistsException, AirplaneWithThisSerialNumberAlreadyExistsException {
        Airplane oldAirplane = findAirplaneById(id);
        findByAirplaneName(newAirplane);
        findBySerialNumber(newAirplane);
        oldAirplane.setCreatedAt(newAirplane.getCreatedAt());
        oldAirplane.setName(newAirplane.getName());
        oldAirplane.setFlightDistance(newAirplane.getFlightDistance());
        oldAirplane.setFuelCapacity(newAirplane.getFuelCapacity());
        oldAirplane.setFactorySerialNumber(newAirplane.getFactorySerialNumber());
        oldAirplane.setNumberOfFlights(newAirplane.getNumberOfFlights());
        oldAirplane.setType(newAirplane.getType());
        return airplaneRepository.save(oldAirplane);
    }

    public void deleteAirplaneById(Long id) throws AirplaneNotFoundException {
        findAirplaneById(id);
        airplaneRepository.deleteById(id);
    }

    public Airplane changeAirCompanyIdOfTheAirPlane(Long airplaneId, Long airCompanyId) throws AirplaneNotFoundException, AirCompanyNotFoundException {
        Airplane changed = findAirplaneById(airplaneId);
        AirCompany airCompany = airCompanyService.findAirCompanyById(airCompanyId);
        if (airCompany == null) {
            throw new AirCompanyNotFoundException("No air company with ID "+ airCompanyId +" was found");
        }
        changed.setAirCompany(airCompany);
        return airplaneRepository.save(changed);
    }

    public void findByAirplaneName(Airplane airplane) throws AirplaneWithThisNameAlreadyExistsException {
        Airplane name = airplaneRepository.findByName(airplane.getName());
        if (name != null) {
            throw new AirplaneWithThisNameAlreadyExistsException("Airplane with this name already exists");
        }
    }
    public void findBySerialNumber(Airplane airplane) throws AirplaneWithThisSerialNumberAlreadyExistsException {
        Airplane serialNumber = airplaneRepository.findByFactorySerialNumber(airplane.getFactorySerialNumber());
        if (serialNumber != null) {
            throw new AirplaneWithThisSerialNumberAlreadyExistsException("Airplane with this serial number already exists");
        }
    }
}
