package com.offer_service.offer_service.offer;

import java.math.BigDecimal;

public record OfferResponse(
        Integer id,
        String dep_location,
        String arr_location,
        String time,
        BigDecimal price,
        String nb_place,
        Integer vehicle_Id,
        String matriculationNumber,
        String brand,
        String model,
        String color

) {

}
