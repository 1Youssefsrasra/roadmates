package com.demand_service.demand_service.demand;

import com.demand_service.demand_service.Offer.OfferClient;
import com.demand_service.demand_service.Offer.OfferResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/demand")
@RequiredArgsConstructor
public class demandController {

    private final DemandService service;
    private final DemandRepository repository;
    private final OfferClient offerClient;

    @PostMapping
    public ResponseEntity<DemandResponse> createDemand(@RequestBody @Valid DemandRequest request) {
        // Check availability of seats by calling Offer Service
        OfferResponse offer = offerClient.getOffer(request.offerId());

        if (offer.availableSeats() < request.seatsRequested()) {
            throw new RuntimeException("Not enough available seats");
        }

        DemandResponse response = service.createDemand(request);
        return ResponseEntity.ok(response);
    }

    @PutMapping("/{demandId}/approve")
    public ResponseEntity<DemandResponse> approveDemand(@PathVariable Integer demandId, @RequestParam boolean approve) {
        DemandResponse response = service.approveOrDenyDemand(demandId, approve);
        return ResponseEntity.ok(response);
    }

    // Get demands by user ID
    @GetMapping("/by-user/{userId}")
    public ResponseEntity<List<DemandResponse>> getDemandsByUser(@PathVariable String userId) {
        // Ensure that the user ID is valid and that you handle any exceptions that might occur during DB access
        try {
            List<Demand> demands = repository.findByUserId(userId);
            List<DemandResponse> demandResponses = demands.stream()
                    .map(this::mapToDemandResponse)
                    .collect(Collectors.toList());

            // Wrap the result in ResponseEntity
            return ResponseEntity.ok(demandResponses);
        } catch (Exception e) {
            // Log the exception and rethrow or handle it gracefully
            throw new RuntimeException("Error retrieving demands for user: " + userId, e);
        }
    }

    private DemandResponse mapToDemandResponse(Demand demand) {
        return new DemandResponse(
                demand.getId(),              // Integer id
                demand.getUserId(),          // String userId
                demand.getOfferId(),         // Integer offerId
                demand.getStatus(),
                demand.getSeatsRequested(),// DemandStatus status
                demand.getCreatedAt()        // LocalDateTime createdAt
        );
    }

    public void deleteDemand(Integer id) {
        // Delete demand by ID
        repository.deleteById(id);
    }
}
