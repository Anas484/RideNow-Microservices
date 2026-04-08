package com.ridenow.ridebooking_service.dto;

import com.ridenow.ridebooking_service.model.RideBookingStatus;

import java.math.BigDecimal;

public record RideBookingRequestDTO(
        Long passengerId,
        String pickUpLocation,
        String dropOffLocation
) {
}
