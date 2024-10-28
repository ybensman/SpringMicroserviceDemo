package com.example.demo.model;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public class CalculateRequest {
    @NotNull
    @Positive
    @Max(100)
    public int num1;
    @NotNull
    @Positive
    @Max(100)
    public int num2;
}
