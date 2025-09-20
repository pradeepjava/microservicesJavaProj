package com.cloudgateway.cloudgateway.config;

import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.LocalDateTime;

@Configuration
public class GatewayRoutes {
    @Bean
    public RouteLocator routeLocator(RouteLocatorBuilder builder) {
        return builder.routes()
                .route(predicateSpec -> predicateSpec.path("/accountsms/**")
                        .filters(gatewayFilterSpec ->
                                gatewayFilterSpec
                                        .addResponseHeader("X-ResponseTime", LocalDateTime.now().toString())
                                        .rewritePath("/accountsms/(?<segment>.*)", "/${segment}"))
                        .uri("lb://ACCOUNTSMS")
                ).route(predicateSpec -> predicateSpec.path("/loansms/**")
                        .filters(gatewayFilterSpec ->
                                gatewayFilterSpec.rewritePath("/loansms/(?<segment>.*)", "/${segment}"))
                        .uri("lb://LOANSMS")
                ).route(predicateSpec -> predicateSpec.path("/cardsms/**")
                        .filters(gatewayFilterSpec ->
                                gatewayFilterSpec.rewritePath("/cardsms/(?<segment>.*)", "/${segment}"))
                        .uri("lb://CARDSMS")
                ).build();
    }
}
