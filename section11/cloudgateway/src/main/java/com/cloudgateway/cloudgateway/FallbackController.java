package com.cloudgateway.cloudgateway;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
public class FallbackController {
    @GetMapping("/contactSupport")
    public Mono<String> getContactSupport() {
        return Mono.just("Please try later or Contact Support team.");
    }
}
