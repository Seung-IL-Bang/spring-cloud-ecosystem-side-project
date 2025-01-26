package com.project.gateway_service.filter;


import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

@Slf4j
@Component
public class MyCustomFilter extends AbstractGatewayFilterFactory<MyCustomFilter.Config> {

    public MyCustomFilter() {
        super(Config.class);
    }

    @Override
    public GatewayFilter apply(Config config) {
        return (exchange, chain) -> {
            log.info("My Custom Pre Filter executed");
            return chain.filter(exchange).then(Mono.fromRunnable(() -> {
                log.info("My Custom Post Filter executed");
            }));
        };
    }

    public static class Config {

    }
}
