package com.project.gateway_service.filter;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

@Slf4j
@Component
public class MyLoggingFilter extends AbstractGatewayFilterFactory<MyLoggingFilter.Config> {

    public MyLoggingFilter() {
        super(Config.class);
    }

    @Override
    public GatewayFilter apply(Config config) {
        return (exchange, chain) -> {
            log.info("Logging Pre Filter: baseMessage -> {}", config.baseMessage);
            if (config.isPreLogger()) log.info("Logging Pre Filter: request id -> {}", exchange.getRequest().getId());
            return chain.filter(exchange).then(Mono.fromRunnable(() -> {
                if (config.isPreLogger()) log.info("Logging Post Filter: response code -> {}", exchange.getResponse().getStatusCode());
            }));
        };
    }

    @Data
    public static class Config {
        private String baseMessage;
        private boolean preLogger;
        private boolean postLogger;
    }
}
