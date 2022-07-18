package com.example.air.repository;

import com.example.air.Status;
import com.example.air.entity.Flight;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FlightRepository extends JpaRepository<Flight, Long> {
    @Query(value = "SELECT * FROM flight WHERE status = 3 and DATEDIFF(ended_at, delay_started_at) != estimated_flight_time", nativeQuery = true)
    List<Flight> findAllWithStatusComplete(Status status);

    @Query(value = "SELECT * FROM flight WHERE status = 0 and DATEDIFF(NOW(), delay_started_at) >= 1", nativeQuery = true)
    List<Flight> findFlightActive(Status status);

    List<Flight> findByStatus(Status status);

}
