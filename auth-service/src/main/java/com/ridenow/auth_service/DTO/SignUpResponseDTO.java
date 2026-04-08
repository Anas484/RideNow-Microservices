package com.ridenow.auth_service.DTO;

public record SignUpResponseDTO(
        Long id,
        String firstName,
        String lastName,
        String role
) {
}
