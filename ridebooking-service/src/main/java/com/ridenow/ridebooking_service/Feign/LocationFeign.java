package com.ridenow.ridebooking_service.Feign;


import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Map;

@FeignClient(name = "location-service",url = "http://localhost:8085/api/internal/location")
public interface LocationFeign {

    @PostMapping("/update-location/{driverId}")
    public void updateDriverLocation(@PathVariable Long driverId, @RequestParam Double lat, @RequestParam Double lon);

    //GetNearbyDriversWithing5Km
    @GetMapping("/getNearbyDrivers")
    public List<Long> getNearbyDriversIds(@RequestParam Double lat, @RequestParam Double lon);


    @GetMapping("/getGeoCode")
    public Map<String , Double> getGeoCode(@RequestParam String location);
}
