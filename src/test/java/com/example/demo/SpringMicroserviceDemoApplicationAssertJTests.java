package com.example.demo;

import com.example.demo.model.CalculateResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(properties = "--demo.course.name=SpringBootTest")
@AutoConfigureMockMvc
class SpringMicroserviceDemoApplicationAssertJTests {
    @Autowired
    MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;

    @Test
    void givenAddRequestBody_WhenValidInput_ReturnCorrectResult() throws Exception {
        int num1 = 1, num2 = 2;
        String inputBody = """
                {
                    "num1": %d,
                    "num2": %d
                }
                """.formatted(num1, num2);

        String response = mockMvc.perform(post("/calculate/universalAdd")
                        .contentType(MediaType.APPLICATION_JSON)
                        .param("numeralSystem", "DEC")
                        .content(inputBody))
                .andExpect(status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString()
                ;

        CalculateResponse calculateResponse = objectMapper.readValue(response, CalculateResponse.class);
        assertThat(calculateResponse.result()).isEqualTo(num1 + num2);
    }
}
