package com.example.demo.controller;

import com.example.demo.config.CalculateConfiguration;
import com.example.demo.errorhandling.FeatureNotAvailableException;
import com.example.demo.service.CalculationService;
import com.example.demo.service.PrintConfigService;
import com.example.demo.model.CalculateRequest;
import com.example.demo.model.CalculateResponse;
import com.example.demo.model.NumeralSystemName;
import com.example.demo.model.MultipurposeCalculateRequest;
import com.example.demo.model.MultipurposeCalculateResponse;
import com.example.demo.service.RandomNumbersService;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;

@RestController
@RequestMapping("calculate")
@Validated
public class CalculateController {
    PrintConfigService printConfigService;

    CalculationService decCalculationService;
    CalculationService hexCalculationService;
    RandomNumbersService randomNumbersService;

    CalculateConfiguration calculateConfiguration;

    CalculateController(PrintConfigService printConfigService,
                        RandomNumbersService randomNumbersService,
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

    @GetMapping("/multipurposeAdd")
    String multipurposeAdd(@RequestParam String num1, @RequestParam String num2,
                    @RequestParam(required = false) NumeralSystemName numeralSystem) throws IllegalAccessException {

        if (numeralSystem == null) {
            numeralSystem = NumeralSystemName.DEC;
        }

        String result;
        if (numeralSystem == NumeralSystemName.DEC) {
            result = decCalculationService.addTwoNumbers(num1, num2);
        }
        else if (numeralSystem == NumeralSystemName.HEX) {
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

    @PostMapping("/multipurposeAdd")
    public MultipurposeCalculateResponse multipurposeAdd(@RequestParam NumeralSystemName numeralSystem, @Valid @RequestBody MultipurposeCalculateRequest request) throws IllegalAccessException, FeatureNotAvailableException{
       if (calculateConfiguration.isAvailable() == false) {
           throw new FeatureNotAvailableException();
       }

        String result;
        if (numeralSystem == NumeralSystemName.DEC) {
            result = decCalculationService.addTwoNumbers(request.num1(), request.num2());
        }
        else if (numeralSystem == NumeralSystemName.HEX) {
            result = hexCalculationService.addTwoNumbers(request.num1(), request.num2());
        }
        else {
            throw new IllegalAccessException();
        }

        return new MultipurposeCalculateResponse(result);
    }
}