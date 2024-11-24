package com.offer_service.offer_service.offer;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/offer")
public class offerController {

    private final OfferService service;
    private final OfferRepository offerRepository;


    // Constructor to initialize both service and repository
    public offerController(OfferService service, OfferRepository offerRepository) {
        this.service = service;
        this.offerRepository = offerRepository;
    }


    @PostMapping
    public ResponseEntity<Integer> createOffer(@RequestBody @Valid OfferRequest request) {
        return ResponseEntity.ok(service.createOffer(request));
    }



    @GetMapping("/{offer-id}")
    public ResponseEntity<OfferResponse> findById(
            @PathVariable("offer-id") Integer offerId
    ){
        return ResponseEntity.ok(service.findById(offerId));
    }

    @GetMapping
    public ResponseEntity<List<OfferResponse>> findAll(){
        return ResponseEntity.ok(service.findAll());
    }

    @PutMapping("/{id}/update-seats")
    public ResponseEntity<Void> updateAvailableSeats(
            @PathVariable Integer id,
            @RequestParam Integer newAvailableSeats
    ) {
        Offer offer = offerRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Offer not found"));

        offer.setAvailableSeats(newAvailableSeats);
        offerRepository.save(offer);

        return ResponseEntity.ok().build();
    }


}
