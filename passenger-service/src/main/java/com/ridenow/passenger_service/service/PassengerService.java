package com.ridenow.passenger_service.service;


import com.ridenow.passenger_service.dto.Passengerdto;
import com.ridenow.passenger_service.model.Passenger;
import com.ridenow.passenger_service.repository.PassengerRepo;
import com.ridenow.passenger_service.util.PassengerToPassengerDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PassengerService {

    private final PassengerRepo passengerRepo;
    private final PassengerToPassengerDTO passengerToPassengerDTO;

    public Passengerdto getPassengerDetails(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Long id = (Long) authentication.getPrincipal();
        Passenger pass = passengerRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("Passenger not found with id: " + id));
        return passengerToPassengerDTO.passengerDtoConverter(pass);
    }


}
