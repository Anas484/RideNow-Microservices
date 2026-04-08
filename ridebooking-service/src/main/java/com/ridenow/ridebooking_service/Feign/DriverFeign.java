package com.ridenow.ridebooking_service.Feign;


import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "rider-service", url = "http://localhost:8081/api/internal/driver")
public interface DriverFeign {
    @GetMapping("/checkStatus/{id}")
    public String checkDriverStatus(@PathVariable Long id);

    //UpdateDriverStatus
    @PutMapping("/updateStatus/{id}")
    public void updateDriverStatus(@PathVariable Long id, @RequestParam String status);

}
