package com.example.ISA_AMA_projekat.controller;

import static org.hamcrest.Matchers.hasItem;
import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
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
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.WebApplicationContext;

import com.example.ISA_AMA_projekat.TestUtil;
import com.example.ISA_AMA_projekat.constants.HotelConstants;
import com.example.ISA_AMA_projekat.constants.RezervacijaHotelConstants;
import com.example.ISA_AMA_projekat.constants.SobaConstants;
import com.example.ISA_AMA_projekat.model.Hotel;
import com.example.ISA_AMA_projekat.model.RezervacijaHotel;

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
		.andExpect(content().contentType(contentType))
		.andExpect(jsonPath("$", hasSize(DB_COUNT)))
		.andExpect(jsonPath("$.[*].id").value(hasItem(HotelConstants.DB_ID.intValue())))
		.andExpect(jsonPath("$.[*].naziv").value(hasItem(DB_NAZIV)))
		.andExpect(jsonPath("$.[*].promotivni_opis").value(hasItem(DB_PROMOTIVNI_OPIS)))
		.andExpect(jsonPath("$.[*].prosecna_ocena").value(hasItem(DB_PROSECNA_OCENA)))
		.andExpect(jsonPath("$.[*].id_admin").value(hasItem(DB_ADMIN_ID)));
	}
	
	@Test
	public void testGetHotelsForAdmin() throws Exception {
		mockMvc.perform(get(URL_PREFIX + "/all_admin")).andExpect(status().isOk())
		.andExpect(content().contentType(contentType))
		.andExpect(jsonPath("$", hasSize(DB_COUNT)))
		.andExpect(jsonPath("$.[*].id").value(hasItem(HotelConstants.DB_ID.intValue())))
		.andExpect(jsonPath("$.[*].naziv").value(hasItem(DB_NAZIV)))
		.andExpect(jsonPath("$.[*].promotivni_opis").value(hasItem(DB_PROMOTIVNI_OPIS)))
		.andExpect(jsonPath("$.[*].prosecna_ocena").value(hasItem(DB_PROSECNA_OCENA)))
		.andExpect(jsonPath("$.[*].id_admin").value(hasItem(DB_ADMIN_ID)));
	}
	
	@Test
	public void testGetHotelsSearch() throws Exception {
		mockMvc.perform(get(URL_PREFIX + "/search/" + HotelConstants.DB_NAZIV + "/" + HotelConstants.DB_DATE_STR + "/" + HotelConstants.DB_DATE_STR + "/" + HotelConstants.DB_ADULTS))
		.andExpect(status().isOk())
		.andExpect(content().contentType(contentType))
		.andExpect(jsonPath("$", hasSize(DB_COUNT)))
		.andExpect(jsonPath("$.[*].id").value(hasItem(HotelConstants.DB_ID.intValue())))
		.andExpect(jsonPath("$.[*].naziv").value(hasItem(DB_NAZIV)))
		.andExpect(jsonPath("$.[*].promotivni_opis").value(hasItem(DB_PROMOTIVNI_OPIS)))
		.andExpect(jsonPath("$.[*].prosecna_ocena").value(hasItem(DB_PROSECNA_OCENA)))
		.andExpect(jsonPath("$.[*].id_admin").value(hasItem(DB_ADMIN_ID)));
		
	}
	
	@Test
	public void testGetRezervacija() throws Exception {
		mockMvc.perform(get(URL_PREFIX + "/rezervacija/" + HotelConstants.DB_DATE_STR + "/" + HotelConstants.DB_DATE_STR))
		.andExpect(status().isOk())
		.andExpect(content().contentType(contentType))
		.andExpect(jsonPath("$.id").value(RezervacijaHotelConstants.DB_ID.intValue()))
		.andExpect(jsonPath("$.datum_dolaska").value(RezervacijaHotelConstants.DB_DATE))
		.andExpect(jsonPath("$.datum_odlaska").value(RezervacijaHotelConstants.DB_DATE))
		.andExpect(jsonPath("$.brza").value(RezervacijaHotelConstants.DB_BRZA))
		.andExpect(jsonPath("$.broj_nocenja").value(RezervacijaHotelConstants.DB_BROJ_NOCENJA))
		.andExpect(jsonPath("$.aktivirana").value(RezervacijaHotelConstants.DB_AKTIVIRANA));;
	}
	
	@Test
	public void testGetHotel() throws Exception {
		mockMvc.perform(get(URL_PREFIX + "/"+ HotelConstants.DB_ID + "/" + HotelConstants.DB_NAZIV + "/" + HotelConstants.DB_DATE_STR + "/" + HotelConstants.DB_DATE_STR + "/" + HotelConstants.DB_ADULTS))
		.andExpect(status().isOk())
		.andExpect(content().contentType(contentType))
		.andExpect(jsonPath("$.id").value(HotelConstants.DB_ID.intValue()))
		.andExpect(jsonPath("$.naziv").value(DB_NAZIV))
		.andExpect(jsonPath("$.promotivni_opis").value(DB_PROMOTIVNI_OPIS))
		.andExpect(jsonPath("$.prosecna_ocena").value(DB_PROSECNA_OCENA))
		.andExpect(jsonPath("$.id_admin").value(DB_ADMIN_ID));
		
	}
	
	@Test
	public void testGetOneHotel() throws Exception {
		mockMvc.perform(get(URL_PREFIX  + "/admin/"+ HotelConstants.DB_ADMIN_ID + "/" + HotelConstants.DB_ID))
		.andExpect(status().isOk())
		.andExpect(content().contentType(contentType))
		.andExpect(jsonPath("$.id").value(HotelConstants.DB_ID.intValue()))
		.andExpect(jsonPath("$.naziv").value(DB_NAZIV))
		.andExpect(jsonPath("$.promotivni_opis").value(DB_PROMOTIVNI_OPIS))
		.andExpect(jsonPath("$.prosecna_ocena").value(DB_PROSECNA_OCENA))
		.andExpect(jsonPath("$.id_admin").value(DB_ADMIN_ID));
		
	}
	
	@Test
	public void testGetHotelSpecialPrice() throws Exception {
		mockMvc.perform(get(URL_PREFIX + "/specialPrice/" + SobaConstants.DB_ID + "/" + HotelConstants.DB_DATE_STR + "/" + HotelConstants.DB_DATE_STR + "/" + HotelConstants.DB_ADULTS))
		.andExpect(status().isOk())
		.andExpect(content().contentType(contentType))
		.andExpect(jsonPath("$", hasSize(DB_COUNT)))
		.andExpect(jsonPath("$.[*].id").value(hasItem(SobaConstants.DB_ID.intValue())))
		.andExpect(jsonPath("$.[*].cena_nocenja").value(hasItem(SobaConstants.DB_CENA_NOCENJA)))
		.andExpect(jsonPath("$.[*].opis").value(hasItem(SobaConstants.DB_OPIS)))
		.andExpect(jsonPath("$.[*].prosecna_ocena").value(hasItem(SobaConstants.DB_PROSECNA_OCENA)))
		.andExpect(jsonPath("$.[*].broj_kreveta").value(hasItem(SobaConstants.DB_BROJ_KREVETA)))
		.andExpect(jsonPath("$.[*].zauzeta").value(hasItem(SobaConstants.DB_ZAUZETA)))
		.andExpect(jsonPath("$.[*].cena_popust").value(hasItem(SobaConstants.DB_CENA_POPUST)));
		
	}
	
	@Test
	@Transactional
	@Rollback(true)
	public void testSaveStudent() throws Exception {
		Hotel hotel = new Hotel();
		hotel.setNaziv(NEW_NAZIV);
		hotel.setPromotivni_opis(NEW_PROMOTIVNI_OPIS);
		hotel.setProsecna_ocena(NEW_PROSECNA_OCENA);
		hotel.setId_admin(NEW_ADMIN_ID);

		String json = TestUtil.json(hotel);
		this.mockMvc.perform(post(URL_PREFIX + "/save").contentType(contentType).content(json)).andExpect(status().isCreated());
	}
	
	@Test
	public void testAddUsluga() throws Exception {
		RezervacijaHotel rezervacijaHotel = new RezervacijaHotel();
		rezervacijaHotel.setBroj_nocenja(RezervacijaHotelConstants.DB_BROJ_NOCENJA);
		rezervacijaHotel.setDatum_rezervacije(RezervacijaHotelConstants.DB_DATE);
		rezervacijaHotel.setDatum_dolaska(RezervacijaHotelConstants.DB_DATE);
		rezervacijaHotel.setDatum_odlaska(RezervacijaHotelConstants.DB_DATE);
		rezervacijaHotel.setUkupna_cena(RezervacijaHotelConstants.DB_UKUPNA_CENA);
		rezervacijaHotel.setBrza(RezervacijaHotelConstants.DB_BRZA);
		rezervacijaHotel.setPopust(RezervacijaHotelConstants.DB_POPUST);
		rezervacijaHotel.setAktivirana(RezervacijaHotelConstants.DB_AKTIVIRANA);

		String json = TestUtil.json(rezervacijaHotel);
		this.mockMvc.perform(post(URL_PREFIX + "/add_usluga/" + DB_ID).contentType(contentType).content(json)).andExpect(status().isOk());
	}
	
	@Test
	public void testRemoveUsluga() throws Exception {
		
		RezervacijaHotel rezervacijaHotel = new RezervacijaHotel();
		rezervacijaHotel.setBroj_nocenja(RezervacijaHotelConstants.DB_BROJ_NOCENJA);
		rezervacijaHotel.setDatum_rezervacije(RezervacijaHotelConstants.DB_DATE);
		rezervacijaHotel.setDatum_dolaska(RezervacijaHotelConstants.DB_DATE);
		rezervacijaHotel.setDatum_odlaska(RezervacijaHotelConstants.DB_DATE);
		rezervacijaHotel.setUkupna_cena(RezervacijaHotelConstants.DB_UKUPNA_CENA);
		rezervacijaHotel.setBrza(RezervacijaHotelConstants.DB_BRZA);
		rezervacijaHotel.setPopust(RezervacijaHotelConstants.DB_POPUST);
		rezervacijaHotel.setAktivirana(RezervacijaHotelConstants.DB_AKTIVIRANA);

		String json = TestUtil.json(rezervacijaHotel);
		this.mockMvc.perform(post(URL_PREFIX + "/remove_usluga/" + DB_ID).contentType(contentType).content(json)).andExpect(status().isOk());
	}
	
	@Test
	public void testGetNumberOfDays() throws Exception {
		mockMvc.perform(post(URL_PREFIX + "/get_number_of_days/" + HotelConstants.DB_DATE_STR + "/" + HotelConstants.DB_DATE_STR))
		.andExpect(status().isOk())
		.andExpect(content().contentType(contentType));
		
	}
	
	@Test
	public void testGetSoba() throws Exception {
		mockMvc.perform(post(URL_PREFIX + "/get_soba/" + SobaConstants.DB_ID))
		.andExpect(status().isOk())
		.andExpect(content().contentType(contentType))
		.andExpect(jsonPath("$.id").value(SobaConstants.DB_ID.intValue()))
		.andExpect(jsonPath("$.cena_nocenja").value(SobaConstants.DB_CENA_NOCENJA))
		.andExpect(jsonPath("$.opis").value(SobaConstants.DB_OPIS))
		.andExpect(jsonPath("$.prosecna_ocena").value(SobaConstants.DB_PROSECNA_OCENA))
		.andExpect(jsonPath("$.broj_kreveta").value(SobaConstants.DB_BROJ_KREVETA))
		.andExpect(jsonPath("$.zauzeta").value(SobaConstants.DB_ZAUZETA))
		.andExpect(jsonPath("$.cena_popust").value(SobaConstants.DB_CENA_POPUST));
		
	}
	
	@Test
	@Transactional
	@Rollback(true)
	public void testBookRoom() throws Exception {
		
		RezervacijaHotel rezervacijaHotel = new RezervacijaHotel();
		rezervacijaHotel.setBroj_nocenja(RezervacijaHotelConstants.DB_BROJ_NOCENJA);
		rezervacijaHotel.setDatum_rezervacije(RezervacijaHotelConstants.DB_DATE);
		rezervacijaHotel.setDatum_dolaska(RezervacijaHotelConstants.DB_DATE);
		rezervacijaHotel.setDatum_odlaska(RezervacijaHotelConstants.DB_DATE);
		rezervacijaHotel.setUkupna_cena(RezervacijaHotelConstants.DB_UKUPNA_CENA);
		rezervacijaHotel.setBrza(RezervacijaHotelConstants.DB_BRZA);
		rezervacijaHotel.setPopust(RezervacijaHotelConstants.DB_POPUST);
		rezervacijaHotel.setAktivirana(RezervacijaHotelConstants.DB_AKTIVIRANA);

		String json = TestUtil.json(rezervacijaHotel);
		this.mockMvc.perform(put(URL_PREFIX + "/book_room/" + + SobaConstants.DB_ID + "/" + RezervacijaHotelConstants.DB_ID).contentType(contentType).content(json))
		.andExpect(status().isOk())
		.andExpect(content().contentType(contentType))
		.andExpect(jsonPath("$.id").value(SobaConstants.DB_ID.intValue()))
		.andExpect(jsonPath("$.cena_nocenja").value(SobaConstants.DB_CENA_NOCENJA))
		.andExpect(jsonPath("$.opis").value(SobaConstants.DB_OPIS))
		.andExpect(jsonPath("$.prosecna_ocena").value(SobaConstants.DB_PROSECNA_OCENA))
		.andExpect(jsonPath("$.broj_kreveta").value(SobaConstants.DB_BROJ_KREVETA))
		.andExpect(jsonPath("$.zauzeta").value(SobaConstants.DB_ZAUZETA))
		.andExpect(jsonPath("$.cena_popust").value(SobaConstants.DB_CENA_POPUST));
	}
}
