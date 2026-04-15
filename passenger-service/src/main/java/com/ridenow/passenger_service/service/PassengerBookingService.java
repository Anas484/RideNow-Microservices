package com.ridenow.passenger_service.service;


import com.ridenow.passenger_service.dto.LocationDto;
import com.ridenow.passenger_service.dto.RideBookingRequestDTO;
import com.ridenow.passenger_service.repository.PassengerRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PassengerBookingService {

    private final PassengerRepo  passengerRepo;
    private final RabbitTemplate rabbitTemplate;


    public void bookRide(LocationDto book){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Long id = (Long) authentication.getPrincipal();
        RideBookingRequestDTO bookingDTo = new RideBookingRequestDTO(
                id,
                book.source(),
                book.destination()
        );
        rabbitTemplate.convertAndSend("ride-booking-queue",bookingDTo);
    }
}
