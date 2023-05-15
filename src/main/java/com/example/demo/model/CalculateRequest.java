package com.example.demo.model;

import javax.validation.constraints.Max;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

public class CalculateRequest {
    @Positive
    @Max(100)
    public int num1;
    @Positive
    @Max(100)
    public int num2;
}
