package com.liridonmorina.bookingservice.config;

@FunctionalInterface
public interface LockedFunction<T> {
    T apply();
}
