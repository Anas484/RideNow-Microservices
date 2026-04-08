package com.ridenow.auth_service.DTO;

public record SignUpRequestDTO(
        String email,
        String firstName,
        String lastName,
        String password,
        String mobileNumber,
        String role


) {
}
