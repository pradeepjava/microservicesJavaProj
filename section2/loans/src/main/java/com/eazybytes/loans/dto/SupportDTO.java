package com.eazybytes.loans.dto;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.util.List;
import java.util.Map;
@Data
@Configuration
@ConfigurationProperties(prefix = "loans")
public class SupportDTO {
    private String message;
    Map<String, String> contactDetails;
    List<String> onCallSupport;
}
