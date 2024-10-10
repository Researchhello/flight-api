package org.mayon.flightapi.service;

import org.mayon.flightapi.dto.FlightRequest;
import org.mayon.flightapi.dto.FlightResponse;
import org.mayon.flightapi.model.Flight;
import org.springframework.stereotype.Service;

@Service
public class FlightMapper {
    public Flight toFlightDAO(FlightRequest request) {

        return Flight.builder()
                .flight(request.getFlight())
                .origin(request.getOrigin())
                .destination(request.getDestination())
                .speedSeries(request.getSpeedSeries())
                .build();
    }

    public FlightResponse toFlightDTO(Flight flight) {
        FlightResponse response = new FlightResponse();
        response.setId(flight.getId());
        response.setFlight(flight.getFlight());
        response.setOrigin(flight.getOrigin());
        response.setDestination(flight.getDestination());
        response.setSpeedSeries(flight.getSpeedSeries());
        return response;
    }
}
