package com.ridenow.ridebooking_service.exception;

public class NoDriverAcceptedRequest extends RuntimeException {
    public NoDriverAcceptedRequest(String message) {
        super(message);
    }
}
