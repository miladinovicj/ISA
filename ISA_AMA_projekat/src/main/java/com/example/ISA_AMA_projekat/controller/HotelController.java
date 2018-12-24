package com.example.ISA_AMA_projekat.controller;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.NoSuchElementException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.example.ISA_AMA_projekat.model.Hotel;
import com.example.ISA_AMA_projekat.service.HotelService;


@RestController
@RequestMapping(value="api/hotels")
public class HotelController {

	@Autowired
	private HotelService hotelService;
	
	@RequestMapping(
			value = "/all",
			method = RequestMethod.GET,
			produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Collection<Hotel>> getHotels(){
		
		Collection<Hotel> hotels = hotelService.findAll();
		
		return new ResponseEntity<Collection<Hotel>>(hotels, HttpStatus.OK);
	}
	
	@RequestMapping(
			value = "/search/{name_location}",
			method = RequestMethod.GET,
			produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Collection<Hotel>> getHotelsSearch(@PathVariable("name_location") String name_location){
		
		name_location = name_location.toLowerCase();
		String[] search = name_location.split(" ");

		String forSearch = "%";
		
		for(int i=0; i<search.length; i++) {
			forSearch += search[i] + "%";
		}
		
		
		//Collection<Hotel> hotels = hotelService.search(forSearch);
		List<Hotel> hotels = hotelService.search("%" + search[0] + "%");
		for(int i=0; i<hotels.size(); i++) {
			System.out.println("hotel name: " + hotels.get(i).getNaziv() + ", hotel address: " + hotels.get(i).getAdresa());
		}
		
		if(search.length>1) {
			System.out.println("Search ima vise reci");
			Collection<Hotel> hoteli = pretraga(hotels, search);
			
			return new ResponseEntity<Collection<Hotel>>(hoteli, HttpStatus.OK);
		}else {
			System.out.println("Search ima jednu rec");
			return new ResponseEntity<Collection<Hotel>>(hotels, HttpStatus.OK);
		}
		
	}
	
	@RequestMapping(
			value = "/{id}",
			method = RequestMethod.GET,
			produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Hotel> getHotel(@PathVariable("id") Integer id)
	{
		System.out.println("usao u metodu api/hotels/{id}");
		try
		{
			Hotel found = hotelService.findById(id).get();
			System.out.println("Nasao hotel sa id:" + found.getId());
			return new ResponseEntity<Hotel>(found, HttpStatus.OK);
		}
		catch(NoSuchElementException e)
		{
			System.out.println("Ne postoji hotel sa id: " + id);
			return null; 	
		}
	}
	
	@RequestMapping(
			value = "/save",
			method = RequestMethod.POST,
			consumes=MediaType.APPLICATION_JSON_VALUE,
			produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Hotel> saveHotel(@RequestBody Hotel hotel) throws Exception{
		
		Hotel postoji = hotelService.findByNaziv(hotel.getNaziv());
		
		if(postoji != null) {
			System.out.println("Hotel sa ovim nazivom vec postoji u bazi");
			return new ResponseEntity<Hotel>(postoji, HttpStatus.CONFLICT);
		}else {
			Hotel saved = hotelService.save(hotel);
			return new ResponseEntity<Hotel>(saved, HttpStatus.CREATED);
		}
		/*
		Hotel saved = null;
		try {
			saved = hotelService.save(hotel);
		} catch(Exception e) {
			System.out.println("Hotel sa ovim nazivom vec postoji u bazi");
			return new ResponseEntity<Hotel>(postoji, HttpStatus.CONFLICT);
		}
		
		return new ResponseEntity<Hotel>(saved, HttpStatus.CREATED);
		*/
	}
	
	public Collection<Hotel> pretraga(List<Hotel> hoteli, String[] search){
		Collection<Hotel> result = new ArrayList<Hotel>();
		
		for(int j=0; j<hoteli.size(); j++) {
			for(int i=1; i<search.length; i++) {
				System.out.println("adresa hotela: " + hoteli.get(j).getAdresa().toLowerCase() + ", search: " + search[i]);
				System.out.println("naziv hotela: " + hoteli.get(j).getNaziv().toLowerCase() + ", search: " + search[i]);
				if(hoteli.get(j).getAdresa().toLowerCase().contains(search[i]) || hoteli.get(j).getNaziv().toLowerCase().contains(search[i])) {
					result.add(hoteli.get(j));
				}
			}
		}
		
		
		return result;
	}
}
