package com.project.product_service.config;

import org.redisson.Redisson;
import org.redisson.api.RedissonClient;
import org.redisson.config.Config;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.IOException;

@Configuration
public class RedissonConfig {

    @Value("${REDIS_ADDRESS:redis://localhost:6379}")
    private String redisAddress;

    @Value("${REDIS_CONNECTION_POOL_SIZE:64}")
    private int connectionPoolSize;

    @Value("${REDIS_CONNECTION_MINIMUM_IDLE_SIZE:10}")
    private int connectionMinimumIdleSize;

    @Value("${REDIS_PASSWORD:}")
    private String password;

    @Bean
    public RedissonClient redissonClient() throws IOException {
        Config config = new Config();

        config.useSingleServer()
                .setAddress(redisAddress)
                .setConnectionPoolSize(connectionPoolSize)
                .setConnectionMinimumIdleSize(connectionMinimumIdleSize);

        return Redisson.create(config);
    }
}
