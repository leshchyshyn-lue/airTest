package com.example.air.repository;

import com.example.air.entity.AirCompany;
import com.example.air.util.Status;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AirCompanyRepository extends JpaRepository<AirCompany, Long> {
    AirCompany findByName(String name);



}
