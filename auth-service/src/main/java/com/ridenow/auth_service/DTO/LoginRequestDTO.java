package com.ridenow.auth_service.DTO;

public record LoginRequestDTO(
        String email,
        String password,
        String role
) {
}
