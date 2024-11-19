package com.offer_service.offer_service.offer;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.math.BigDecimal;

public record OfferRequest(
    Integer id,
    @NotNull(message = "Departure location is required")
    String dep_location,
    @NotNull(message = "Arrival location is required")
    String arr_location,
    @NotNull(message = "Time is required")
    String time,
    @Positive(message = "Price should be positive")
    BigDecimal price,
    @NotNull(message = "Nb places is required")
    Integer nb_place,
    @NotNull(message = "Vehicle is required")
    Integer vehicleId
    )
{
}
