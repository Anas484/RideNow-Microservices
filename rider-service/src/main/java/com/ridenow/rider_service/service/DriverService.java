package com.ridenow.rider_service.service;


import com.ridenow.rider_service.DTO.DriverRequestDTO;
import com.ridenow.rider_service.DTO.DriverResponseDTO;
import com.ridenow.rider_service.DTO.LoginProcessDTO;
import com.ridenow.rider_service.DTO.SignUpProcessDTO;
import com.ridenow.rider_service.DriverExceptions.DriverAlreadyExistsException;
import com.ridenow.rider_service.ModelMapper.DriverMapper;
import com.ridenow.rider_service.model.Driver;
import com.ridenow.rider_service.model.Role;
import com.ridenow.rider_service.repository.DriverRepo;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;

@Service
@RequiredArgsConstructor
@Slf4j
public class DriverService {

    public final DriverRepo driverRepo;

    public final DriverMapper driverMapper;

    private final BCryptPasswordEncoder bCryptPasswordEncoder;

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


}
