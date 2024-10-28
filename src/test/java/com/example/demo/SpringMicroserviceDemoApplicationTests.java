package com.example.demo;

import com.example.demo.model.CalculateResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest(properties = "--demo.course.name=SpringBootTest")
@AutoConfigureMockMvc
class SpringMicroserviceDemoApplicationTests {

	@Autowired
	MockMvc mockMvc;

	@Autowired
	ObjectMapper objectMapper;

	@Test
	void givenAddPathVariables_whenValidInput_thenReturnCorrectResult() throws Exception {
		int num1 = 1, num2 = 2;

		mockMvc.perform(get("/calculate/add/{num1}/{num2}", num1, num2))
				.andExpect(status().isOk())
				.andExpect(content().string(String.valueOf(num1 + num2)))
		;
	}

	@Test
	void givenAddRequestParams_whenValidInput_thenReturnCorrectResult() throws Exception {
		int num1 = 1, num2 = 2;
		String expectedValue = String.valueOf(num1 + num2);

		mockMvc.perform(get("/calculate/decimalAdd")
						.param("num1", String.valueOf(num1))
						.param("num2", String.valueOf(num2)))
				.andExpect(status().isOk())
				.andExpect(content().string(expectedValue))
		;
	}

	@Test
	void givenUniversalAdd_whenValidInputDecimalRadix_thenReturnCorrectResult() throws Exception {
		int num1 = 1, num2 = 9;
		int radix = 10;
		String expectedResult = Integer.toString(num1 + num2, radix);

		mockMvc.perform(get("/calculate/universalAdd")
						.param("num1", String.valueOf(num1))
						.param("num2", String.valueOf(num2))
						.param("numeralSystem", "DEC"))
				.andExpect(status().isOk())
				.andExpect(content().string(expectedResult))
				;
	}

	@Test
	void givenUniversalAdd_whenValidInputHexRadix_thenReturnCorrectResult() throws Exception {
		int num1 = 1, num2 = 9;
		int radix = 16;
		String expectedResult = Integer.toString(num1 + num2, radix);

		mockMvc.perform(get("/calculate/universalAdd")
						.param("num1", String.valueOf(num1))
						.param("num2", String.valueOf(num2))
						.param("numeralSystem", "HEX"))
				.andExpect(status().isOk())
				.andExpect(content().string(expectedResult))
		;
	}

	@Test
	void givenUniversalAdd_whenValidHexInputHexRadix_thenReturnCorrectResult() throws Exception {
		String num1 = "1", num2 = "f";
		String expectedResult = "10";

		mockMvc.perform(get("/calculate/universalAdd")
						.param("num1", num1)
						.param("num2", num2)
						.param("numeralSystem", "HEX"))
				.andExpect(status().isOk())
				.andExpect(content().string(expectedResult))
		;
	}

	@Test
	void givenUniversalAddRequestBody_whenValidInput_thenReturnCorrectResult() throws Exception {
		int num1 = 1, num2 = 2;
		String body = """
                        {
                            "num1": %d,
                            "num2": %d
                        }
                        """.formatted(num1, num2);

		String result = mockMvc.perform(post("/calculate/universalAdd")
						.contentType(MediaType.APPLICATION_JSON)
						.param("numeralSystem", "DEC")
						.content(body))
				.andExpect(status().isOk())
				.andReturn()
				.getResponse()
				.getContentAsString();

		CalculateResponse response = objectMapper.readValue(result, CalculateResponse.class);
		assertThat(response.result(), equalTo(num1 + num2));

		//
		// Another way to test it
		//
		mockMvc.perform(post("/calculate/universalAdd")
						.contentType(MediaType.APPLICATION_JSON)
						.param("numeralSystem", "DEC")
						.content(body))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.result", is(String.valueOf(num1 + num2))))
		;
	}

}
