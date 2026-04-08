package com.ridenow.ridebooking_service.repository;


import com.ridenow.ridebooking_service.model.RideBookings;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RideBookingsRepo extends JpaRepository<RideBookings, Long> {
}
