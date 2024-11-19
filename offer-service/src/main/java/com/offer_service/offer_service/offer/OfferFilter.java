package com.offer_service.offer_service.offer;

import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.springframework.boot.autoconfigure.condition.ConditionalOnNotWebApplication;

import java.math.BigDecimal;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
public class OfferFilter {
    private String depLocation;
    private String arrLocation;
    private BigDecimal minPrice;
    private BigDecimal maxPrice;
}
