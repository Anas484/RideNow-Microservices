package com.ridenow.rider_service.DTO;

import lombok.NonNull;

public record DriverRequestDTO(
        String firstName,

        String lastName,

        @NonNull String email,

        @NonNull String password,

        String mobileNumber
) {
}
