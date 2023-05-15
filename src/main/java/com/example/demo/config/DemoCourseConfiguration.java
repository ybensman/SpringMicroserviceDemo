package com.example.demo.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties("demo.course")
public class DemoCourseConfiguration {
    private Double version;
    private String name;

    public Double getVersion() {
        return version;
    }

    public void setVersion(Double version) {
        this.version = version;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "DemoCourseConfiguration{" +
                "version=" + version +
                ", name='" + name + '\'' +
                '}';
    }
}
