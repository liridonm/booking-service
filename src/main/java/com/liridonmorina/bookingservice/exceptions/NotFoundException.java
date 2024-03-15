package com.liridonmorina.bookingservice.exceptions;

public class NotFoundException extends BookingServiceException {
    public NotFoundException(String message) {
        super(message);
    }
}
