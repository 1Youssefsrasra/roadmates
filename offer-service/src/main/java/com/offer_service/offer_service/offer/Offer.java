package com.offer_service.offer_service.offer;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
@Entity
public class Offer {
    @Id
    @GeneratedValue
    private Integer id;
    private String dep_location;
    private String arr_location;
    private String time;
    private BigDecimal price;
    private Integer availableSeats;
    private String matriculationNumber;
    private String brand;
    private String color;


}
