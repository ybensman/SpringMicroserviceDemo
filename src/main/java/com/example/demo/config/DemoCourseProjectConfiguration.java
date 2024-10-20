package com.example.demo.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties("demo.course.projects")
public record DemoCourseProjectConfiguration(String name,
                                            String details) {
}
