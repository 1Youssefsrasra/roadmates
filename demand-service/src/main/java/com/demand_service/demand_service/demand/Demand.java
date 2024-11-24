package com.demand_service.demand_service.demand;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Builder
@Setter
@Entity
@EntityListeners(AuditingEntityListener.class)
public class Demand {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(name = "user_id", nullable = false)
    private String userId;
    @Column(name = "offer_id", nullable = false)
    private Integer offerId;
    @Enumerated(EnumType.STRING)
    private DemandStatus status;
    private Integer seatsRequested;
    private LocalDateTime createdAt;
}
