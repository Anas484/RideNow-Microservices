package com.ridenow.auth_service.security;


import com.ridenow.auth_service.DTO.*;
import com.ridenow.auth_service.Feign.DriverFeign;
import com.ridenow.auth_service.Feign.PassengerFeign;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class AuthService {

    private final AuthUtil authUtil;

    private final PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    private final DriverFeign  driverFeign;

    private final PassengerFeign passengerFeign;


    public LoginResponseDTO login(LoginRequestDTO request) {
        if (request.role().equals("DRIVER")) {
            return loginDriver(request);
        } else if (request.role().equals("PASSENGER")) {
            return loginPassenger(request);
        } else {
            throw new BadCredentialsException("Invalid role");
        }

    }

    public LoginResponseDTO loginDriver(LoginRequestDTO request) {
        LoginProcessDTO person = driverFeign.getDriverByEmail(request.email());
        if (!passwordEncoder.matches(request.password(), person.password())){
            throw new BadCredentialsException("Incorrect email or password");
        }
        String token = authUtil.generateToken(person);
        log.info("Logged In User: {}", person.email() + " With role" + person.role());
        return new LoginResponseDTO(
                person.id(),
                token
        );
    }

    public LoginResponseDTO loginPassenger(LoginRequestDTO request) {
        LoginProcessDTO person = passengerFeign.getPassengerByEmail(request.email());
        if (!passwordEncoder.matches(request.password(), person.password())){
            throw new BadCredentialsException("Incorrect email or password");
        }
        String token = authUtil.generateToken(person);
        log.info("Logged In User: {}", person.email() + " With role" + person.role());
        return new LoginResponseDTO(
                person.id(),
                token
        );
    }

    public void signup(SignUpRequestDTO request) {
        if (request.role().equals("DRIVER")) {
            signUpDriver(request);
        } else if (request.role().equals("PASSENGER")) {
            signUpPassenger(request);
        } else {
            throw new BadCredentialsException("Invalid role");
        }
    }

    public void signUpDriver(SignUpRequestDTO request) {
        SignUpProcessDTO person = new SignUpProcessDTO(
                request.email(),
                request.firstName(),
                request.lastName(),
                passwordEncoder.encode(request.password()),
                request.mobileNumber(),
                request.role()
        );
        driverFeign.createDriver(person);
        log.info("Sign Up User: {}", person.email() + " With role" + person.role());
    }

    public void signUpPassenger(SignUpRequestDTO request) {

    }
}
