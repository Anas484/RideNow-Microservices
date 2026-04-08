package com.ridenow.rider_service.security;

import com.ridenow.rider_service.repository.DriverRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CustomDriverDetailsService implements UserDetailsService {
    private final DriverRepo  driverRepo;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        return driverRepo.findDriverByEmail(email).orElseThrow(() -> new UsernameNotFoundException("User not found"));
    }
}
