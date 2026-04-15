package com.ridenow.passenger_service.util;

import com.ridenow.passenger_service.dto.Passengerdto;
import com.ridenow.passenger_service.model.Passenger;
import org.springframework.stereotype.Component;

@Component
public class PassengerToPassengerDTO {

    public Passengerdto passengerDtoConverter(Passenger  p) {
        return new Passengerdto(
                p.getPassengerId(),
                p.getEmail(),
                p.getFirstName(),
                p.getLastName(),
                p.getRole()
        );
    }
}
