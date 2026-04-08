package com.ridenow.rider_service.DTO;

public record SignUpProcessDTO(
        String email,
        String firstName,
        String lastName,
        String password,
        String mobileNumber,
        String role
) {
}
