package com.ridenow.rider_service.controller;


import com.ridenow.rider_service.service.DriverService;
import com.ridenow.rider_service.service.InternalDriverService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/internal/driver")
public class InternalController {

    private final InternalDriverService internalDriverService;

    //CheckDriverStatus
    @GetMapping("/checkStatus/{id}")
    public String checkDriverStatus(@PathVariable Long id){
        return internalDriverService.checkDriverStatus(id);
    }

    //UpdateDriverStatus
    @PutMapping("/updateStatus/{id}")
    public void updateDriverStatus(@PathVariable Long id, @RequestParam String status) {
        internalDriverService.updateDriverStatus(id, status);
    }


}
