package org.mayon.flightapi.service;

import org.mayon.flightapi.dto.FlightRequest;
import org.mayon.flightapi.dto.FlightResponse;
import org.mayon.flightapi.exception.FlightNotFoundException;
import org.mayon.flightapi.model.Flight;
import org.mayon.flightapi.repository.FlightRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Slf4j
public class FlightService {
    private final FlightRepository flightRepository;
    private final FlightMapper mapper;
    public FlightService(FlightRepository flightRepository, FlightMapper mapper) {
        this.flightRepository = flightRepository;
        this.mapper = mapper;
    }


    public FlightResponse createFlightTravel(FlightRequest request) {
        Flight flight = flightRepository.save(mapper.toFlightDAO(request));
        return mapper.toFlightDTO(flight);
    }

    public FlightResponse getFlight(Integer flightId) {
        log.info("Check flight {}", flightId);
        Optional<Flight> flight = flightRepository.findById(flightId);
        if(flight.isEmpty()) {
            throw new FlightNotFoundException("Flight Id doest not exist");
        }
        return mapper.toFlightDTO(flight.get());
    }

    public List<FlightResponse> getFlights(String originName, String orderBy) {
        log.info("Check flights {}", originName);
        if(originName == null && orderBy == null) {
            return getAllFlightsByIDInIncreasingOrder();
        } else if (!StringUtils.hasLength(originName) && StringUtils.hasLength(orderBy)) {
            if(originName == null && orderBy.equals("destination")) {
                return getAllFlightsOrderByDestination();
            } else if(orderBy.equals("-destination")) {
                return getAllFlightsOrderByDestinationDesc();
            }
        }else if (!StringUtils.hasLength(orderBy) && StringUtils.hasLength(originName)){
            Optional<List<Flight>> flights = flightRepository.findByOrigin(originName);
            if(flights.isEmpty()) {
                throw new FlightNotFoundException("Flight origin doest not exist");
            } else {
                return getAllFlightsWithOrigin(flights.get());
            }

        }
        return List.of();
    }

    private List<FlightResponse> getAllFlightsWithOrigin(List<Flight> flights) {
        return flights
                .stream()
                .map(mapper::toFlightDTO).collect(Collectors.toList());
    }

    private List<FlightResponse> getAllFlightsOrderByDestinationDesc() {
        return flightRepository.findAll().stream()
                .sorted(Comparator.comparing(Flight::getDestination).reversed().thenComparing(Flight::getId))
                .map(mapper::toFlightDTO).collect(Collectors.toList());
    }

    private List<FlightResponse> getAllFlightsOrderByDestination() {
        return flightRepository.findAll().stream()
                .sorted(Comparator.comparing(Flight::getDestination))
                .map(mapper::toFlightDTO).collect(Collectors.toList());
    }

    private List<FlightResponse> getAllFlightsByIDInIncreasingOrder() {
        return flightRepository.findAll().stream()
                .sorted(Comparator.comparing(Flight::getId))
                .map(mapper::toFlightDTO).collect(Collectors.toList());
    }

    public Page<Flight> searchFlights(PageRequest pageRequest) {
        return flightRepository.findAll(pageRequest);
    }

    public Slice<Flight> searchOnlyFlights(Pageable pageable) {
        return flightRepository.findAll(pageable);
    }
}
