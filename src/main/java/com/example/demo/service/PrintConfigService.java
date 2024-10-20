package com.example.demo.service;

import com.example.demo.config.DemoCourseConfiguration;
import com.example.demo.config.ServerConfiguration;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;

import jakarta.annotation.PostConstruct;

@Service
public class PrintConfigService {
    @Value("${demo.course.name}")
    private String courseName;

    @Value("${demo.course.version}")
    private Double courseVer;

    @Value("${HOME}")
    private String homeDir;

    ServerConfiguration serverConfiguration;

    DemoCourseConfiguration demoCourseConfiguration;

    Environment environment;

    PrintConfigService(ServerConfiguration serverConfiguration,
                       DemoCourseConfiguration demoCourseConfiguration, Environment environment) {
        this.serverConfiguration = serverConfiguration;
        this.demoCourseConfiguration = demoCourseConfiguration;
        this.environment = environment;
    }

    @PostConstruct
    void printConfigurationProperties() {
        System.out.println("Server configuration: "+ serverConfiguration.toString());
        System.out.println("Demo course configuration: " + demoCourseConfiguration.toString());
        System.out.println("Demo course values: name: " + courseName + ", version: " + courseVer);
        System.out.println("HOME (read from Environment Bean): " + environment.getProperty("HOME"));
        System.out.println("HOME (read by @Value): " + homeDir);
    }
}
