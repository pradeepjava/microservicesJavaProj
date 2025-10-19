package com.cloudgateway.cloudgateway.config;

import com.cloudgateway.cloudgateway.ApplicationConstant;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class CorelationIdResponseHeaderFilter {
    @Bean
    public GlobalFilter attachResponseHeaderCorelationidFilter() {
        return ((exchange, chain) -> {
            List<String> corelationIds = exchange.getRequest().getHeaders().get(ApplicationConstant.X_CORELATIONID);
            if (corelationIds != null && !corelationIds.isEmpty()) {
                exchange.getResponse().getHeaders().add(ApplicationConstant.X_CORELATIONID, corelationIds.get(0));
            }
            return chain.filter(exchange);
        });
    }
}
