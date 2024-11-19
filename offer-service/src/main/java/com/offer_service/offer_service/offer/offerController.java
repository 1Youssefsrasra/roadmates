package com.offer_service.offer_service.offer;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/offer")
@RequiredArgsConstructor
public class offerController {

    private final OfferService service;

    @PostMapping
    public ResponseEntity<Integer> createOffer(@RequestBody @Valid OfferRequest request) {
        return ResponseEntity.ok(service.createOffer(request));
    }

    @PostMapping("/reserve")
    public ResponseEntity<List<OfferReservationResponse>> reserveOffer(@RequestBody List<OfferReservationRequest> request){
        return ResponseEntity.ok(service.reserveOffer(request));
    }

    @GetMapping("{offer-id}")
    public ResponseEntity<OfferResponse> findById(
            @PathVariable("offer-id") Integer offerId
    ){
        return ResponseEntity.ok(service.findById(offerId));
    }

    @GetMapping
    public ResponseEntity<List<OfferResponse>> findAll(){
        return ResponseEntity.ok(service.findAll());
    }




}
