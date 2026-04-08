package com.ridenow.auth_service.Feign;


import com.ridenow.auth_service.DTO.LoginProcessDTO;
import com.ridenow.auth_service.DTO.SignUpProcessDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "rider-service", url = "http://localhost:8081",configuration = FeignConfig.class)
public interface DriverFeign {

    @GetMapping("/api/driver/auth/{email}")
    public LoginProcessDTO getDriverByEmail(@PathVariable String email);


    @PostMapping("/api/driver/auth/create-driver")
    public void createDriver(@RequestBody SignUpProcessDTO signUpProcessDTO);

}
