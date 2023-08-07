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

    @MockBean
    @Qualifier("decCalculationService")
    CalculationService decCalculationService;

    @MockBean
    @Qualifier("hexCalculationService")
    CalculationService hexCalculationService;

    @MockBean
    RandomNumbersService randomNumbersService;

    @MockBean
    CalculateConfiguration calculateConfiguration;



    @Test
    void testAdd_whenValidInput_thenReturnCorrectResult() throws Exception {
        int num1 = 1, num2 = 2;

        mockMvc.perform(get("/calculate/add/{num1}/{num2}", num1, num2))
                .andExpect(status().isOk())
                .andExpect(content().string((String.valueOf(num1 + num2))));
    }

    @Test
    void testGetMultipurposeAdd_whenValidInput_thenReturnCorrectResult() throws Exception {

        Mockito.when(calculateConfiguration.isAvailable()).thenReturn(true);
        Mockito.when(decCalculationService.addTwoNumbers("1", "2")).thenReturn("3");

        int num1 = 1, num2 = 2;

        mockMvc.perform(get("/calculate/multipurposeAdd")
                        .contentType(MediaType.APPLICATION_JSON)
                        .param("num1", "1")
                        .param("num2", "2")
                        .param("numeralSystem","DEC"))
                .andExpect(status().isOk())
                .andExpect(content().string(String.valueOf(num1 + num2)))
        ;
    }

    @Test
    void testMultipurposeAdd_whenValidInput_thenReturnCorrectResult() throws Exception {

        Mockito.when(calculateConfiguration.isAvailable()).thenReturn(true);
        Mockito.when(decCalculationService.addTwoNumbers("1", "2")).thenReturn("3");

        int num1 = 1, num2 = 2;
        String body = """
                        {
                            "num1": %d,
                            "num2": %d
                        }
                        """.formatted(num1, num2);

        mockMvc.perform(post("/calculate/multipurposeAdd")
                        .contentType(MediaType.APPLICATION_JSON)
                        .param("numeralSystem","DEC")
                        .content(body))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.result", is(String.valueOf(num1 + num2))))
        ;
    }

    @Test
    void testMultipurposeAdd_whenMissingRequestParam_returnBadRequest() throws Exception {
        int num1 = 1, num2 = 10;
        String body = """
                        {
                            "num1": %d,
                            "num2": %d
                        }
                        """.formatted(num1, num2);

        mockMvc.perform(post("/calculate/multipurposeAdd")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(body))
                .andExpect(status().isBadRequest())
        ;
    }

    @Test
    void testMultipurposeAdd_whenInvalidNumeralSystem_thenReturnBadRequest() throws Exception {

        Mockito.when(calculateConfiguration.isAvailable()).thenReturn(true);

        int num1 = 1, num2 = 10;
        String body = """
                        {
                            "num1": %d,
                            "num2": %d
                        }
                        """.formatted(num1, num2);

        mockMvc.perform(post("/calculate/multipurposeAdd")
                        .param("numeralSystem", "OCT")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(body))
                .andExpect(status().isBadRequest())
                .andExpect(content().string("Illegal Access!"))
        ;
    }
}
