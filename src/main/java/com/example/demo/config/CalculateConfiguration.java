package com.example.demo.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties("app.multi-value-operations")
public record CalculateConfiguration(boolean available,
                                    Integer limit) {
}
