package com.ridenow.auth_service.controller;


import com.ridenow.auth_service.DTO.LoginRequestDTO;
import com.ridenow.auth_service.DTO.LoginResponseDTO;
import com.ridenow.auth_service.DTO.SignUpProcessDTO;
import com.ridenow.auth_service.DTO.SignUpRequestDTO;
import com.ridenow.auth_service.security.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/auth")
public class AuthController {

    private final AuthService authService;

    @PostMapping("/login")
    public LoginResponseDTO login(@RequestBody LoginRequestDTO  loginRequestDTO) {
        return authService.login(loginRequestDTO);
    }
    

    @PostMapping("/signup")
    public void signup(@RequestBody SignUpRequestDTO signUpRequestDTO) {
        authService.signup(signUpRequestDTO);
    }


}
