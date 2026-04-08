package com.ridenow.ridebooking_service.model;


import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter @Setter
@Builder
public class RideBookings {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long bookingId;

    private Long driverId;

    private Long passengerId;

    @Builder.Default
    private LocalDateTime  startTime = LocalDateTime.now();

    @Builder.Default
    private LocalDateTime endTime = null;

    @Enumerated(EnumType.STRING)
    private RideBookingStatus status  =RideBookingStatus.IN_RIDE;

    private String pickUpLocation;
    private Double pickUpLocationLat;
    private Double pickUpLocationLon;

    private String dropOffLocation;
    private Double dropOffLocationLat;
    private Double dropOffLocationLon;

    private BigDecimal price;
}
