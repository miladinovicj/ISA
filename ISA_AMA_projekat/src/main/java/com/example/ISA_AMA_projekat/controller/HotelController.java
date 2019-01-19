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
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.ISA_AMA_projekat.model.Hotel;
import com.example.ISA_AMA_projekat.model.Popust;
import com.example.ISA_AMA_projekat.model.RezervacijaHotel;
import com.example.ISA_AMA_projekat.model.Soba;
import com.example.ISA_AMA_projekat.model.Usluga;
import com.example.ISA_AMA_projekat.service.HotelService;
import com.example.ISA_AMA_projekat.service.RezervacijaHotelService;
import com.example.ISA_AMA_projekat.service.SobaService;
import com.example.ISA_AMA_projekat.service.UslugaService;


@RestController
@RequestMapping(value="api/hotels")
public class HotelController {

	@Autowired
	private HotelService hotelService;
	
	@Autowired
	private RezervacijaHotelService rezervacijaHotelService;
	
	@Autowired
	private UslugaService uslugaService;
	
	@Autowired
	private SobaService sobaService;
	
	@RequestMapping(
			value = "/all",
			method = RequestMethod.GET,
			produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Collection<Hotel>> getHotels(){
		
		Collection<Hotel> hotels = hotelService.findAll();
		
		return new ResponseEntity<Collection<Hotel>>(hotels, HttpStatus.OK);
	}
	
	@RequestMapping(
			value = "/search/{name_location}/{check_in}/{check_out}/{adults}",
			method = RequestMethod.GET,
			produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<Hotel>> getHotelsSearch(@PathVariable("name_location") String name_location, @PathVariable("check_in") String check_in, @PathVariable("check_out") String check_out, @PathVariable("adults") int adults) throws ParseException{
		
		System.out.println("[HotelController: getHotelsSearch]: name_location-" + name_location + ", check_in-" + check_in + ", check_out-" + check_out + ", adults-" + adults);
		name_location = name_location.toLowerCase();
		String[] search = name_location.split(" ");
		
		DateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		Date date_check_in = format.parse(check_in);
		Date date_check_out = format.parse(check_out);
		
		/*
		RezervacijaHotel rezervacijaHotel = new RezervacijaHotel();
		rezervacijaHotel.setDatum_dolaska(date_check_in);
		rezervacijaHotel.setDatum_odlaska(date_check_out);
		rezervacijaHotel.setBrza(false);
		int broj_nocenja = (int) Math.round((date_check_out.getTime() - date_check_in.getTime()) / (double) 86400000) + 1;
		rezervacijaHotel.setBroj_nocenja(broj_nocenja);
		rezervacijaHotel.setAktivirana(false);
		
		rezervacijaHotel = rezervacijaHotelService.save(rezervacijaHotel);
		request.getSession().setAttribute("rezervacijaHotel", rezervacijaHotel);
		*/
		
		List<Hotel> hotels = hotelService.search("%" + search[0] + "%");
		List<Hotel> hoteli = pretraga(hotels, search, date_check_in, date_check_out, adults);
		
			
		return new ResponseEntity<List<Hotel>>(hoteli, HttpStatus.OK);
	}
	
	@RequestMapping(
			value = "/rezervacija/{check_in}/{check_out}",
			method = RequestMethod.GET,
			produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<RezervacijaHotel> getRezervacija(@PathVariable("check_in") String check_in, @PathVariable("check_out") String check_out) throws ParseException{
		
		DateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		Date date_check_in = format.parse(check_in);
		Date date_check_out = format.parse(check_out);
		
		RezervacijaHotel rezervacijaHotel = new RezervacijaHotel();
		rezervacijaHotel.setDatum_dolaska(date_check_in);
		rezervacijaHotel.setDatum_odlaska(date_check_out);
		rezervacijaHotel.setBrza(false);
		int broj_nocenja = (int) Math.round((date_check_out.getTime() - date_check_in.getTime()) / (double) 86400000) + 1;
		rezervacijaHotel.setBroj_nocenja(broj_nocenja);
		rezervacijaHotel.setAktivirana(false);
		
		//rezervacijaHotel = rezervacijaHotelService.save(rezervacijaHotel);
			
		return new ResponseEntity<RezervacijaHotel>(rezervacijaHotel, HttpStatus.OK);
	}
	
	@RequestMapping(
			value = "/{id}/{check_in}/{check_out}/{adults}",
			method = RequestMethod.GET,
			produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Hotel> getHotel(@PathVariable("id") Integer id, @PathVariable("check_in") String check_in, @PathVariable("check_out") String check_out, @PathVariable("adults") int adults) throws ParseException
	{
		System.out.println("usao u metodu api/hotels/{id}; adults: " + adults);
		
		DateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		Date date_check_in = format.parse(check_in);
		Date date_check_out = format.parse(check_out);
		
		Hotel result = null;
		
		try{
			result = hotelService.findById(id).get();
			System.out.println("Nasao hotel sa id:" + result.getId());
		}catch(NoSuchElementException e)
		{
			System.out.println("Ne postoji hotel sa id: " + id);
			return null; 	
		}
		
		result = pretraga_hotel(result, date_check_in, date_check_out, adults);
		
		System.out.println("[HotelController: getHotel]: id pronadjenog hotela: " + result.getId());
		
		return new ResponseEntity<Hotel>(result, HttpStatus.OK);
	}
	
	@RequestMapping(
			value = "specialPrice/{id}/{check_in}/{check_out}/{adults}",
			method = RequestMethod.GET,
			produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Collection<Soba>> getHotelSpecialPrice(@PathVariable("id") Integer id, @PathVariable("check_in") String check_in, @PathVariable("check_out") String check_out, @PathVariable("adults") int adults) throws ParseException
	{
		System.out.println("usao u metodu api/hotels/specialPrice/{id}, id: " + id);
		
		DateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		Date date_check_in = format.parse(check_in);
		Date date_check_out = format.parse(check_out);
		
		Hotel hotel = null;
		
		try{
			hotel = hotelService.findById(id).get();
			System.out.println("[HotelController: getHotelSpecialPrice]: Nasao hotel sa id:" + hotel.getId());
		}catch(NoSuchElementException e)
		{
			System.out.println("Ne postoji hotel sa id: " + id);
			return null; 	
		}
		
		Collection<Soba> result = new ArrayList<Soba>();
		boolean postojiPopust, postojiTerminSoba;
		
		for(Iterator<Soba> iteratorSoba = hotel.getSobe().iterator(); iteratorSoba.hasNext();) {
			postojiPopust = false;
			postojiTerminSoba = true;
			Soba soba = iteratorSoba.next();
			for(Iterator<Popust> iteratorPopust = soba.getPopusti().iterator(); iteratorPopust.hasNext();) {
				Popust popust = iteratorPopust.next();
				if((date_check_in.equals(popust.getPocetak_vazenja()) || date_check_in.after(popust.getPocetak_vazenja())) && (date_check_out.equals(popust.getKraj_vazenja()) || date_check_out.before(popust.getKraj_vazenja()))) {
					System.out.println("[HotelController: getHotelSpecialPrice]: soba sa id: " + soba.getId() + " je na popustu u trazenom periodu.");
					postojiPopust = true;
					break;
				}else {
					System.out.println("[HotelController: getHotelSpecialPrice]: soba sa id: " + soba.getId() + " nije na popustu u trazenom periodu.");
				}
			}
			if(postojiPopust) {
				for(Iterator<RezervacijaHotel> iteratorRezervacija = soba.getRezervacije().iterator(); iteratorRezervacija.hasNext();) {
					RezervacijaHotel rezervacijaHotel = iteratorRezervacija.next();
					System.out.println("[HotelController: getHotelSpecialPrice]: zahtev:  datum dolaska: " + check_in + ", datum odlaska: " + check_out + ".");
					System.out.println("[HotelController: getHotelSpecialPrice]: rezervacija: " + rezervacijaHotel.getId() + ", datum dolaska: " + rezervacijaHotel.getDatum_dolaska() + ", datum odlaska: " + rezervacijaHotel.getDatum_odlaska() + ".");

					if(rezervacijaHotel.isAktivirana() && (date_check_in.equals(rezervacijaHotel.getDatum_dolaska()) || date_check_in.equals(rezervacijaHotel.getDatum_odlaska()) || date_check_out.equals(rezervacijaHotel.getDatum_dolaska()) || ((rezervacijaHotel.getDatum_dolaska()).after(date_check_in) && (rezervacijaHotel.getDatum_dolaska()).before(date_check_out)) || (date_check_in.after(rezervacijaHotel.getDatum_dolaska()) && date_check_in.before(rezervacijaHotel.getDatum_odlaska())) || (date_check_out.after(rezervacijaHotel.getDatum_dolaska()) && date_check_out.before(rezervacijaHotel.getDatum_odlaska())))) {
						System.out.println("[HotelController: getHotelSpecialPrice]: soba je zauzeta u trazenom periodu.");
						postojiTerminSoba = false;
						break;
					}else {
						System.out.println("[HotelController: getHotelSpecialPrice]: soba je slobodna u trazenom periodu.");
					}
				}
				
				if(postojiTerminSoba) {
					if(soba.getBroj_kreveta() <= adults) {
						System.out.println("[HotelController: getHotelSpecialPrice]: odgovara zbog broja osoba; dodata soba sa id:" + soba.getId());
						result.add(soba);
					}else {
						System.out.println("[HotelController: getHotelSpecialPrice]: soba sa id:" + soba.getId() + " ne odgovara zbog broja osoba");
					}
					
				}
			}
		}
		
		/*
		List<Soba> result = new ArrayList<Soba>();
		
		for(Iterator<Soba> iteratorSoba = hotel.getSobe().iterator(); iteratorSoba.hasNext();) {
			System.out.println("[HotelController: getHotelSpecialPrice]: usao u for za prolazak kroz sobe; ima " + hotel.getSobe().size() + " soba.");
			Soba soba = (Soba) iteratorSoba.next();
			
			if(isBrzaSoba(soba, date_check_in, date_check_out)) {
				System.out.println("[HotelController: getHotelSpecialPrice]: soba sa id: " + soba.getId() + " je brza soba.");
				result.add(soba);
			}
		}
		*/
		
		return new ResponseEntity<Collection<Soba>>(result, HttpStatus.OK);
	}
	
	/*@RequestMapping(
			value = "specialPrice/{id}/{check_in}/{check_out}",
			method = RequestMethod.GET,
			produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Collection<RezervacijaHotel>> getHotelSpecialPrice(@PathVariable("id") Integer id, @PathVariable("check_in") String check_in, @PathVariable("check_out") String check_out) throws ParseException
	{
		System.out.println("usao u metodu api/hotels/specialPrice/{id}, id: " + id);
		
		DateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		Date date_check_in = format.parse(check_in);
		Date date_check_out = format.parse(check_out);
		
		Hotel hotel = null;
		
		try{
			hotel = hotelService.findById(id).get();
			System.out.println("[HotelController: getHotelSpecialPrice]: Nasao hotel sa id:" + hotel.getId());
		}catch(NoSuchElementException e)
		{
			System.out.println("Ne postoji hotel sa id: " + id);
			return null; 	
		}
		
		Collection<RezervacijaHotel> brzeRez = rezervacijaHotelService.findBrzeRez(id, true);
		Collection<RezervacijaHotel> result = new ArrayList<RezervacijaHotel>();
		
		for(Iterator<RezervacijaHotel> iteratorRez = brzeRez.iterator(); iteratorRez.hasNext();) {
			RezervacijaHotel rezervacijaHotel = iteratorRez.next();
			
			if((date_check_in.equals(rezervacijaHotel.getDatum_dolaska()) || date_check_in.after(rezervacijaHotel.getDatum_dolaska())) && (date_check_out.equals(rezervacijaHotel.getDatum_odlaska()) || date_check_out.before(rezervacijaHotel.getDatum_odlaska())) ) {

				System.out.println("[HotelController: getHotelSpecialPrice]: soba sa id: " + rezervacijaHotel.getSoba().getId() + " je brza soba.");
				result.add(rezervacijaHotel);
			}else {
				System.out.println("[HotelController: isBrzaSoba]: soba nije na popustu u trazenom periodu.");
			}
		}
		
		/*
		List<Soba> result = new ArrayList<Soba>();
		
		for(Iterator<Soba> iteratorSoba = hotel.getSobe().iterator(); iteratorSoba.hasNext();) {
			System.out.println("[HotelController: getHotelSpecialPrice]: usao u for za prolazak kroz sobe; ima " + hotel.getSobe().size() + " soba.");
			Soba soba = (Soba) iteratorSoba.next();
			
			if(isBrzaSoba(soba, date_check_in, date_check_out)) {
				System.out.println("[HotelController: getHotelSpecialPrice]: soba sa id: " + soba.getId() + " je brza soba.");
				result.add(soba);
			}
		}
		
		
		return new ResponseEntity<Collection<RezervacijaHotel>>(result, HttpStatus.OK);
	}
	*/
	
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
	
	@RequestMapping(
			value = "/add_usluga/{usluga_id}",
			method = RequestMethod.POST,
			produces = MediaType.APPLICATION_JSON_VALUE,
			consumes = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public ResponseEntity<RezervacijaHotel> addUsluga(@PathVariable("usluga_id") Integer id, @RequestBody RezervacijaHotel rezervacijaHotela) throws java.sql.SQLIntegrityConstraintViolationException{
		
		Usluga usluga =  uslugaService.findById(id).get();
		rezervacijaHotela.getUsluge().add(usluga);
		
		return new ResponseEntity<RezervacijaHotel>(rezervacijaHotela, HttpStatus.OK);
	}
	
	@RequestMapping(
			value = "/remove_usluga/{usluga_id}",
			method = RequestMethod.POST,
			produces = MediaType.APPLICATION_JSON_VALUE,
			consumes = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public ResponseEntity<RezervacijaHotel> removeUsluga(@PathVariable("usluga_id") Integer id, @RequestBody RezervacijaHotel rezervacijaHotela){
		
		Usluga usluga =  uslugaService.findById(id).get();
		rezervacijaHotela.getUsluge().remove(usluga);
		
		return new ResponseEntity<RezervacijaHotel>(rezervacijaHotela, HttpStatus.OK);
	}
	
	@RequestMapping(
			value = "/get_number_of_days/{check_in}/{check_out}",
			method = RequestMethod.POST,
			produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Integer> getNumberOfDays(@PathVariable("check_in") String check_in, @PathVariable("check_out") String check_out) throws ParseException
	{
		System.out.println("[HotelController: getNumberOfDays]: usao u metodu getnumberOfDays");
		int result;
		
		if(check_in.equals("0001-01-01") || check_out.equals("0001-01-01")) {
			result = 0;
		}else {
			DateFormat format = new SimpleDateFormat("yyyy-MM-dd");
			Date date_check_in = format.parse(check_in);
			Date date_check_out = format.parse(check_out);
			result = (int) Math.round((date_check_out.getTime() - date_check_in.getTime()) / (double) 86400000) + 1;
		}
		System.out.println("[HotelController: getNumberOfDays]: numberOfDays: " + result);
		
		return new ResponseEntity<Integer>(result, HttpStatus.OK);
		
	}
	
	@RequestMapping(
			value = "/get_soba/{soba_id}",
			method = RequestMethod.POST,
			produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Soba> getSoba(@PathVariable("soba_id") Integer soba_id)
	{
		System.out.println("[HotelController: get_soba]: usao u metodu get_soba");
		Soba result = sobaService.findById(soba_id).get();
		
		
		return new ResponseEntity<Soba>(result, HttpStatus.OK);
		
	}
	
	@RequestMapping(
			value = "/book_room/{soba_id}",
			method = RequestMethod.PUT,
			produces = MediaType.APPLICATION_JSON_VALUE,
			consumes = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public ResponseEntity<Soba> bookRoom(@PathVariable("soba_id") Integer soba_id, @RequestBody RezervacijaHotel rezervacijaHotel){
		
		Soba soba =  sobaService.findById(soba_id).get();
		rezervacijaHotel.setSoba(soba);
		rezervacijaHotel.setAktivirana(true);
		
		Date date_check_in = rezervacijaHotel.getDatum_dolaska();
		Date date_check_out = rezervacijaHotel.getDatum_odlaska();
		int broj_dana = (int) Math.round((date_check_out.getTime() - date_check_in.getTime()) / (double) 86400000) + 1;
		
		double cena_rez = broj_dana * soba.getCena_nocenja();
		
		ArrayList<Usluga> usluge = new ArrayList<Usluga>();
		System.out.println("[HotelController: bookRoom]: U rezervaciji se nalaze sledece usluge: ");
		
		for(Iterator<Usluga> iteratorUsluga = rezervacijaHotel.getUsluge().iterator(); iteratorUsluga.hasNext();) {
			Usluga usluga = iteratorUsluga.next();
			cena_rez += broj_dana * usluga.getCena();
			System.out.println("usluga: " + usluga.getNaziv());
			usluge.add(usluga);
			iteratorUsluga.remove();
		}
		
		rezervacijaHotel.setUkupna_cena(cena_rez);
		rezervacijaHotel = rezervacijaHotelService.save(rezervacijaHotel);
		rezervacijaHotelService.insertRezervacijaSoba(rezervacijaHotel.getId(), soba.getId());
		
		for(int i=0; i<usluge.size(); i++) {
			Usluga usluga = usluge.get(i);
			System.out.println("[HotelController: bookRoom]: dodavanje usluge: " + usluga.getNaziv());
			try {
				rezervacijaHotelService.updateUsluga(usluga.getId(), rezervacijaHotel.getId());
				System.out.println("[HotelController: bookRoom]: usluga: " + usluga.getNaziv() + " dodata.");
			}catch(Exception e) {
				System.out.println("[HotelController: bookRoom]: exception kod dodavanja usluge: " + usluga.getNaziv());
			}
		}
		
		
		return new ResponseEntity<Soba>(soba, HttpStatus.OK);
	}
	
	@RequestMapping(
			value = "/book_room_special/{soba_id}/{check_in}/{check_out}",
			method = RequestMethod.PUT,
			produces = MediaType.APPLICATION_JSON_VALUE,
			consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Soba> bookRoomSpecial(@PathVariable("soba_id") Integer soba_id, @PathVariable("check_in") String check_in_string, @PathVariable("check_out") String check_out_string, @RequestBody Popust popust) throws ParseException {
		
		/*
		RezervacijaHotel rezervacijaHotel = rezervacijaHotelService.findById(rezervacija_id).get();
		
		rezervacijaHotelService.updateAktivirana(rezervacija_id, true);
		
		return new ResponseEntity<RezervacijaHotel>(rezervacijaHotel, HttpStatus.OK);
		*/
		
		Soba soba =  sobaService.findById(soba_id).get();
		
		System.out.println("[HotelController: bookRoomSpecial]: popust id: " + popust.getId());
		
		DateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		Date check_in = format.parse(check_in_string);
		Date check_out = format.parse(check_out_string);
		
		RezervacijaHotel rezervacijaHotel = new RezervacijaHotel();
		rezervacijaHotel.setDatum_dolaska(check_in);
		rezervacijaHotel.setDatum_odlaska(check_out);
		int broj_nocenja = (int) Math.round((check_out.getTime() - check_in.getTime()) / (double) 86400000) + 1;
		rezervacijaHotel.setBroj_nocenja(broj_nocenja);
		rezervacijaHotel.setBrza(true);
		rezervacijaHotel.setPopust(popust.getPopust());
		rezervacijaHotel.setAktivirana(true);
		rezervacijaHotel.setSoba(soba);
		double cena_rez = broj_nocenja * (soba.getCena_nocenja() * 0.01 * (100 - popust.getPopust()));
		
		System.out.println("[HotelController: bookRoomSpecial]: U popustu se nalaze sledece usluge: ");
		for(Iterator<Usluga> iteratorUsluga = popust.getUsluge().iterator(); iteratorUsluga.hasNext();) {
			Usluga usluga = iteratorUsluga.next();
			cena_rez += broj_nocenja * usluga.getCena();
			System.out.println("usluga: " + usluga.getNaziv());
		}
		
		rezervacijaHotel.setUkupna_cena(cena_rez);
		rezervacijaHotel = rezervacijaHotelService.save(rezervacijaHotel);
		rezervacijaHotelService.insertRezervacijaSoba(rezervacijaHotel.getId(), soba.getId());
		
		for(Iterator<Usluga> iteratorUsluga = popust.getUsluge().iterator(); iteratorUsluga.hasNext();) {
			Usluga usluga = iteratorUsluga.next();
			System.out.println("[HotelController: bookRoomSpecial]: dodavanje usluge: " + usluga.getNaziv());
			try {
				rezervacijaHotelService.updateUsluga(usluga.getId(), rezervacijaHotel.getId());
				System.out.println("[HotelController: bookRoomSpecial]: usluga: " + usluga.getNaziv() + " dodata.");
			}catch(Exception e) {
				System.out.println("[HotelController: bookRoomSpecial]: exception kod dodavanja usluge: " + usluga.getNaziv());
			}
		}
		
		return new ResponseEntity<Soba>(soba, HttpStatus.OK);
	}
	
	@RequestMapping(
			value = "/popust/{soba_id}/{check_in}/{check_out}",
			method = RequestMethod.GET,
			produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Popust> getPopust(@PathVariable("soba_id") Integer soba_id, @PathVariable("check_in") String check_in_string, @PathVariable("check_out") String check_out_string) throws ParseException {
		System.out.println("[HotelController: getPopust]: usao u metodu getPopust");
		
		Soba soba =  sobaService.findById(soba_id).get();
		Popust result = null;
		
		DateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		Date check_in = format.parse(check_in_string);
		Date check_out = format.parse(check_out_string);
		
		for(Iterator<Popust> iteratorPopusta = soba.getPopusti().iterator(); iteratorPopusta.hasNext();) {
			Popust popust = iteratorPopusta.next();
			if((check_in.equals(popust.getPocetak_vazenja()) || check_in.after(popust.getPocetak_vazenja())) && (check_out.equals(popust.getKraj_vazenja()) || check_out.before(popust.getKraj_vazenja()))) {
			//if(check_in.equals(popust.getPocetak_vazenja()) || check_in.equals(popust.getKraj_vazenja()) || check_out.equals(popust.getPocetak_vazenja()) || ((popust.getPocetak_vazenja()).after(check_in) && (popust.getPocetak_vazenja()).before(check_out)) || (check_in.after(popust.getPocetak_vazenja()) && check_in.before(popust.getKraj_vazenja())) || (check_out.after(popust.getPocetak_vazenja()) && check_out.before(popust.getKraj_vazenja()))) {
				result = popust;
				System.out.println("[HotelController: getPopust]: soba je na popustu u trazenom periodu, popust id: " + popust.getId());
				break;
			}else {
				System.out.println("[HotelController: isBrzaSoba]: soba nije na popustu u trazenom periodu.");
			}
		}

		
		return new ResponseEntity<Popust>(result, HttpStatus.OK);
		
	}
	
	public List<Hotel> pretraga(List<Hotel> hoteli, String[] search, Date check_in, Date check_out, int adults){
		List<Hotel> result = new ArrayList<Hotel>();
		
		for(int i=0; i<hoteli.size(); i++) {
			result.add(hoteli.get(i));
		}
		
		for(int j=0; j<hoteli.size(); j++) {
			System.out.println("[HotelController: pretraga]: naziv hotela: " + hoteli.get(j).getNaziv() + ", adresa hotela: " + hoteli.get(j).getAdresa().getGrad().getNaziv() + " " + hoteli.get(j).getAdresa().getUlica() + " " + hoteli.get(j).getAdresa().getBroj());
			for(int i=0; i<search.length; i++) {
				if(!(hoteli.get(j).getAdresa().getGrad().getNaziv().toLowerCase().contains(search[i]) || hoteli.get(j).getAdresa().getUlica().toLowerCase().contains(search[i]) || hoteli.get(j).getAdresa().getBroj().toLowerCase().contains(search[i]) || hoteli.get(j).getNaziv().toLowerCase().contains(search[i]))) {
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
			if(hotel.getSobe().size() != 0) {
				for(Iterator<Soba> iteratorSoba = hotel.getSobe().iterator(); iteratorSoba.hasNext();) {
					System.out.println("[HotelController: pretraga]: usao u for za prolazak kroz sobe; ima " + hotel.getSobe().size() + " soba.");
					Soba soba = (Soba) iteratorSoba.next();
					postojiTerminSoba = true;
					/*
					if(isBrzaSoba(soba, check_in, check_out)) {
						System.out.println("[HotelController: pretraga]: soba sa id: " + soba.getId() + " je brza soba.");
						iteratorSoba.remove();
					}else {*/
						if(soba.getRezervacije().isEmpty()) {
							System.out.println("[HotelController: pretraga]: soba nema rezervacija.");
							postojiTerminSoba = true;
							postojiSoba = true;
							//break;
						}else {
							System.out.println("[HotelController: pretraga]: postoje rezervacije za sobu; ima ih " + soba.getRezervacije().size() + ".");
							for(Iterator<RezervacijaHotel> iteratorRezervacija = soba.getRezervacije().iterator(); iteratorRezervacija.hasNext();) {
								RezervacijaHotel rezervacijaHotel = iteratorRezervacija.next();
								System.out.println("[HotelController: pretraga]: zahtev:  datum dolaska: " + check_in + ", datum odlaska: " + check_out + ".");
								System.out.println("[HotelController: pretraga]: rezervacija: " + rezervacijaHotel.getId() + ", datum dolaska: " + rezervacijaHotel.getDatum_dolaska() + ", datum odlaska: " + rezervacijaHotel.getDatum_odlaska() + ".");

								if(rezervacijaHotel.isAktivirana() && (check_in.equals(rezervacijaHotel.getDatum_dolaska()) || check_in.equals(rezervacijaHotel.getDatum_odlaska()) || check_out.equals(rezervacijaHotel.getDatum_dolaska()) || ((rezervacijaHotel.getDatum_dolaska()).after(check_in) && (rezervacijaHotel.getDatum_dolaska()).before(check_out)) || (check_in.after(rezervacijaHotel.getDatum_dolaska()) && check_in.before(rezervacijaHotel.getDatum_odlaska())) || (check_out.after(rezervacijaHotel.getDatum_dolaska()) && check_out.before(rezervacijaHotel.getDatum_odlaska())))) {
									System.out.println("[HotelController: pretraga]: soba je zauzeta u trazenom periodu.");
									iteratorSoba.remove();
									postojiTerminSoba = false;
								}else {
									System.out.println("[HotelController: pretraga]: soba je slobodna u trazenom periodu.");
								}
							}
							
							if(postojiTerminSoba) {
								postojiSoba = true;
							}
						}
					//}
					
				}
				
				if(!postojiSoba) {
					result.remove(hotel);
					i--;
				}
			}
			
		}
		
		
		if(adults != 0) {
			boolean postoji;
			
			for(int i=0; i<result.size(); i++) {
				postoji = false;
				Hotel hotel = result.get(i);
				System.out.println("[HotelController: pretraga]: hotel " + hotel.getNaziv() + " ima " + hotel.getSobe().size() + " soba.");
				for(Iterator<Soba> iteratorSoba = hotel.getSobe().iterator(); iteratorSoba.hasNext();) {
					Soba soba = (Soba) iteratorSoba.next();
					if(soba.getBroj_kreveta() <= adults) {
						postoji = true;
						System.out.println("[HotelController: pretraga]: soba.getBroj_kreveta() <= adults; soba.getBroj_kreveta(): " + soba.getBroj_kreveta() + ".");
						//break;
					}else {
						iteratorSoba.remove();
						System.out.println("[HotelController: pretraga]: uklanjanje sobe.");
						System.out.println("[HotelController: pretraga]: soba.getBroj_kreveta() > adults; soba.getBroj_kreveta(): " + soba.getBroj_kreveta() + ".");
					}
				}
				if(!postoji) {
					result.remove(hotel);
					i--;
				}
			}
		}
		
		return result;
	}
	
	public Hotel pretraga_hotel(Hotel hotel, Date check_in, Date check_out, int adults){
		Hotel result = hotel;
		
		System.out.println("[HotelController: pretraga_hotel]: iteracija pomocu iteratora; naziv hotela: " + hotel.getNaziv());
		
		for(Iterator<Soba> iteratorSoba = hotel.getSobe().iterator(); iteratorSoba.hasNext();) {
			System.out.println("[HotelController: pretraga_hotel]: usao u for za prolazak kroz sobe; ima " + hotel.getSobe().size() + " soba.");
			Soba soba = (Soba) iteratorSoba.next();
			
			if(isBrzaSoba(soba, check_in, check_out)) {
				System.out.println("[HotelController: pretraga_hotel]: soba sa id: " + soba.getId() + " je brza soba.");
				iteratorSoba.remove();
			}else {
				if(soba.getRezervacije().isEmpty()) {
					System.out.println("[HotelController: pretraga_hotel]: soba nema rezervacija.");
				}else {
					System.out.println("[HotelController: pretraga_hotel]: postoje rezervacije za sobu; ima ih " + soba.getRezervacije().size() + ".");
					for(Iterator<RezervacijaHotel> iteratorRezervacija = soba.getRezervacije().iterator(); iteratorRezervacija.hasNext();) {
						RezervacijaHotel rezervacijaHotel = iteratorRezervacija.next();
						System.out.println("[HotelController: pretraga_hotel]: zahtev:  datum dolaska: " + check_in + ", datum odlaska: " + check_out + ".");
						System.out.println("[HotelController: pretraga_hotel]: rezervacija: " + rezervacijaHotel.getId() + ", datum dolaska: " + rezervacijaHotel.getDatum_dolaska() + ", datum odlaska: " + rezervacijaHotel.getDatum_odlaska() + ".");

						if(rezervacijaHotel.isAktivirana() && (check_in.equals(rezervacijaHotel.getDatum_dolaska()) || check_in.equals(rezervacijaHotel.getDatum_odlaska()) || check_out.equals(rezervacijaHotel.getDatum_dolaska()) || ((rezervacijaHotel.getDatum_dolaska()).after(check_in) && (rezervacijaHotel.getDatum_dolaska()).before(check_out)) || (check_in.after(rezervacijaHotel.getDatum_dolaska()) && check_in.before(rezervacijaHotel.getDatum_odlaska())) || (check_out.after(rezervacijaHotel.getDatum_dolaska()) && check_out.before(rezervacijaHotel.getDatum_odlaska())))) {
							System.out.println("[HotelController: pretraga_hotel]: soba je zauzeta u trazenom periodu.");
							iteratorSoba.remove();
						}else {
							System.out.println("[HotelController: pretraga_hotel]: soba je slobodna u trazenom periodu.");
						}
					}
				}
			}
		}
		
		if(adults != 0) {
			System.out.println("[HotelController: pretraga_hotel]: hotel " + hotel.getNaziv() + " ima " + hotel.getSobe().size() + " soba.");
			
			for(Iterator<Soba> iteratorSoba = hotel.getSobe().iterator(); iteratorSoba.hasNext();) {
				Soba soba = (Soba) iteratorSoba.next();
				if(soba.getBroj_kreveta() <= adults) {
					System.out.println("[HotelController: pretraga_hotel]: soba.getBroj_kreveta() <= adults; soba.getBroj_kreveta(): " + soba.getBroj_kreveta() + ".");
				}else {
					iteratorSoba.remove();
					System.out.println("[HotelController: pretraga_hotel]: uklanjanje sobe.");
					System.out.println("[HotelController: pretraga_hotel]: soba.getBroj_kreveta() > adults; soba.getBroj_kreveta(): " + soba.getBroj_kreveta() + ".");
				}
			}
		}
		
		return result;
	}
	
	public boolean isBrzaSoba(Soba soba, Date check_in, Date check_out) {
		
		boolean result = false;
		
		System.out.println("[HotelController: isBrzaSoba]: postoje popusti za sobu; ima ih " + soba.getPopusti().size() + ".");
		for(Iterator<Popust> iteratorPopusta = soba.getPopusti().iterator(); iteratorPopusta.hasNext();) {
			Popust popust = iteratorPopusta.next();

			if(check_in.equals(popust.getPocetak_vazenja()) || check_in.equals(popust.getKraj_vazenja()) || check_out.equals(popust.getPocetak_vazenja()) || ((popust.getPocetak_vazenja()).after(check_in) && (popust.getPocetak_vazenja()).before(check_out)) || (check_in.after(popust.getPocetak_vazenja()) && check_in.before(popust.getKraj_vazenja())) || (check_out.after(popust.getPocetak_vazenja()) && check_out.before(popust.getKraj_vazenja()))) {

				System.out.println("[HotelController: isBrzaSoba]: soba je na popustu u trazenom periodu.");
				//soba.setCena_popust(soba.getCena_nocenja() * 0.01 * (100 - popust.getPopust()));
				result = true;
				break;
			}else {
				System.out.println("[HotelController: isBrzaSoba]: soba nije na popustu u trazenom periodu.");
			}
		}
		
		return result;
	}
}
