package com.example.demo.service;

public class CalculationService {
    int radix;

    CalculationService(int radix) {
        this.radix = radix;
    }

    public String addTwoNumbers(String num1, String num2) {
        int number1 = Integer.parseInt(num1, radix);
        int number2 = Integer.parseInt(num2, radix);

        return Integer.toString(number1 + number2, radix);
    }
}
