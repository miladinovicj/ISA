package com.example.ISA_AMA_projekat.controller;

import static com.example.ISA_AMA_projekat.constants.HotelConstants.DB_COUNT;
import static com.example.ISA_AMA_projekat.constants.HotelConstants.DB_NAZIV;
import static com.example.ISA_AMA_projekat.constants.HotelConstants.DB_PROMOTIVNI_OPIS;
import static com.example.ISA_AMA_projekat.constants.HotelConstants.DB_PROSECNA_OCENA;
import static org.hamcrest.Matchers.hasItem;
import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;



import java.nio.charset.Charset;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;




import com.example.ISA_AMA_projekat.constants.HotelConstants;
import com.example.ISA_AMA_projekat.model.Korisnik;

@RunWith(SpringRunner.class)
@SpringBootTest
public class AuthenticationControllerTest {

	private static final String URL_PREFIX = "/api/auth";

	private MediaType contentType = new MediaType(MediaType.APPLICATION_JSON.getType(),
			MediaType.APPLICATION_JSON.getSubtype(), Charset.forName("utf8"));
	
	private MockMvc mockMvc;

	@Autowired
	private WebApplicationContext webApplicationContext;

	@Before
	public void setup() {
		this.mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
	}
	
	
	
	@Test
	public void createAuthenticationTokenTest() throws Exception {
		
		Korisnik korisnik = new Korisnik("andrijana.jeremi@gmail.com", "$2a$04$Vbug2lwwJGrvUXTj6z7ff.97IzVBkrJ1XfApfGNl.Z695zqcnPYra");
		
		mockMvc.perform(get(URL_PREFIX + "/login")).andExpect(status().isOk())
		.andExpect(content().contentType(contentType)).andExpect(jsonPath("$", hasSize(1)))
		.andExpect(jsonPath("$.[*].email").value(hasItem(korisnik.getEmail())))
		.andExpect(jsonPath("$.[*].lozinka").value(hasItem("123")));
		
	}

}
