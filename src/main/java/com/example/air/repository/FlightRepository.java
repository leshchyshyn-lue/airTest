package com.example.air.repository;

import com.example.air.entity.AirCompany;
import com.example.air.util.Status;
import com.example.air.entity.Flight;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FlightRepository extends JpaRepository<Flight, Long> {
    @Query(value = "SELECT * FROM flight WHERE status = 3 and DATEDIFF(ended_at, delay_started_at) != estimated_flight_time", nativeQuery = true)
    List<Flight> findAllFlightsWithStatusComplete(Status status);

    @Query(value = "SELECT * FROM flight WHERE status = 0 and DATEDIFF(NOW(), delay_started_at) >= 1", nativeQuery = true)
    List<Flight> findFlightsWithStatusActive(Status status);

    List<Flight> findByStatus(Status status);
    @Query(value = "SELECT flight FROM Flight flight WHERE flight.status = ?1 AND flight.airCompany = ?2")
    List<Flight> findByStatusAndByCompanyId(Status status, AirCompany airCompany);
}
