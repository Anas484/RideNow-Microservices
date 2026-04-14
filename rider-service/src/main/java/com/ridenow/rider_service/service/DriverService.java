package com.ridenow.rider_service.service;


import com.ridenow.rider_service.DTO.*;
import com.ridenow.rider_service.DriverExceptions.DriverAlreadyExistsException;
import com.ridenow.rider_service.ModelMapper.DriverMapper;
import com.ridenow.rider_service.model.Driver;
import com.ridenow.rider_service.model.Role;
import com.ridenow.rider_service.repository.DriverRepo;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Service
@RequiredArgsConstructor
@Slf4j
public class DriverService {

    public final DriverRepo driverRepo;

    public final DriverMapper driverMapper;

    private final RabbitTemplate rabbitTemplate;

    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    private final Random random = new Random();


    public LoginProcessDTO getDriverByEmail(@NonNull String email) {
        Driver driver = driverRepo.findDriverByEmail(email)
                .orElseThrow(()-> new NoSuchElementException("No such Driver Exists"));
        return driverMapper.loginProcessDTO(driver);
    }

    public void createDriver(SignUpProcessDTO data){
        Driver isExist = driverRepo.findDriverByEmail(data.email()).orElse(null);
        if (isExist != null) {
            throw new DriverAlreadyExistsException("Driver already exists");
        }
        Role role = Role.valueOf(data.role().toUpperCase());
        Driver driver = Driver.builder()
                .email(data.email())
                .firstName(data.firstName())
                .lastName(data.lastName())
                .mobileNumber(data.mobileNumber())
                .password(data.password())
                .role(role)
                .build();
        driverRepo.save(driver);
        log.info("Created Driver with email {}", data.email());
    }


    //Get All drivers
    public List<DriverResponseDTO> getAllDrivers(){
        return driverRepo.findAll()
                .stream()
                .map(driverMapper::DriverResponceMapper)
                .toList();
    }

    //Get Current river
    public DriverResponseDTO getCurrentDriver(){
        Authentication authentication =  SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getName();
        Driver driver = driverRepo.findDriverByEmail(email)
                .orElse(null);
        return driverMapper.DriverResponceMapper(driver);
    }


    @Scheduled(fixedRate = 5000)
    public void sendDriverLocationGeneral(){
        ExecutorService executor = Executors.newFixedThreadPool(1000);
        for (int i = 1; i <= 1000; i++) {
            final long driverId = i;
//            Long id = random.nextLong(1,10);
            executor.submit(() -> {
                Double lat = (random.nextDouble() - 0.5) / 1000;
                Double lon = (random.nextDouble() - 0.5) / 1000;
                DriverLocation driver = new DriverLocation(driverId, lat, lon);
                rabbitTemplate.convertAndSend("driver-location-queue", driver);
            });

        }
        log.info("Sending 100 driver location");
    }



}
