package com.example.demo.controller;

import com.example.demo.model.CalculateRequest;
import com.example.demo.model.CalculateResponse;
import com.example.demo.model.NumeralSystemName;
import com.example.demo.model.UniversalCalculateRequest;
import com.example.demo.model.UniversalCalculateResponse;
import com.example.demo.service.CalculationService;
import com.example.demo.service.PrintConfigService;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.Digits;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

@RestController
@RequestMapping("calculate")
@Validated
public class CalculateController {
    PrintConfigService printConfigService;

    CalculationService decCalculationService;
    CalculationService hexCalculationService;

    CalculateController(PrintConfigService printConfigService,
                        @Qualifier("decCalculationService") CalculationService dec,
                        @Qualifier("hexCalculationService") CalculationService hex
                        ) {
        this.decCalculationService = dec;
        this.hexCalculationService = hex;
    }

    @GetMapping("/add/{num1}/{num2}")
    // Can be also:
    // @PathVariable(num1) int number1
    // @PathVariable(value = "num1") int number1
    int add1(@PathVariable @Min(1) @Max(100) int num1, @PathVariable @Min(1) @Max(100) int num2) {
        return num1 + num2;
    }

    @GetMapping("/add")
    int add(@RequestParam @Min(1) @Max(100) Integer num1,
            @RequestParam(required = false) @Min(1) @Max(100) Integer num2) {
        if (num2 == null) {
            num2 = 0;
        }

        return num1 + num2;
    }

    @PostMapping("/add")
    public CalculateResponse add(@Valid @RequestBody CalculateRequest request) {
        return new CalculateResponse(request.num1 + request.num2);
    }

    @PostMapping("/universalAdd")
    public UniversalCalculateResponse universalAdd(@RequestParam NumeralSystemName numeralSystem, @Valid @RequestBody UniversalCalculateRequest request) throws IllegalAccessException{
        String result = "";
        if (numeralSystem == NumeralSystemName.DEC) {
            result = decCalculationService.addTwoNumbers(request.num1(), request.num2());
        }
        else if (numeralSystem == NumeralSystemName.HEX) {
            result = hexCalculationService.addTwoNumbers(request.num1(), request.num2());
        }
        else {
            throw new IllegalAccessException();
        }

        return new UniversalCalculateResponse(result);
    }
}