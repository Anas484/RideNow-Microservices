package com.ridenow.rider_service.controller;

import com.ridenow.rider_service.service.DriverService;
import com.ridenow.rider_service.service.InternalDriverService;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class InternalControllerTest {

    private InternalDriverService internalDriverService;

    @Test
    void checkDriverStatus() {
        internalDriverService.checkDriverStatus(1L);
    }

    @Test
    void updateDriverStatus() {
        internalDriverService.updateDriverStatus(1L, "ACTIVE");
    }
}