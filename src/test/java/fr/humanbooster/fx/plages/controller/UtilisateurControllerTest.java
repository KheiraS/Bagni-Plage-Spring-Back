package fr.humanbooster.fx.plages.controller;


import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

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
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

@SpringBootTest
@AutoConfigureMockMvc // On demande à Spring de créer et de configurer un objet
// qui va imiter ce que fait le navigateur Internet
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class UtilisateurControllerTest {

	@Autowired
	private MockMvc mockMvc;
	
	@Test
	@Order(1)
	void testerAccueil() throws Exception {
	
		// On prépare une requête en HTTP en indiquant la méthode (ds l'ex : get) puis en paramètre l'url
		MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/");

		mockMvc.perform(requestBuilder)
			.andExpect(view().name("index"))
			.andExpect(status().isOk());
	}
	
	@Test
	@Order(3)
	void testerConnexionPeppeAvecSucces() throws Exception {
	
		// La méthode remplit le champ username, le champ password et clique sur le bouton de connexion
		mockMvc.perform(MockMvcRequestBuilders.post("/login")
			.accept(MediaType.TEXT_HTML)
			.param("username", "peppe@humanbooster.fr")
			.param("password", "12345678"))
			.andExpect(MockMvcResultMatchers.redirectedUrl("clients"))
			.andExpect(status().isFound());
	}

	@Test
	@Order(2)
	void testerConnexionPeppeAvecEchec() throws Exception {
	
		mockMvc.perform(MockMvcRequestBuilders.post("/login")
			.accept(MediaType.TEXT_HTML)
			.param("username", "peppe@humanbooster.fr")
			.param("password", "abcd"))
			.andExpect(MockMvcResultMatchers.redirectedUrl("index?notification=Email%20ou%20mot%20passe%20incorrect"))
			.andExpect(status().isFound());
	}
	
	@Test
	@Order(4)
	void testerConnexionPlagisteAvecSucces() throws Exception {
	
		// La méthode remplit le champ username, le champ password et clique sur le bouton de connexion
		mockMvc.perform(MockMvcRequestBuilders.post("/login")
			.accept(MediaType.TEXT_HTML)
			.param("username", "plagiste@humanbooster.fr")
			.param("password", "12345678"))
			.andExpect(MockMvcResultMatchers.redirectedUrl("parasols"))
			.andExpect(status().isFound());
	}


}
