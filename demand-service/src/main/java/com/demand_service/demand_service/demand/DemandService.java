package com.demand_service.demand_service.demand;

import com.demand_service.demand_service.Offer.OfferClient;
import com.demand_service.demand_service.Offer.OfferResponse;
import com.demand_service.demand_service.exception.BusinessException;
import com.demand_service.demand_service.kafka.DemandConfirmation;
import com.demand_service.demand_service.kafka.DemandProducer;
import com.demand_service.demand_service.user.UserClient;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class DemandService {

    private final OfferClient offerClient;
    private final DemandRepository demandRepository;

    public DemandService(DemandRepository demandRepository, OfferClient offerClient) {
        this.demandRepository = demandRepository;
        this.offerClient = offerClient;
    }


    @Value("${application.config.offer-url}")
    private String offerUrl;

    public DemandResponse createDemand(DemandRequest request) {
        // Fetch the offer details using Feign client
        OfferResponse offer = offerClient.getOffer(request.offerId());

        // Create a new demand
        Demand demand = new Demand();
        demand.setOfferId(request.offerId());
        demand.setUserId(request.userId());
        demand.setSeatsRequested(request.seatsRequested());
        demand.setStatus(DemandStatus.PENDING);

        // Save the demand to the database
        demandRepository.save(demand);

        // Return the DemandResponse
        return new DemandResponse(
                demand.getId(),
                demand.getUserId(),
                demand.getOfferId(),
                demand.getStatus(),
                demand.getSeatsRequested(),
                demand.getCreatedAt()

        );
    }


    public List<DemandResponse> getDemandsByOffer(Integer offerId) {
        // Rechercher les demandes pour une annonce spécifique
        return demandRepository.findByOfferId(offerId)
                .stream()
                .map(this::mapToResponse)
                .toList();
    }

    public List<DemandResponse> getDemandsByUser(String userId) {
        // Rechercher les demandes pour un utilisateur spécifique
        return demandRepository.findByUserId(userId)
                .stream()
                .map(this::mapToResponse)
                .toList();
    }

    private DemandResponse mapToResponse(Demand demand) {
        return new DemandResponse(
                demand.getId(),               // Integer id
                demand.getUserId(),           // String userId
                demand.getOfferId(),          // Integer offerId
                demand.getStatus(),           // DemandStatus status
                demand.getSeatsRequested(),   // Integer seatsRequested
                demand.getCreatedAt()         // LocalDateTime createdAt
        );
    }

    public DemandResponse approveOrDenyDemand(Integer demandId, boolean approve) {
        // Fetch the demand from the Demand microservice's repository
        Demand demand = demandRepository.findById(demandId)
                .orElseThrow(() -> new RuntimeException("Demand not found"));

        // Fetch the related offer from the Offer microservice using Feign client
        OfferResponse offer = offerClient.getOffer(demand.getOfferId());

        if (approve) {
            // Check if there are enough seats available
            if (offer.availableSeats() >= demand.getSeatsRequested()) {
                // Update available seats in the Offer microservice
                offerClient.updateAvailableSeats(
                        offer.getId(),
                        offer.availableSeats() - demand.getSeatsRequested()
                );
                // Approve the demand
                demand.setStatus(DemandStatus.APPROVED);
            } else {
                // Deny the demand due to insufficient seats
                demand.setStatus(DemandStatus.DENIED);
            }
        } else {
            // Deny the demand as per the user's choice
            demand.setStatus(DemandStatus.DENIED);
        }

        // Save the updated demand
        demandRepository.save(demand);

        // Return the updated demand response
        return new DemandResponse(
                demand.getId(),
                demand.getUserId(),
                demand.getOfferId(),
                demand.getStatus(),
                demand.getSeatsRequested(),
                demand.getCreatedAt()
        );
    }
}
