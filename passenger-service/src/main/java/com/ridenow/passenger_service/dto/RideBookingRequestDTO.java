package com.ridenow.passenger_service.dto;

public record RideBookingRequestDTO(
        Long passengerId,
        String pickUpLocation,
        String dropOffLocation
) {
}
