package com.offer_service.offer_service.offer;

import com.offer_service.offer_service.exception.OfferReservationException;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class OfferService {

    private final OfferRepository repository;
    private final OfferMapper mapper;
    public Integer createOffer(OfferRequest request) {
        var offer = mapper.toOffer(request);
        offer.setAvailableSeats(Integer.valueOf(offer.getNb_place()));
        return repository.save(offer).getId();
    }

    public List<OfferReservationResponse> reserveOffer(List<OfferReservationRequest> request) {
        var offerIds= request
                .stream()
                .map(OfferReservationRequest::offerID)
                .toList();

        var storedOffers = repository.findAllByIdInOrderById(offerIds);
        if (offerIds.size() != storedOffers.size()){
            throw new OfferReservationException("One or more offers does not exists");
        }

        // Sort the requests and offers to ensure matching order
        var sortedRequests = request.stream()
                .sorted(Comparator.comparing(OfferReservationRequest::offerID))
                .toList();

        var reservedOffers = new ArrayList<OfferReservationResponse>();
        for (int i = 0; i < storedOffers.size(); i++) {
            var offer = storedOffers.get(i);
            var offerRequest = sortedRequests.get(i);

            // Check if the offer can be reserved (e.g., check seat availability)
            if (offer.getAvailableSeats() < offerRequest.seatsRequested()) {
                throw new OfferReservationException("Insufficient seats for offer ID: " + offer.getId());
            }

            // Reserve the seats and update the offer
            offer.setAvailableSeats(offer.getAvailableSeats() - offerRequest.seatsRequested());
            repository.save(offer);

            // Add the reserved offer response
            reservedOffers.add(mapper.toOfferReservedResponse(offer, offerRequest));
        }

        return reservedOffers;
    }

    public OfferResponse findById(Integer offerId) {
        return repository.findById(offerId)
                .map(mapper::toOfferResponse)
                .orElseThrow(() -> new EntityNotFoundException("Offer not found with the ID:: " + offerId));

    }

    public List<OfferResponse> findAll() {
        return repository.findAll()
                .stream()
                .map(mapper::toOfferResponse)
                .collect(Collectors.toList());
    }


    public OfferResponse updateOffer(Integer offerId, OfferRequest request) {
        var offer = repository.findById(offerId)
                .orElseThrow(() -> new EntityNotFoundException("Offer not found with the ID: " + offerId));

        mapper.updateOfferFromRequest(offer, request); // Update fields using the mapper
        var updatedOffer = repository.save(offer);

        return mapper.toOfferResponse(updatedOffer);
    }
    public void deleteOffer(Integer offerId) {
        if (!repository.existsById(offerId)) {
            throw new EntityNotFoundException("Offer not found with the ID: " + offerId);
        }
        repository.deleteById(offerId);
    }

    public List<OfferResponse> findOffersByPriceRange(Double minPrice, Double maxPrice) {
        return repository.findAllByPriceBetween(minPrice, maxPrice)
                .stream()
                .map(mapper::toOfferResponse)
                .collect(Collectors.toList());
    }

    public List<OfferResponse> filterOffers(OfferFilter filter) {
        return repository.findByFilterCriteria(filter)
                .stream()
                .map(mapper::toOfferResponse)
                .collect(Collectors.toList());
    }




}

