package com.ridenow.passenger_service.dto;

import com.ridenow.passenger_service.model.Role;
import jakarta.persistence.Column;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.Builder;

public record Passengerdto(
        Long passengerId,
        String email,
        String firstName,
        String lastName,
        Role role
) {
}
