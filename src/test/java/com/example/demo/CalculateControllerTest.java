package com.example.demo;

import com.example.demo.controller.CalculateController;
import com.example.demo.service.CalculationServiceFactory;
import com.example.demo.service.PrintConfigService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(CalculateController.class)
@Import(CalculationServiceFactory.class)
class CalculateControllerTest {
    @Autowired
    MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;

    @MockBean
    PrintConfigService printConfigService;

    @Test
    void whenValidInput_thenReturnCorrectResult() throws Exception {
        int num1 = 1, num2 = 2;
        String body = """
                        {
                            "num1": %d,
                            "num2": %d
                        }
                        """.formatted(num1, num2);

        mockMvc.perform(post("/calculate/add")
                .contentType(MediaType.APPLICATION_JSON)
                .content(body))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.result", is(equalTo(num1 + num2))));
    }
}
