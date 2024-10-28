package com.example.demo.errorhandling;

public class NumsLimitExceededException extends RuntimeException {
    public NumsLimitExceededException() {
        super("Numbers limit exceeded");
    }
}
