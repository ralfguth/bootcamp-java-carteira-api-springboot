package br.com.alura.carteira.controller;

import javax.transaction.Transactional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
@Transactional
class UsuarioControllerTest {

	@Autowired
	private MockMvc mvc;
	// o MockMvc simula uma requisição http para a aplicação

	@Test
	void naoDeveriaCadastrarUsuarioComDadosIncompletos() throws Exception {
		String json = "{}";

		mvc
		.perform(MockMvcRequestBuilders.post("/usuarios").contentType(MediaType.APPLICATION_JSON).content(json))
		.andExpect(MockMvcResultMatchers.status().isBadRequest());
	}

	@Test
	void deveriaCadastrarUsuarioComDadosCompletos() throws Exception {
		String json = "{\"nome\":\"fulano\", \"login\":\"fulano@email.com\"}";
		
		mvc
		.perform(MockMvcRequestBuilders.post("/usuarios").contentType(MediaType.APPLICATION_JSON).content(json))
		.andExpect(MockMvcResultMatchers.status().isCreated())
		.andExpect(MockMvcResultMatchers.header().exists("Location"))
		.andExpect(MockMvcResultMatchers.content().json(json));
	}

}
