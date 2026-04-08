package com.ridenow.location_service.controller;


import com.ridenow.location_service.service.GeoCoderService;
import com.ridenow.location_service.service.LocationService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/internal/location")
public class InternalController {

    private final LocationService locationService;
    private final GeoCoderService geoCoderService;

    //UpdateDriverLocation
    @PostMapping("/update-location/{driverId}")
    public void updateDriverLocation(@PathVariable Long driverId, @RequestParam Double lat, @RequestParam Double lon) {
        locationService.updateDriverLocation(driverId, lat, lon);
    }

    //GetNearbyDriversWithing5Km
    @GetMapping("/getNearbyDrivers")
    public List<Long> getNearbyDriversIds(@RequestParam Double lat, @RequestParam Double lon) {
        return locationService.getNearbyDriversIds(lat, lon);
    }


    @GetMapping("/getGeoCode")
    public Map<String , Double> getGwoCode(@RequestParam String location){
        return geoCoderService.getGeoCode(location);
    }



}
