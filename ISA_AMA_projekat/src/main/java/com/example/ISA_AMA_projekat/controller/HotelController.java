package com.example.ISA_AMA_projekat.controller;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.Iterator;
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
import com.example.ISA_AMA_projekat.model.RezervacijaHotel;
import com.example.ISA_AMA_projekat.model.Soba;
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
			value = "/search/{name_location}/{check_in}/{check_out}",
			method = RequestMethod.GET,
			produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<Hotel>> getHotelsSearch(@PathVariable("name_location") String name_location, @PathVariable("check_in") String check_in, @PathVariable("check_out") String check_out) throws ParseException{
		
		System.out.println("[HotelController: getHotelsSearch]: name_location-" + name_location + ", check_in-" + check_in + ", check_out-" + check_out);
		name_location = name_location.toLowerCase();
		String[] search = name_location.split(" ");
		
		DateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		Date date_check_in = format.parse(check_in);
		Date date_check_out = format.parse(check_out);
		
		List<Hotel> hotels = hotelService.search("%" + search[0] + "%");
		
		/*
		for(int i=0; i<hotels.size(); i++) {
			System.out.println("[HotelController: getHotelsSearch]: hotel name: " + hotels.get(i).getNaziv() + ", hotel address: " + hotels.get(i).getAdresa());
		}
		*/
		
		List<Hotel> hoteli = pretraga(hotels, search, date_check_in, date_check_out);
			
		return new ResponseEntity<List<Hotel>>(hoteli, HttpStatus.OK);
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
	
	public List<Hotel> pretraga(List<Hotel> hoteli, String[] search, Date check_in, Date check_out){
		List<Hotel> result = new ArrayList<Hotel>();
		
		for(int i=0; i<hoteli.size(); i++) {
			result.add(hoteli.get(i));
		}
		
		for(int j=0; j<hoteli.size(); j++) {
			System.out.println("[HotelController: pretraga]: naziv hotela: " + hoteli.get(j).getNaziv() + ", adresa hotela: " + hoteli.get(j).getAdresa());
			for(int i=0; i<search.length; i++) {
				if(!(hoteli.get(j).getAdresa().toLowerCase().contains(search[i]) || hoteli.get(j).getNaziv().toLowerCase().contains(search[i]))) {
					result.remove(hoteli.get(j));
					System.out.println("[HotelController: pretraga]: obrisan hotel sa nazivom - " + hoteli.get(j).getNaziv());
				}
			}
		}
		
		boolean postojiTerminSoba;
		boolean postojiSoba;
		
		for (int i=0; i<result.size(); i++) {
			Hotel hotel = result.get(i);
			postojiSoba = false;
			System.out.println("[HotelController: pretraga]: iteracija pomocu iteratora; naziv hotela: " + hotel.getNaziv());
			for(Iterator<Soba> iteratorSoba = hotel.getSobe().iterator(); iteratorSoba.hasNext();) {
				System.out.println("[HotelController: pretraga]: usao u for za prolazak kroz sobe; ima " + hotel.getSobe().size() + " soba.");
				Soba soba = (Soba) iteratorSoba.next();
				postojiTerminSoba = true;
				
				if(soba.getRezervacije().isEmpty()) {
					System.out.println("[HotelController: pretraga]: soba nema rezervacija.");
					postojiTerminSoba = true;
					postojiSoba = true;
					break;
				}else {
					for(Iterator<RezervacijaHotel> iteratorRezervacija = soba.getRezervacije().iterator(); iteratorRezervacija.hasNext();) {
						System.out.println("[HotelController: pretraga]: postoje rezervacije za sobu; ima ih " + soba.getRezervacije().size() + ".");
						RezervacijaHotel rezervacijaHotel = iteratorRezervacija.next();
						System.out.println("[HotelController: pretraga]: zahtev:  datum dolaska: " + check_in + ", datum odlaska: " + check_out + ".");
						System.out.println("[HotelController: pretraga]: rezervacija: " + rezervacijaHotel.getId() + ", datum dolaska: " + rezervacijaHotel.getDatum_dolaska() + ", datum odlaska: " + rezervacijaHotel.getDatum_odlaska() + ".");
						System.out.println("[HotelController: pretraga]: " + check_in.equals(rezervacijaHotel.getDatum_dolaska()));
						System.out.println("[HotelController: pretraga]: " + check_in.after(rezervacijaHotel.getDatum_dolaska()));
						System.out.println("[HotelController: pretraga]: " + check_in.before(rezervacijaHotel.getDatum_odlaska()));
						if( check_in.equals(rezervacijaHotel.getDatum_dolaska()) || check_in.equals(rezervacijaHotel.getDatum_odlaska()) || check_out.equals(rezervacijaHotel.getDatum_dolaska()) || ((rezervacijaHotel.getDatum_dolaska()).after(check_in) && (rezervacijaHotel.getDatum_dolaska()).before(check_out)) || (check_in.after(rezervacijaHotel.getDatum_dolaska()) && check_in.before(rezervacijaHotel.getDatum_odlaska())) || (check_out.after(rezervacijaHotel.getDatum_dolaska()) && check_out.before(rezervacijaHotel.getDatum_odlaska()))) {
							System.out.println("[HotelController: pretraga]: soba je zauzeta u trazenom periodu.");
							postojiTerminSoba = false;
							break;
						}else {
							System.out.println("[HotelController: pretraga]: soba je slobodna u trazenom periodu.");
						}
					}
					
					if(postojiTerminSoba) {
						postojiSoba = true;
						break;
					}
				}
			}
			
			if(!postojiSoba) {
				result.remove(hotel);
				i--;
			}
		}
		
		return result;
	}
}
