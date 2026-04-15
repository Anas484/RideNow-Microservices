package com.ridenow.passenger_service.repository;


import com.ridenow.passenger_service.model.Passeneger;
import com.ridenow.passenger_service.model.Passenger;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PassengerRepo extends JpaRepository<Long,Passeneger> {
    Optional<Passeneger> findByByEmail(String email);

    Optional<Passenger> findById(Long id);
}
