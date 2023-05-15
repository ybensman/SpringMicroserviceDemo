package com.example.demo.service;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class CalculationServiceFactory {

    @Bean
    @Qualifier("decCalculationService")
    CalculationService decimalCalculationService() {
        return new CalculationService(10);
    }

    @Bean
    @Qualifier("hexCalculationService")
    CalculationService hexadecimalCalculationService() {
        return new CalculationService(16);
    }
}
