package com.example.demo.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.NestedConfigurationProperty;
import org.springframework.validation.annotation.Validated;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import java.util.List;

@ConfigurationProperties("demo.course")
@Validated
public class DemoCourseConfiguration {
    @Positive
    private Double version;
    @NotBlank
    private String name;
    @NotBlank
    private String endPoint;

    @NestedConfigurationProperty
    List<DemoCourseProjectConfiguration> projects;

    public void setVersion(Double version) {
        this.version = version;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setEndPoint(String endPoint) {
        this.endPoint = endPoint;
    }

    public void setProjects(List<DemoCourseProjectConfiguration> projects) {
        this.projects = projects;
    }

    @Override
    public String toString() {
        return "DemoCourseConfiguration{" +
                "version=" + version +
                ", name='" + name + '\'' +
                ", endPoint='" + endPoint + '\'' +
                ", projects='" + projects + '\'' +
                '}';
    }
}
