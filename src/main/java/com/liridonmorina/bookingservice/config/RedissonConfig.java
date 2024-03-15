package com.liridonmorina.bookingservice.config;

import org.redisson.Redisson;
import org.redisson.api.RedissonClient;
import org.redisson.config.Config;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RedissonConfig {
    @Value("${config.redis.address}")
    private String address;

    @Bean
    public RedissonClient redissonClient() {
        System.out.println("Redis address " + address);
        Config config = new Config();
        config.useSingleServer()
                .setAddress(address);
        return Redisson.create(config);
    }
}
