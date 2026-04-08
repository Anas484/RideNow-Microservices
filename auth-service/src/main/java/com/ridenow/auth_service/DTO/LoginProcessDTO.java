package com.ridenow.auth_service.DTO;

public record LoginProcessDTO(
        Long id,
        String email,
        String password,
        String role
) {
}
