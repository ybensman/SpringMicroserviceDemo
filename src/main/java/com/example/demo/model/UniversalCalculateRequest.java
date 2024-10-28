package com.example.demo.model;

import jakarta.validation.constraints.*;

public record UniversalCalculateRequest(
        @NotBlank
        @Pattern(regexp = "^[a-fA-F0-9]+$")
        String num1,
        @NotBlank
        @Pattern(regexp = "^[a-fA-F0-9]+$")
        String num2) {
}
