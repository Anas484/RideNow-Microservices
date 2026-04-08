package com.ridenow.rider_service.repository;


import com.ridenow.rider_service.model.Driver;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface DriverRepo extends JpaRepository<Driver, Long> {

    Optional<Driver> findDriverByEmail(String email);
}
