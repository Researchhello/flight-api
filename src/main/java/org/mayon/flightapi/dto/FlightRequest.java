package org.mayon.flightapi.dto;

import lombok.*;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class FlightRequest {
    private String flight;
    private String origin;
    private String destination;
    private List<Integer> speedSeries;

}
