package com.cloudgateway.cloudgateway.config;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.cloud.gateway.filter.ratelimit.KeyResolver;
import org.springframework.cloud.gateway.filter.ratelimit.RedisRateLimiter;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.http.HttpMethod;
import reactor.core.publisher.Mono;

import java.time.Duration;
import java.time.LocalDateTime;

@Configuration
public class GatewayRoutes {

    @Bean
    public RouteLocator routeLocator(RouteLocatorBuilder builder) {
        return builder.routes()
                .route(predicateSpec -> predicateSpec.path("/accounts/**")
                        .filters(gatewayFilterSpec ->
                                gatewayFilterSpec
                                        .addResponseHeader("X-ResponseTime", LocalDateTime.now().toString())
                                        .rewritePath("/accounts/(?<segment>.*)", "/${segment}")
                                        .circuitBreaker(config -> config.setName("apiGatewayAccountsCircuitBreaker")
                                                .setFallbackUri("forward:/contactSupport"))
                        )
                        .uri("http://accounts:8080")
                ).route(predicateSpec -> predicateSpec.path("/loans/**")
                        .filters(gatewayFilterSpec ->
                                gatewayFilterSpec.rewritePath("/loans/(?<segment>.*)", "/${segment}")
                                        .retry(config -> config.setMethods(HttpMethod.GET)
                                                .setRetries(3)
                                                .setBackoff(Duration.ofMillis(100), Duration.ofMillis(1000), 2, true))

                        )
                        .uri("http://loans:8090")
                ).route(predicateSpec -> predicateSpec.path("/cards/**")
                        .filters(gatewayFilterSpec ->
                                gatewayFilterSpec.rewritePath("/cards/(?<segment>.*)", "/${segment}")

                        )
                        .uri("http://cards:9000")
                ).build();
    }

//    @Bean
//    @Primary
//    public RedisRateLimiter redisRateLimiter() {
//        return new RedisRateLimiter(50, 70);
//    }
//
//    @Bean
//    @Qualifier("userKeyResolver")
//    public KeyResolver userKeyResolver() {
//        KeyResolver keyResolver = serverExchange -> {
//            String user = serverExchange.getRequest().getHeaders().getFirst("user");
//            return user == null ? Mono.just("undefined") : Mono.just(user);
//        };
//        return keyResolver;
//    }

}


