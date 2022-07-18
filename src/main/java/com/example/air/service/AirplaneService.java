package com.example.air.service;

import com.example.air.entity.AirCompany;
import com.example.air.entity.Airplane;
import com.example.air.repository.AirplaneRepository;
import com.example.air.util.AirCompanyNotFoundException;
import com.example.air.util.AirplaneNotFoundException;
import com.example.air.util.AirplaneWithThisNameAlreadyExists;
import com.example.air.util.AirplaneWithThisSerialNumberAlreadyExists;
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

    public List<Airplane> findAll() {
        return airplaneRepository.findAll();
    }

    public Airplane findById(Long id) throws AirplaneNotFoundException {
        return airplaneRepository.findById(id)
                .orElseThrow(() -> new AirplaneNotFoundException("No airplane with this ID was found"));
    }

    public Airplane saveAirplane(Airplane airplain) throws AirplaneWithThisNameAlreadyExists, AirplaneWithThisSerialNumberAlreadyExists {
        Airplane byName = airplaneRepository.findByName(airplain.getName());
        if (byName != null) {
            throw new AirplaneWithThisNameAlreadyExists("Airplane with this name already exists");
        }
        Airplane bySerialNumber = airplaneRepository.findByFactorySerialNumber(airplain.getFactorySerialNumber());
        if (bySerialNumber != null) {
            throw new AirplaneWithThisSerialNumberAlreadyExists("Airplane with this serial number already exists");
        }
        return airplaneRepository.save(airplain);
    }

    public Airplane updateAirplane(Airplane newAirplane, Long id) throws AirplaneNotFoundException, AirplaneWithThisNameAlreadyExists, AirplaneWithThisSerialNumberAlreadyExists {
        Airplane oldAirplane = findById(id);
        Airplane byName = airplaneRepository.findByName(newAirplane.getName());
        if (byName != null) {
            throw new AirplaneWithThisNameAlreadyExists("Airplane with this name already exists");
        }
        Airplane bySerialNumber = airplaneRepository.findByFactorySerialNumber(newAirplane.getFactorySerialNumber());
        if (bySerialNumber != null) {
            throw new AirplaneWithThisSerialNumberAlreadyExists("Airplane with this serial number already exists");
        }
        oldAirplane.setCreatedAt(newAirplane.getCreatedAt());
        oldAirplane.setName(newAirplane.getName());
        oldAirplane.setFlightDistance(newAirplane.getFlightDistance());
        oldAirplane.setFuelCapacity(newAirplane.getFuelCapacity());
        oldAirplane.setFactorySerialNumber(newAirplane.getFactorySerialNumber());
        oldAirplane.setNumberOfFlights(newAirplane.getNumberOfFlights());
        oldAirplane.setType(newAirplane.getType());
        return airplaneRepository.save(oldAirplane);
    }

    public void deleteById(Long id) throws AirplaneNotFoundException {
        findById(id);
        airplaneRepository.deleteById(id);
    }

    public Airplane changeAirCompanyId(Long airplaneId, Long airCompanyId) throws AirplaneNotFoundException, AirCompanyNotFoundException {
        Airplane changed = findById(airplaneId);
        AirCompany airCompany = airCompanyService.findById(airCompanyId);
        if (airCompany == null) {
            throw new AirCompanyNotFoundException("No air company with this ID was found");
        }
        changed.setAirCompanyId(airCompany);
        return airplaneRepository.save(changed);
    }
}
