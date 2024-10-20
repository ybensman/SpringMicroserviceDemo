package com.example.demo.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties("app.multipurpose-add")
public record CalculateConfiguration(boolean available,
                                    Integer min,
                                    Integer max) {
}
