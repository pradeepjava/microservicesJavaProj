package com.cloudgateway.cloudgateway.config;

import org.springframework.cloud.gateway.handler.predicate.AbstractRoutePredicateFactory;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.server.ServerWebExchange;

import java.util.function.Predicate;

@Configuration
public class SimpleHeaderPredicate extends AbstractRoutePredicateFactory<SimpleHeaderPredicate.Config> {
    public SimpleHeaderPredicate() {
        super(Config.class);
    }

    @Override
    public Predicate<ServerWebExchange> apply(Config config) {
        return serverWebExchange -> serverWebExchange
                .getRequest().getHeaders().keySet().stream().anyMatch(headerKey -> headerKey.equals(config.getHeaderName()));
    }

    public static class Config {
        private String headerName;

        public String getHeaderName() {
            return headerName;
        }

        public void setHeaderName(String headerName) {
            this.headerName = headerName;
        }
    }

}
