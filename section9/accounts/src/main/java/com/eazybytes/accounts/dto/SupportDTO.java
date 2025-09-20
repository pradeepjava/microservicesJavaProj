package com.eazybytes.accounts.dto;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySources;

import java.util.List;
import java.util.Map;
@Data
@Configuration
@ConfigurationProperties(prefix = "accounts")
@RefreshScope
public class SupportDTO {
    private String message;
    Map<String, String> contactDetails;
    List<String> onCallSupport;
}
