package com.ridenow.location_service.dto;


import lombok.*;
import org.springframework.stereotype.Component;

@Component
@Builder
@Getter @Setter
@AllArgsConstructor
@NoArgsConstructor
public class DriverLocation {
    private Long driverId;
    private Double lat;
    private Double lon;

}
