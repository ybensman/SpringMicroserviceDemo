package com.example.demo.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.NestedConfigurationProperty;
import org.springframework.validation.annotation.Validated;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import java.util.List;

@ConfigurationProperties("demo.course")
@Validated
public record DemoCourseConfiguration(
        @Positive
        Double version,
        @NotBlank
        String name,
        @NotBlank
        String endPoint,
        @NestedConfigurationProperty
        List<DemoCourseProjectConfiguration> projects) {
}
