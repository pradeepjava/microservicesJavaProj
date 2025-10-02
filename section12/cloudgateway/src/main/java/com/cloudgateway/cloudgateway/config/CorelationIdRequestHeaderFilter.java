package com.cloudgateway.cloudgateway.config;

import com.cloudgateway.cloudgateway.ApplicationConstant;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.util.UUID;

@Order(-1)
@Component
public class CorelationIdRequestHeaderFilter implements GlobalFilter {
    Logger logger = LoggerFactory.getLogger(CorelationIdRequestHeaderFilter.class);

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        if (exchange.getRequest().getURI().getPath().contains("getCompleteAccountDetails")) {
            if (exchange.getRequest().getHeaders().containsKey(ApplicationConstant.X_CORELATIONID)) {
                logger.debug("CORELATIONID EXISTS");
                return chain.filter(exchange);
            } else {
                logger.debug("CORELATIONID GENERATED");
               ServerWebExchange serverWebExchange= exchange.mutate().request(exchange.getRequest().mutate().header(ApplicationConstant.X_CORELATIONID, generateCorelationId()).build()).build();
               return chain.filter(serverWebExchange);

            }
        }
        return chain.filter(exchange);
    }

    private String generateCorelationId() {
        return UUID.randomUUID().toString();
    }
}
