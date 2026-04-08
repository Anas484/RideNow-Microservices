package com.ridenow.ridebooking_service.exception;

public class NoDriverFoundNearbyException extends RuntimeException {
    public NoDriverFoundNearbyException(String message) {
        super(message);
    }
}
