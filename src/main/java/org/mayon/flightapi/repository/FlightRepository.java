package org.mayon.flightapi.repository;

import org.mayon.flightapi.model.Flight;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface FlightRepository extends JpaRepository<Flight, Integer> {
    Optional<List<Flight>> findByOrigin(String origin);
}
