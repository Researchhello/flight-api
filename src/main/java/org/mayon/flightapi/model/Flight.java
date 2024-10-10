package org.mayon.flightapi.model;

import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.*;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
@Entity
public class Flight {
    @Id
    @GeneratedValue
    private Integer id;
    private String flight;
    private String origin;
    private String destination;
    @ElementCollection
    private List<Integer> speedSeries;
}
