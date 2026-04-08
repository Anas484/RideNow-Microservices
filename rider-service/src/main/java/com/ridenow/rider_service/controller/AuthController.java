package com.ridenow.rider_service.controller;


import com.ridenow.rider_service.DTO.DriverRequestDTO;
import com.ridenow.rider_service.DTO.DriverResponseDTO;
import com.ridenow.rider_service.DTO.LoginProcessDTO;
import com.ridenow.rider_service.DTO.SignUpProcessDTO;
import com.ridenow.rider_service.security.AuthService;
import com.ridenow.rider_service.service.DriverService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/driver/auth")
public class AuthController {

    private final DriverService driverService;
    private final AuthService  authService;

    @GetMapping("/get-all")
    public List<DriverResponseDTO> getAllDrivers(){
        return driverService.getAllDrivers();
    }

    @GetMapping("/{email}")
    public LoginProcessDTO getDriverByEmail(@PathVariable String email){
        return driverService.getDriverByEmail(email);
    }

    @PostMapping("/create-driver")
    public void createDriver(@RequestBody SignUpProcessDTO signUpProcessDTO){
        driverService.createDriver(signUpProcessDTO);
    }

}
