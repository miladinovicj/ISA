package com.example.ISA_AMA_projekat.controller;

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

import com.example.ISA_AMA_projekat.TestUtil;
import com.example.ISA_AMA_projekat.constants.HotelConstants;
import com.example.ISA_AMA_projekat.model.Hotel;


import static com.example.ISA_AMA_projekat.constants.HotelConstants.NEW_ID;
import static com.example.ISA_AMA_projekat.constants.HotelConstants.NEW_NAZIV;
import static com.example.ISA_AMA_projekat.constants.HotelConstants.NEW_PROMOTIVNI_OPIS;
import static com.example.ISA_AMA_projekat.constants.HotelConstants.NEW_PROSECNA_OCENA;
import static com.example.ISA_AMA_projekat.constants.HotelConstants.NEW_ADMIN_ID;
import static com.example.ISA_AMA_projekat.constants.HotelConstants.DB_ID;
import static com.example.ISA_AMA_projekat.constants.HotelConstants.DB_NAZIV;
import static com.example.ISA_AMA_projekat.constants.HotelConstants.DB_PROMOTIVNI_OPIS;
import static com.example.ISA_AMA_projekat.constants.HotelConstants.DB_PROSECNA_OCENA;
import static com.example.ISA_AMA_projekat.constants.HotelConstants.DB_ADMIN_ID;
import static com.example.ISA_AMA_projekat.constants.HotelConstants.DB_COUNT;

@RunWith(SpringRunner.class)
@SpringBootTest
public class HotelControllerTest {

	private static final String URL_PREFIX = "/api/hotels";
	
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
	public void testGetHotels() throws Exception {
		mockMvc.perform(get(URL_PREFIX + "/all")).andExpect(status().isOk())
		.andExpect(content().contentType(contentType)).andExpect(jsonPath("$", hasSize(DB_COUNT)))
		.andExpect(jsonPath("$.[*].id").value(hasItem(HotelConstants.DB_ID.intValue())))
		.andExpect(jsonPath("$.[*].naziv").value(hasItem(DB_NAZIV)))
		.andExpect(jsonPath("$.[*].promotivni_opis").value(hasItem(DB_PROMOTIVNI_OPIS)))
		.andExpect(jsonPath("$.[*].prosecna_ocena").value(hasItem(DB_PROSECNA_OCENA)))
		.andExpect(jsonPath("$.[*].id_admin").value(hasItem(DB_ADMIN_ID)));
	}
}
