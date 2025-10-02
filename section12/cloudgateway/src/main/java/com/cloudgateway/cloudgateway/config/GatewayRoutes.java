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
                .route(predicateSpec -> predicateSpec.path("/accountsms/**")
                        .filters(gatewayFilterSpec ->
                                gatewayFilterSpec
                                        .addResponseHeader("X-ResponseTime", LocalDateTime.now().toString())
                                        .rewritePath("/accountsms/(?<segment>.*)", "/${segment}")
                                        .circuitBreaker(config -> config.setName("apiGatewayAccountsCircuitBreaker")
                                                .setFallbackUri("forward:/contactSupport"))
                                        .requestRateLimiter(config -> config.setKeyResolver(userKeyResolver())
                                                .setRateLimiter(redisRateLimiter()))
                        )


                        .uri("lb://ACCOUNTSMS")
                ).route(predicateSpec -> predicateSpec.path("/loansms/**")
                        .filters(gatewayFilterSpec ->
                                gatewayFilterSpec.rewritePath("/loansms/(?<segment>.*)", "/${segment}")
                                        .retry(config -> config.setMethods(HttpMethod.GET)
                                                .setRetries(3)
                                                .setBackoff(Duration.ofMillis(100), Duration.ofMillis(1000), 2, true))

                        )
                        .uri("lb://LOANSMS")
                ).route(predicateSpec -> predicateSpec.path("/cardsms/**")
                        .filters(gatewayFilterSpec ->
                                gatewayFilterSpec.rewritePath("/cardsms/(?<segment>.*)", "/${segment}")

                        )
                        .uri("lb://CARDSMS")
                ).build();
    }

    @Bean
    @Primary
    public RedisRateLimiter redisRateLimiter() {
        return new RedisRateLimiter(50, 70);
    }

    @Bean
    @Qualifier("userKeyResolver")
    public KeyResolver userKeyResolver() {
        KeyResolver keyResolver = serverExchange -> {
            String user = serverExchange.getRequest().getHeaders().getFirst("user");
            return user == null ? Mono.just("undefined") : Mono.just(user);
        };
        return keyResolver;
    }

}


