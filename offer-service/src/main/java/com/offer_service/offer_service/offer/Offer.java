package com.offer_service.offer_service.offer;

import com.offer_service.offer_service.vehicle.Vehicle;
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
    private String nb_place;
    private Integer availableSeats;

    @ManyToOne
    @JoinColumn(name = "vehicle_id")
    private Vehicle vehicle;



}
