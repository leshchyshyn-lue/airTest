package com.example.air.repository;

import com.example.air.entity.AirCompany;
import org.springframework.data.jpa.repository.JpaRepository;


public interface AirCompanyRepository extends JpaRepository<AirCompany, Long> {
    AirCompany findByAirCompanyName(String name);

}
