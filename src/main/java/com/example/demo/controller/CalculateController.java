package com.example.demo.controller;

import com.example.demo.config.CalculateConfiguration;
import com.example.demo.errorhandling.FeatureNotAvailableException;
import com.example.demo.errorhandling.NumsLimitExceededException;
import com.example.demo.service.CalculationService;
import com.example.demo.model.CalculateRequest;
import com.example.demo.model.CalculateResponse;
import com.example.demo.model.NumeralSystem;
import com.example.demo.model.UniversalCalculateRequest;
import com.example.demo.model.UniversalCalculateResponse;
import com.example.demo.service.RandomNumbersService;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;

import java.util.List;

@RestController
@RequestMapping("calculate")
@Validated
public class CalculateController {
    CalculationService decCalculationService;
    CalculationService hexCalculationService;
    RandomNumbersService randomNumbersService;

    CalculateConfiguration calculateConfiguration;

    CalculateController(RandomNumbersService randomNumbersService,
                        @Qualifier("decCalculationService") CalculationService dec,
                        @Qualifier("hexCalculationService") CalculationService hex,
                        CalculateConfiguration calculateConfiguration
                        ) {
        this.decCalculationService = dec;
        this.hexCalculationService = hex;
        this.calculateConfiguration = calculateConfiguration;
        this.randomNumbersService = randomNumbersService;
    }

    @GetMapping("/rand")
    double getRandomNumber() {
        return randomNumbersService.generateRandomNumber();
    }

    @GetMapping("/add/{num1}/{num2}")
    // Can be also:
    // @PathVariable(num1) int number1
    // @PathVariable(value = "num1") int number1
    int add1(@PathVariable @Min(1) @Max(100) int num1, @PathVariable @Min(1) @Max(100) int num2) {
        return num1 + num2;
    }

    @GetMapping("/decimalAdd")
    int decimalAdd(@RequestParam @Min(1) @Max(100) Integer num1,
            @RequestParam(required = false) @Min(1) @Max(100) Integer num2) {
        if (num2 == null) {
            num2 = 0;
        }

        return num1 + num2;
    }

    @GetMapping("/universalAdd")
    String universalAdd(@RequestParam String num1, @RequestParam String num2,
                    @RequestParam(required = false) NumeralSystem numeralSystem) throws IllegalAccessException {

        if (numeralSystem == null) {
            numeralSystem = NumeralSystem.DEC;
        }

        String result;
        if (numeralSystem == NumeralSystem.DEC) {
            result = decCalculationService.addTwoNumbers(num1, num2);
        }
        else if (numeralSystem == NumeralSystem.HEX) {
            result = hexCalculationService.addTwoNumbers(num1, num2);
        }
        else {
            throw new IllegalAccessException();
        }

        return result;
    }

    @PostMapping("/add")
    public CalculateResponse add(@Valid @RequestBody CalculateRequest request) {
        return new CalculateResponse(request.num1 + request.num2);
    }

    @PostMapping("/universalAdd")
    public UniversalCalculateResponse universalAdd(@RequestParam NumeralSystem numeralSystem, @Valid @RequestBody UniversalCalculateRequest request) throws IllegalAccessException {
        String result;
        if (numeralSystem == NumeralSystem.DEC) {
            result = decCalculationService.addTwoNumbers(request.num1(), request.num2());
        }
        else if (numeralSystem == NumeralSystem.HEX) {
            result = hexCalculationService.addTwoNumbers(request.num1(), request.num2());
        }
        else {
            throw new IllegalAccessException();
        }

        return new UniversalCalculateResponse(result);
    }

    @GetMapping("/sumNumbers")
    public int sumNumbers(@RequestParam List<Integer> nums) {
        if (!calculateConfiguration.available()) {
            throw new FeatureNotAvailableException();
        }

        if (nums.size() > calculateConfiguration.limit()) {
            throw new NumsLimitExceededException();
        }

        return nums.stream().reduce(0, Integer::sum);
    }
}