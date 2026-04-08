package com.ridenow.rider_service.security;


import com.ridenow.rider_service.DTO.DriverRequestDTO;
import com.ridenow.rider_service.DTO.DriverResponseDTO;
import com.ridenow.rider_service.DriverExceptions.DriverAlreadyExistsException;
import com.ridenow.rider_service.ModelMapper.DriverMapper;
import com.ridenow.rider_service.model.Driver;
import com.ridenow.rider_service.repository.DriverRepo;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final DriverRepo driverRepo;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

//    //SignUp Or Create A Driver
//    public DriverResponseDTO createOrSignupDriver(@NonNull DriverRequestDTO data) {
//        Driver isExists = driverRepo.findDriverByEmail(data.email()).orElse(null);
//        if (isExists != null) {
//            throw new DriverAlreadyExistsException(data.email() + " already exists");
//        }
//        Driver driver = Driver.builder()
//                .firstName(data.firstName())
//                .lastName(data.lastName())
//                .email(data.email())
//                .password(bCryptPasswordEncoder.encode(data.password()))
//                .mobileNumber(data.mobileNumber())
//                .build();
//        driverRepo.save(driver);
//        return DriverMapper.DriverResponceMapper(driver);
//    }


    //Login Driver

}
