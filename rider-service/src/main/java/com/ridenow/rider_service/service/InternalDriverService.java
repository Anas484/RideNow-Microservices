package com.ridenow.rider_service.service;


import com.ridenow.rider_service.DriverExceptions.DriveDoesNotExists;
import com.ridenow.rider_service.ModelMapper.DriverMapper;
import com.ridenow.rider_service.model.Driver;
import com.ridenow.rider_service.model.DriverStatus;
import com.ridenow.rider_service.repository.DriverRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class InternalDriverService {

    private final DriverMapper driverMapper;
    private final DriverRepo driverRepo;


    // Check driver status
    public Map<Long, String> checkAllDriverStatus(List<Long> id){
        Map<Long, String> driversMap = new HashMap<>();
        driversMap.putAll(driverRepo.findAllById(id).stream().collect(Collectors.toMap(Driver::getId, driver -> driver.getStatus().name())));
        return driversMap;
    }

    //Update Driver Status
    public void updateDriverStatus(@PathVariable Long id, @RequestParam String status) {
        Driver driver = driverRepo.findById(id).orElse(null);
        if (driver == null) {
            throw new DriveDoesNotExists("Driver with id " + id + " does not exist");
        }
        driver.setStatus(DriverStatus.valueOf(status));
        driverRepo.save(driver);
    }

    public String checkDriverStatus(Long id) {
        Driver driver = driverRepo.findById(id).orElse(null);
        if (driver == null) {
            throw new DriveDoesNotExists("Driver with id " + id + " does not exist");
        }
        return driver.getStatus().name();
    }
}
