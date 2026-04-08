package com.ridenow.rider_service.DTO;

public record DriverResponseDTO(
        Long id,

        String firstName,

        String lastName,

        String email,

        String mobileNumber
) {
}
