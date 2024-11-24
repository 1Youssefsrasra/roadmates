package com.offer_service.offer_service.offer;

import com.offer_service.offer_service.exception.OfferReservationException;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class OfferService {

    @Autowired
    private final OfferRepository repository;
    private final OfferMapper mapper;
    public Integer createOffer(OfferRequest request) {
        var offer = mapper.toOffer(request);
        return repository.save(offer).getId();
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







}

