package com.example.demo.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties("app.universal-add")
public class CalculateConfiguration {
    private boolean available;
    private Integer min;
    private Integer max;

    public void setAvailable(boolean available) {
        this.available = available;
    }

    public void setMin(Integer min) {
        this.min = min;
    }

    public void setMax(Integer max) {
        this.max = max;
    }

    public boolean isAvailable() {
        return available;
    }

    public Integer getMin() {
        return min;
    }

    public Integer getMax() {
        return max;
    }
}
