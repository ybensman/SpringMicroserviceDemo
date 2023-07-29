package com.example.demo.model;

import javax.validation.constraints.*;

public record MultipurposeCalculateRequest(
        @NotNull
        @NotBlank
        @Min(1)
        @Max(100)
        @Pattern(regexp = "^[0-9]*[a-f]*$")
        String num1,
        @NotNull
        @NotBlank
        @Min(1)
        @Max(100)
        @Pattern(regexp = "^[0-9]*[a-f]*$")
        String num2) {
}
