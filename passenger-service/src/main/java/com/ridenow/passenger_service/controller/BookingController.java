package com.ridenow.passenger_service.controller;


import com.ridenow.passenger_service.dto.LocationDto;
import com.ridenow.passenger_service.dto.RideBookingRequestDTO;
import com.ridenow.passenger_service.service.PassengerBookingService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/bookings")
public class BookingController {

    private final PassengerBookingService passengerBookingService;

    @PostMapping("/bookRide")
    public void bookRide(@RequestBody LocationDto request){
        passengerBookingService.bookRide(request);
    }
}
