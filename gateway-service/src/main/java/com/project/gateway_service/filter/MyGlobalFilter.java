package com.project.gateway_service.filter;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

@Slf4j
@Component
public class MyGlobalFilter extends AbstractGatewayFilterFactory<MyGlobalFilter.Config> {

    public MyGlobalFilter() {
        super(Config.class);
    }

    @Override
    public GatewayFilter apply(Config config) {
        return (exchange, chain) -> {
            if (config.isPreFilter()) log.info("Global Pre Filter executed");
            return chain.filter(exchange).then(Mono.fromRunnable(() -> {
                if (config.isPostFilter()) log.info("Global Post Filter executed");
            }));
        };
    }

    @Data
    public static class Config {
        private boolean preFilter;
        private boolean postFilter;
    }
}
