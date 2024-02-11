package fr.humanbooster.fx.plages.controller;


import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;

@SpringBootTest
@AutoConfigureMockMvc // On demande à Spring de créer et de configurer un objet
// qui va imiter ce que fait le navigateur Internet
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class ClientControllerTest {

	@Autowired
	private MockMvc mockMvc;
	
	@Test
	@WithMockUser(roles = "USER")
	void testerGetClientsEnTantQueClient() throws Exception {
	
		MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/clients");

		mockMvc.perform(requestBuilder)
			 .andExpect(status().isForbidden())
			 .andDo(MockMvcResultHandlers.print());
	}
	
}