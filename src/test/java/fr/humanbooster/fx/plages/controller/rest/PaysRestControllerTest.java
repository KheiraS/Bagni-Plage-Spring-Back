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

import com.fasterxml.jackson.databind.ObjectMapper;

import fr.humanbooster.fx.plages.dto.PaysDto;

@SpringBootTest
@AutoConfigureMockMvc
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class PaysRestControllerTest {

	@Autowired
	private MockMvc mockMvc; // cet objet imite ce que fait Postman, Insomnia, Swagger ou le front

	@Autowired
	private ObjectMapper objectMapper; // cet objet va sérialiser des objets métier

	private PaysDto paysDto = new PaysDto();
	String code = "aa";
	String nom = "TEST";

	@Test
	@Order(1)
	void testerAjouterPays() throws Exception {

		paysDto.setCode(code);
		paysDto.setNom(nom);

		MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.post("/api/pays")
				// la méthode writeValueAsString invoquée sur l'objet objectMapper va produire
				// un contenu au format JSON qui est placé dans le corps de la requête HTTP (qui utilise la méthode POST)
				.content(objectMapper.writeValueAsString(paysDto))
				.contentType(MediaType.APPLICATION_JSON);

		// La méthode perform fait la même chose qu'un clic sur le bouton bleu Execute de Swagger
		mockMvc.perform(requestBuilder)
				.andExpect(MockMvcResultMatchers.jsonPath("$.code").value(code))
				.andExpect(MockMvcResultMatchers.jsonPath("$.nom").value(nom))
				.andExpect(status().isCreated())
				.andDo(MockMvcResultHandlers.print());
	}

	@Test
	@Order(2)
	void testerPatcherPays() throws Exception {
		
		String nouveauNom = "test2";
		
		MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.patch("/api/pays/{code}/{nom}", code, nouveauNom)
				.contentType(MediaType.APPLICATION_JSON);

		mockMvc.perform(requestBuilder)
				.andExpect(MockMvcResultMatchers.jsonPath("$.nom").value(nouveauNom))
				.andExpect(MockMvcResultMatchers.jsonPath("$.code").value(code))
				.andExpect(status().is2xxSuccessful())
				.andDo(MockMvcResultHandlers.print());
	}
	
	// Méthode qui vérifie que le patch envoie un 404 si le code du pays n'existe
	// pas en base
	@Test
	@Order(3)
	void testerPatcherPays404() throws Exception {
		String nouveauNom = "test2";
		String nouveauCode = "RR";
	        
	        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.patch("/api/pays/{code}/{nom}", nouveauCode, nouveauNom)
	                .contentType(MediaType.APPLICATION_JSON);

	        mockMvc.perform(requestBuilder)
	                //.andExpect(status().is4xxClientError())
	        .andExpect(status().is(404))
	                .andDo(MockMvcResultHandlers.print());
	    }
	 
	// méthode qui teste la suppression d'un pays
    @Test
    @Order(4)
    void testerDeletePays() throws Exception {
    	
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.delete("/api/pays/{code}", code)
                .contentType(MediaType.APPLICATION_JSON);

        mockMvc.perform(requestBuilder)
//                .andExpect(MockMvcResultMatchers.jsonPath("$").value(true))
        .andExpect(MockMvcResultMatchers.content().string("true"))
                .andExpect(status().is2xxSuccessful())
                .andDo(MockMvcResultHandlers.print());
    }
}