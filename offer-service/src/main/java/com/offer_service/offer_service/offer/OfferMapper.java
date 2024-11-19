package com.offer_service.offer_service.offer;

import com.offer_service.offer_service.vehicle.Vehicle;
import org.springframework.stereotype.Service;

@Service
public class OfferMapper {

    /**
     * Converts an OfferRequest into an Offer entity.
     *
     * @param request the offer request
     * @return the Offer entity
     */
    public Offer toOffer(OfferRequest request) {
        return Offer.builder()
                .id(request.id())
                .dep_location(request.dep_location())
                .arr_location(request.arr_location())
                .time(request.time())
                .price(request.price())
                .nb_place(request.nb_place())
                .vehicle(
                        Vehicle.builder()
                                .vehicle_id(request.vehicleId())
                                .build())
                .build();
    }

    /**
     * Converts an Offer entity into an OfferResponse.
     *
     * @param offer the Offer entity
     * @return the OfferResponse
     */
    public OfferResponse toOfferResponse(Offer offer) {
        return new OfferResponse(
                offer.getId(),
                offer.getDep_location(),
                offer.getArr_location(),
                offer.getTime(),
                offer.getPrice(),
                offer.getNb_place(),
                offer.getVehicle().getVehicle_id(),
                offer.getVehicle().getBrand(),
                offer.getVehicle().getModel(),
                offer.getVehicle().getColor(),
                offer.getVehicle().getMatriculationNumber()
        );
    }

    /**
     * Updates an existing Offer entity with data from an OfferRequest.
     *
     * @param offer   the existing Offer entity
     * @param request the offer request with updated data
     */
    public void updateOfferFromRequest(Offer offer, OfferRequest request) {
        offer.setDep_location(request.dep_location());
        offer.setArr_location(request.arr_location());
        offer.setTime(request.time());
        offer.setPrice(request.price());
        offer.setNb_place(request.nb_place());

        if (offer.getVehicle() == null) {
            offer.setVehicle(new Vehicle());
        }

        offer.getVehicle().setVehicle_id(request.vehicleId());
    }

    /**
     * Converts an Offer and OfferReservationRequest into an OfferReservationResponse.
     *
     * @param offer   the reserved Offer entity
     * @param request the reservation request
     * @return the OfferReservationResponse
     */
    public OfferReservationResponse toOfferReservedResponse(Offer offer, OfferReservationRequest request) {
        // Convert nb_place from String to Integer if necessary
        Integer availableSeats = offer.getNb_place();

        // Calculate the remaining seats
        Integer remainingSeats = availableSeats - request.seatsRequested();

        return new OfferReservationResponse(
                offer.getId(),
                offer.getDep_location(),
                offer.getArr_location(),
                offer.getTime(),
                offer.getPrice(),
                request.seatsRequested(), // Number of seats requested
                remainingSeats,  // Remaining seats
                offer.getVehicle().getVehicle_id(),
                offer.getVehicle().getBrand(),
                offer.getVehicle().getModel()
        );
    }
}
