package com.offer_service.offer_service.offer;

import jakarta.validation.constraints.NotNull;

public record OfferReservationRequest(
        @NotNull(message = "Offer is mandatory")
        Integer offerID,
        @NotNull(message = "Location is mandatory")

        String dep_location,
        @NotNull(message = "Location is mandatory")

        String arr_location,
        @NotNull(message = "the number of Seats requested is mandatory")
        Integer seatsRequested

) {
}
