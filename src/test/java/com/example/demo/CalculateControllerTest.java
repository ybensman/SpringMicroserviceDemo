package com.example.demo;

import com.example.demo.config.CalculateConfiguration;
import com.example.demo.controller.CalculateController;
import com.example.demo.service.CalculationService;
import com.example.demo.service.CalculationServiceFactory;
import com.example.demo.service.PrintConfigService;
import com.example.demo.service.RandomNumbersService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(CalculateController.class)
//@Import(CalculationServiceFactory.class)
class CalculateControllerTest {
    @Autowired
    MockMvc mockMvc;

    @MockBean
    PrintConfigService printConfigService;

    @MockBean(name = "decCalculationService")
    CalculationService decCalculationService;

    @MockBean(name = "hexCalculationService")
    CalculationService hexCalculationService;

    @MockBean
    RandomNumbersService randomNumbersService;

    @MockBean
    CalculateConfiguration calculateConfiguration;



    @Test
    void givenAdd_whenValidInput_thenReturnCorrectResult() throws Exception {
        int num1 = 1, num2 = 2;

        mockMvc.perform(get("/calculate/add/{num1}/{num2}", num1, num2))
                .andExpect(status().isOk())
                .andExpect(content().string((String.valueOf(num1 + num2))));
    }

    @Test
    void givenAdd_whenInvalidInput_thenReturnBadRequest() throws Exception {
        int num1 = 1, num2 = 102;

        mockMvc.perform(get("/calculate/add/{num1}/{num2}", num1, num2))
                .andExpect(status().isBadRequest())
                .andExpect(content().string("Input validation failed: add1.num2: must be less than or equal to 100"));
    }

    @Test
    void givenGetUniversalAdd_whenValidInput_thenReturnCorrectResult() throws Exception {

        Mockito.when(calculateConfiguration.available()).thenReturn(true);
        Mockito.when(decCalculationService.addTwoNumbers("1", "2")).thenReturn("3");

        int num1 = 1, num2 = 2;

        mockMvc.perform(get("/calculate/universalAdd")
                        .param("num1", "1")
                        .param("num2", "2")
                        .param("numeralSystem","DEC"))
                .andExpect(status().isOk())
                .andExpect(content().string(String.valueOf(num1 + num2)))
        ;
    }

    @Test
    void givenUniversalAdd_whenValidInput_thenReturnCorrectResult() throws Exception {

        Mockito.when(calculateConfiguration.available()).thenReturn(true);
        Mockito.when(decCalculationService.addTwoNumbers("1", "2")).thenReturn("3");

        int num1 = 1, num2 = 2;
        String body = """
                        {
                            "num1": %d,
                            "num2": %d
                        }
                        """.formatted(num1, num2);

        mockMvc.perform(post("/calculate/universalAdd")
                        .contentType(MediaType.APPLICATION_JSON)
                        .param("numeralSystem","DEC")
                        .content(body))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.result").value(String.valueOf(num1 + num2)))
        ;
    }

    @Test
    void givenUniversalAdd_whenMissingNumeralSystem_returnBadRequest() throws Exception {
        int num1 = 1, num2 = 10;
        String body = """
                        {
                            "num1": %d,
                            "num2": %d
                        }
                        """.formatted(num1, num2);

        mockMvc.perform(post("/calculate/universalAdd")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(body))
                .andExpect(status().isBadRequest())
        ;
    }

    @Test
    void givenUniversalAdd_whenInvalidRequestParam_thenReturnBadRequest() throws Exception {

        Mockito.when(calculateConfiguration.available()).thenReturn(true);

        int num1 = 1, num2 = 10;
        String body = """
                        {
                            "num1": %d,
                            "num2": %d
                        }
                        """.formatted(num1, num2);

        mockMvc.perform(post("/calculate/universalAdd")
                        .param("numeralSystem", "OCT")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(body))
                .andExpect(status().isBadRequest())
                .andExpect(content().string("Illegal Access!"))
        ;
    }

    @Test
    void givenUniversalAdd_whenInvalidInputBody_returnBadRequest() throws Exception {
        int num1 = 1, num2 = 101;
        String body = """
                        {
                            "num1": %d,
                            "num2": %d
                        }
                        """.formatted(num1, num2);

        mockMvc.perform(post("/calculate/universalAdd")
                        .contentType(MediaType.APPLICATION_JSON)
                        .param("numeralSystem", "DEC")
                        .content(body))
                .andExpect(status().isBadRequest())
                .andExpect(content().string(is(startsWith("Input validation failed"))))
        ;
    }
}
