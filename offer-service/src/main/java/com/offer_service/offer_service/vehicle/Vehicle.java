package com.offer_service.offer_service.vehicle;

import com.offer_service.offer_service.offer.Offer;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
@Entity
public class Vehicle {
    @Id
    @GeneratedValue
    private Integer vehicle_id;
    private String matriculationNumber;
    private String brand;
    private String model;
    private String color;

    @OneToMany(mappedBy = "vehicle", cascade = CascadeType.REMOVE)
    private List<Offer> offers;
}
