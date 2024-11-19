package com.offer_service.offer_service.offer;

import java.math.BigDecimal;

public record OfferReservationResponse(
        Integer offerID,
        String depLocation,
        String arrLocation,
        String time,
        BigDecimal price,
        Integer reservedSeats,
        Integer remainingSeats,
        Integer vehicleId,
        String Brand,
        String Model
) {
}
