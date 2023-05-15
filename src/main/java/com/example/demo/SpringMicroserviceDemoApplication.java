package com.example.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;

@SpringBootApplication
@ConfigurationPropertiesScan
public class SpringMicroserviceDemoApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringMicroserviceDemoApplication.class, args);
	}

}
