package com.ridenow.rider_service.controller;


import com.ridenow.rider_service.service.InternalDriverService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

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

    @GetMapping("/checkStatus/all")
    public Map<Long, String> checkAllDriverStatus(List<Long> ids){
        return internalDriverService.checkAllDriverStatus(ids);
    }

    //UpdateDriverStatus
    @PutMapping("/updateStatus/{id}")
    public void updateDriverStatus(@PathVariable Long id, @RequestParam String status) {
        internalDriverService.updateDriverStatus(id, status);
    }


}
