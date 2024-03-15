package com.liridonmorina.bookingservice.service;

import com.liridonmorina.bookingservice.config.LockedFunction;
import com.liridonmorina.bookingservice.exceptions.BookingServiceException;
import lombok.RequiredArgsConstructor;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

@Service
@RequiredArgsConstructor
public class RedissonLockedExecutionService {
    private static final int LOCK_EXPIRATION_SECONDS = 30;

    private final RedissonClient redissonClient;

    public <T> T runLocked(LockedFunction<T> function, String lockName) {
        try {
            RLock lock = redissonClient.getLock(lockName);
            boolean isLocked = lock.tryLock(LOCK_EXPIRATION_SECONDS, TimeUnit.SECONDS);

            if (isLocked) {
                try {
                    return function.apply();
                } finally {
                    lock.unlock();
                }
            } else {
                throw new RuntimeException("Failed to acquire lock for " + lockName);
            }
        } catch (BookingServiceException e) {
            throw e; // rethrow known exceptions
        } catch (Throwable e) {
            throw new RuntimeException("Running locked task failed", e);
        }
    }
}
