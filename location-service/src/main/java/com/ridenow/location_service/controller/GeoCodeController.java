package com.ridenow.location_service.controller;


import com.ridenow.location_service.service.GeoCoderService;
import com.ridenow.location_service.service.LocationService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/location/geocode")
public class GeoCodeController {

    //GeoCode

}
