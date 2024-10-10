package org.mayon.flightapi.controller;

import org.mayon.flightapi.dto.FlightRequest;
import org.mayon.flightapi.dto.FlightResponse;
import org.mayon.flightapi.model.Flight;
import org.mayon.flightapi.model.SortField;
import org.mayon.flightapi.service.FlightService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Slice;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/flight")
@Slf4j
public class FlightController {

    private final FlightService service;

    public FlightController(FlightService service) {
        this.service = service;
    }

    @PostMapping
    public ResponseEntity<FlightResponse> createFlightTravel(@RequestBody FlightRequest request) {
        log.info("Creating flight request: {}", request);
        FlightResponse response = service.createFlightTravel(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping("/{flight-id}")
    public ResponseEntity<FlightResponse> getFlight(@PathVariable("flight-id") Integer flightId) {
        log.info("Retrieving flight with id: {}", flightId);
        return ResponseEntity.ok(service.getFlight(flightId));
    }

    @GetMapping
    public ResponseEntity<List<FlightResponse>> getFlights(
            @RequestParam(value = "origin", required = false)  String originName,
            @RequestParam(value = "orderBy", required = false)  String orderBy
    ) {
        return ResponseEntity.ok(service.getFlights(originName, orderBy));
    }

    @GetMapping("/search")
    public ResponseEntity<Page<Flight>> searchFlights(
            @RequestParam(value = "pageNumber", required = false, defaultValue = "0")  Integer pageNumber,
            @RequestParam(value = "pageCount", required = false, defaultValue = "10")  Integer pageCount,
            @RequestParam(value = "sortBy", required = false, defaultValue = "id")  String sortBy
    ) {
        return ResponseEntity.ok(service.searchFlights(PageRequest.of(pageNumber, pageCount, Sort.by(sortBy))));
    }

    @GetMapping("/searchonly")
    public ResponseEntity<Slice<Flight>> searchOnlyFlights(
            @RequestParam(value = "pageNumber", defaultValue = "0")  Integer pageNumber,
            @RequestParam(value = "pageCount", defaultValue = "10")  Integer pageCount,
            @RequestParam(defaultValue = "ID") String sortField,
            @RequestParam(defaultValue = "DESC") Sort.Direction sortDirection
    ) {
        return ResponseEntity.ok(service.searchOnlyFlights(PageRequest.of(pageNumber, pageCount,sortDirection, SortField.fromValue(sortField).getSortField())));
    }


}
