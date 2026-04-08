package com.ridenow.rider_service.controller;


import com.ridenow.rider_service.DTO.DriverResponseDTO;
import com.ridenow.rider_service.service.DriverService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/driver")
public class DriverController {

    private final DriverService driverService;

    @GetMapping("/currentDriver")
    public DriverResponseDTO getCurrentDriver(){
        return driverService.getCurrentDriver();
    }
}
