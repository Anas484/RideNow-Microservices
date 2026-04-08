package com.ridenow.rider_service.ModelMapper;


import com.ridenow.rider_service.DTO.DriverResponseDTO;
import com.ridenow.rider_service.DTO.LoginProcessDTO;
import com.ridenow.rider_service.model.Driver;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;


@Component
public class DriverMapper {

    public  DriverResponseDTO DriverResponceMapper(Driver driver) {
        return new  DriverResponseDTO(
                driver.getId(),
                driver.getFirstName(),
                driver.getLastName(),
                driver.getEmail(),
                driver.getMobileNumber()
        );
    }

    public LoginProcessDTO loginProcessDTO(Driver driver) {
        return new LoginProcessDTO(
                driver.getId(),
                driver.getEmail(),
                driver.getPassword(),
                driver.getAuthorities().toString()
        );
    }
}
