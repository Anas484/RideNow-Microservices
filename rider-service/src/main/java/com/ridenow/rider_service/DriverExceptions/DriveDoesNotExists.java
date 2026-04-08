package com.ridenow.rider_service.DriverExceptions;

public class DriveDoesNotExists extends RuntimeException {
    public DriveDoesNotExists(String message) {
        super(message);
    }
}
