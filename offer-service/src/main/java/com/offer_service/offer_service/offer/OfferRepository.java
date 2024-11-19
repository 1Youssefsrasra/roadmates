package com.offer_service.offer_service.offer;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OfferRepository extends JpaRepository<Offer, Integer> {
    List<Offer> findAllByIdInOrderById( List<Integer> offerIds );
    List<Offer> findAllByPriceBetween(Double minPrice, Double maxPrice);
}
