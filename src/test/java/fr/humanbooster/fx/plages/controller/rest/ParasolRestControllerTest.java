package fr.humanbooster.fx.plages.controller.rest;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

@SpringBootTest
@AutoConfigureMockMvc
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class ParasolRestControllerTest {

	@Autowired
	private MockMvc mockMvc; // cet objet imite ce que fait Postman, Insomnia, Swagger ou le front

	@Test
	@Order(1)
	void getParasolTest() throws Exception {
		MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/api/parasols/{id}", 1)
				.contentType(MediaType.APPLICATION_JSON);

		mockMvc.perform(requestBuilder).andExpect(MockMvcResultMatchers.jsonPath("$.id").value("1"))
				.andExpect(status().isOk()).andDo(MockMvcResultHandlers.print());
	}

	@Test
	@Order(2)
	void testerPatchParasol() throws Exception {
		String nouveauNumEmplacement = "42";
		
		MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders
				.patch("/api/parasols/{id}/{nouveauNumEmplacement}", "1", nouveauNumEmplacement)
				.contentType(MediaType.APPLICATION_JSON);

		mockMvc.perform(requestBuilder)
				.andExpect(MockMvcResultMatchers.jsonPath("$.numEmplacement").value(nouveauNumEmplacement))
				.andExpect(status().is2xxSuccessful())
				.andDo(MockMvcResultHandlers.print());
	}
	
	@Test
    @Order(5)
    void getParasolsPageTest() throws Exception {
        int page=3;
        int length=12;
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/api/parasols?page={page}&sort=file.numero,ASC&size={length}", page, length)
                .contentType(MediaType.APPLICATION_JSON);

        mockMvc.perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.jsonPath("$.pageable.pageNumber").value(page))
                .andExpect(MockMvcResultMatchers.jsonPath("$.content.length()").value(length))
                .andExpect(status().isOk())
                .andDo(MockMvcResultHandlers.print());
    }
}
