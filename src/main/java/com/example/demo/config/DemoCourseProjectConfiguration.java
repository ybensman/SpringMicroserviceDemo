package com.example.demo.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties("demo.course.projects")
public class DemoCourseProjectConfiguration {
    private String name;
    private String details;

    public void setName(String name) {
        this.name = name;
    }

    public void setDetails(String details) {
        this.details = details;
    }

    @Override
    public String toString() {
        return "DemoCourseProjectConfiguration{" +
                "name='" + name + '\'' +
                ", details='" + details + '\'' +
                '}';
    }
}
