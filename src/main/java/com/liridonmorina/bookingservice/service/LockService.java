package com.liridonmorina.bookingservice.service;

import java.util.concurrent.Callable;

public interface LockService {
    <T> T runLocked(Callable<T> function, String lockName);
}
