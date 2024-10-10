package org.mayon.flightapi.dto;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class FlightResponse extends FlightRequest{
    private Integer id;
}
