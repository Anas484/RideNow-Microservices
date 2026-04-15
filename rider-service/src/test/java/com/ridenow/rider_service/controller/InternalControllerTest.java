package com.ridenow.rider_service.controller;

import com.ridenow.rider_service.service.InternalDriverService;
import org.junit.jupiter.api.Test;

class InternalControllerTest {

    private InternalDriverService internalDriverService;

    @Test
    void checkDriverStatus() {
        internalDriverService.checkAllDriverStatus(1L);
    }

    @Test
    void updateDriverStatus() {
        internalDriverService.updateDriverStatus(1L, "ACTIVE");
    }
}