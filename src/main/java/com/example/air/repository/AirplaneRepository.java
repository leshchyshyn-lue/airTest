package com.example.air.repository;

import com.example.air.entity.Airplane;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AirplaneRepository extends JpaRepository<Airplane, Long> {
    Airplane findByName(String name);

    Airplane findByFactorySerialNumber(Long number);

}
