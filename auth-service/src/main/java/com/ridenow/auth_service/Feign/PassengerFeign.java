package com.ridenow.auth_service.Feign;


import com.ridenow.auth_service.DTO.LoginProcessDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "passenger-service", url = "http://localhost:8082/api/passenger/", configuration = FeignConfig.class)
public interface PassengerFeign {

    @GetMapping("/{email}")
    public LoginProcessDTO getPassengerByEmail(@PathVariable String email);
}
