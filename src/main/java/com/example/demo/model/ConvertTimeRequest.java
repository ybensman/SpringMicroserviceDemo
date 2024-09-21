package com.example.demo.model;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;

public class ConvertTimeRequest {
    @NotNull
    @NotBlank
    @Pattern(regexp = "^[A-z]*\\/[A-z]*$")
    public String timeZone;
}
