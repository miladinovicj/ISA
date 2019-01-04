package com.example.ISA_AMA_projekat.controller;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

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
	public ResponseEntity<List<Hotel>> getHotelsSearch(HttpServletRequest request, @PathVariable("name_location") String name_location, @PathVariable("check_in") String check_in, @PathVariable("check_out") String check_out, @PathVariable("adults") int adults) throws ParseException{
		
		System.out.println("[HotelController: getHotelsSearch]: name_location-" + name_location + ", check_in-" + check_in + ", check_out-" + check_out + ", adults-" + adults);
		name_location = name_location.toLowerCase();
		String[] search = name_location.split(" ");
		
		DateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		Date date_check_in = format.parse(check_in);
		Date date_check_out = format.parse(check_out);
		
		RezervacijaHotel rezervacijaHotel = new RezervacijaHotel();
		rezervacijaHotel.setDatum_dolaska(date_check_in);
		rezervacijaHotel.setDatum_odlaska(date_check_out);
		rezervacijaHotel.setBrza(false);
		int broj_nocenja = (int) Math.round((date_check_out.getTime() - date_check_in.getTime()) / (double) 86400000) + 1;
		System.out.println("[HotelController: getHotelsSearch]: broj_nocenja-" + broj_nocenja);
		rezervacijaHotel.setBroj_nocenja(broj_nocenja);
		rezervacijaHotel.setAktivirana(false);
		
		rezervacijaHotel = rezervacijaHotelService.save(rezervacijaHotel);
		request.getSession().setAttribute("rezervacijaHotel", rezervacijaHotel);
		
		List<Hotel> hotels = hotelService.search("%" + search[0] + "%");
		
		List<Hotel> hoteli = pretraga(hotels, search, date_check_in, date_check_out, adults);
		
		request.getSession().setAttribute("hoteliPretraga", hoteli);
			
		return new ResponseEntity<List<Hotel>>(hoteli, HttpStatus.OK);
	}
	
	@RequestMapping(
			value = "/{id}",
			method = RequestMethod.GET,
			produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Hotel> getHotel(HttpServletRequest request, @PathVariable("id") Integer id)
	{
		System.out.println("usao u metodu api/hotels/{id}");
		/*try
		{
			Hotel found = hotelService.findById(id).get();
			System.out.println("Nasao hotel sa id:" + found.getId());
			return new ResponseEntity<Hotel>(found, HttpStatus.OK);
		}
		catch(NoSuchElementException e)
		{
			System.out.println("Ne postoji hotel sa id: " + id);
			return null; 	
		}*/
		
		List<Hotel> hoteliPretraga = (List<Hotel>) request.getSession().getAttribute("hoteliPretraga");
		Hotel result = null;
		
		if(hoteliPretraga != null) {
			for(int i=0; i<hoteliPretraga.size(); i++) {
				if(hoteliPretraga.get(i).getId() == id) {
					result = hoteliPretraga.get(i);
				}
			}
		}else {
			result = hotelService.findById(id).get();
		}
		
		return new ResponseEntity<Hotel>(result, HttpStatus.OK);
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
	
	@RequestMapping(
			value = "/add_usluga/{usluga_id}",
			method = RequestMethod.PUT,
			produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Usluga> addUsluga(@PathVariable("usluga_id") Integer id, HttpServletRequest request) throws java.sql.SQLIntegrityConstraintViolationException{
		
		RezervacijaHotel rezervacijaHotel = (RezervacijaHotel) request.getSession().getAttribute("rezervacijaHotel");
		Usluga usluga =  uslugaService.findById(id).get();
		rezervacijaHotel.getUsluge().add(usluga);
		request.getSession().setAttribute("rezervacijaHotel", rezervacijaHotel);
		
		try {
			rezervacijaHotelService.updateUsluga(usluga.getId(), rezervacijaHotel.getId());
		}catch(Exception e) {
			System.out.println("[HotelController: addUsluga]: usluga sa id: " + usluga.getId() + "je vec dodata");
		}
		
		return new ResponseEntity<Usluga>(usluga, HttpStatus.OK);
	}
	
	@RequestMapping(
			value = "/remove_usluga/{usluga_id}",
			method = RequestMethod.DELETE,
			produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Usluga> removeUsluga(@PathVariable("usluga_id") Integer id, HttpServletRequest request){
		
		RezervacijaHotel rezervacijaHotel = (RezervacijaHotel) request.getSession().getAttribute("rezervacijaHotel");
		Usluga usluga =  uslugaService.findById(id).get();
		rezervacijaHotel.getUsluge().remove(usluga);
		request.getSession().setAttribute("rezervacijaHotel", rezervacijaHotel);
		
		rezervacijaHotelService.removeUsluga(usluga.getId(), rezervacijaHotel.getId());
		
		return new ResponseEntity<Usluga>(usluga, HttpStatus.OK);
	}
	
	@RequestMapping(
			value = "/get_number_of_days",
			method = RequestMethod.GET,
			produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Integer> getNumberOfDays(HttpServletRequest request)
	{
		System.out.println("[HotelController: getNumberOfDays]: usao u metodu getnumberOfDays");
		RezervacijaHotel rezervacijaHotel = (RezervacijaHotel) request.getSession().getAttribute("rezervacijaHotel");
		int result;
		
		if(rezervacijaHotel != null) {
			Date date_check_in = rezervacijaHotel.getDatum_dolaska();
			Date date_check_out = rezervacijaHotel.getDatum_odlaska();
			result = (int) Math.round((date_check_out.getTime() - date_check_in.getTime()) / (double) 86400000) + 1;
		}else {
			result = 0;
		}
		
		System.out.println("[HotelController: getNumberOfDays]: numberOfDays: " + result);
		
		return new ResponseEntity<Integer>(result, HttpStatus.OK);
		
	}
	
	@RequestMapping(
			value = "/book_room/{soba_id}",
			method = RequestMethod.PUT,
			produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Soba> bookRoom(@PathVariable("soba_id") Integer soba_id, HttpServletRequest request){
		
		RezervacijaHotel rezervacijaHotel = (RezervacijaHotel) request.getSession().getAttribute("rezervacijaHotel");
		Soba soba =  sobaService.findById(soba_id).get();
		rezervacijaHotel.setSoba(soba);
		rezervacijaHotel.setAktivirana(true);
		
		Date date_check_in = rezervacijaHotel.getDatum_dolaska();
		Date date_check_out = rezervacijaHotel.getDatum_odlaska();
		int broj_dana = (int) Math.round((date_check_out.getTime() - date_check_in.getTime()) / (double) 86400000) + 1;
		
		double cena_rez = broj_dana * soba.getCena_nocenja();
		for(Iterator<Usluga> iteratorUsluga = rezervacijaHotel.getUsluge().iterator(); iteratorUsluga.hasNext();) {
			cena_rez += broj_dana * iteratorUsluga.next().getCena();
		}
		
		rezervacijaHotel.setUkupna_cena(cena_rez);
		request.getSession().setAttribute("rezervacijaHotel", rezervacijaHotel);
		
		rezervacijaHotelService.updateSoba(soba.getId(), true, cena_rez, rezervacijaHotel.getId());
		
		request.getSession().removeAttribute("rezervacijaHotel");
		
		return new ResponseEntity<Soba>(soba, HttpStatus.OK);
	}
	
	public List<Hotel> pretraga(List<Hotel> hoteli, String[] search, Date check_in, Date check_out, int adults){
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
				
				if(isBrzaSoba(soba, check_in, check_out)) {
					System.out.println("[HotelController: pretraga]: soba sa id: " + soba.getId() + " je brza soba.");
					iteratorSoba.remove();
					//TODO ovu sobu ubaciti u neku listu brzih soba pa nju prikazivati u onom posebnom linku
				}else {
					if(soba.getRezervacije().isEmpty()) {
						System.out.println("[HotelController: pretraga]: soba nema rezervacija.");
						postojiTerminSoba = true;
						postojiSoba = true;
						//break;
					}else {
						for(Iterator<RezervacijaHotel> iteratorRezervacija = soba.getRezervacije().iterator(); iteratorRezervacija.hasNext();) {
							System.out.println("[HotelController: pretraga]: postoje rezervacije za sobu; ima ih " + soba.getRezervacije().size() + ".");
							RezervacijaHotel rezervacijaHotel = iteratorRezervacija.next();
							System.out.println("[HotelController: pretraga]: zahtev:  datum dolaska: " + check_in + ", datum odlaska: " + check_out + ".");
							System.out.println("[HotelController: pretraga]: rezervacija: " + rezervacijaHotel.getId() + ", datum dolaska: " + rezervacijaHotel.getDatum_dolaska() + ", datum odlaska: " + rezervacijaHotel.getDatum_odlaska() + ".");
							System.out.println("[HotelController: pretraga]: " + check_in.equals(rezervacijaHotel.getDatum_dolaska()));
							System.out.println("[HotelController: pretraga]: " + check_in.after(rezervacijaHotel.getDatum_dolaska()));
							System.out.println("[HotelController: pretraga]: " + check_in.before(rezervacijaHotel.getDatum_odlaska()));

							if(rezervacijaHotel.isAktivirana() && (check_in.equals(rezervacijaHotel.getDatum_dolaska()) || check_in.equals(rezervacijaHotel.getDatum_odlaska()) || check_out.equals(rezervacijaHotel.getDatum_dolaska()) || ((rezervacijaHotel.getDatum_dolaska()).after(check_in) && (rezervacijaHotel.getDatum_dolaska()).before(check_out)) || (check_in.after(rezervacijaHotel.getDatum_dolaska()) && check_in.before(rezervacijaHotel.getDatum_odlaska())) || (check_out.after(rezervacijaHotel.getDatum_dolaska()) && check_out.before(rezervacijaHotel.getDatum_odlaska())))) {

								System.out.println("[HotelController: pretraga]: soba je zauzeta u trazenom periodu.");
								hotel.getSobe().remove(soba);
								postojiTerminSoba = false;
								//break;
							}else {
								System.out.println("[HotelController: pretraga]: soba je slobodna u trazenom periodu.");
							}
						}
						
						if(postojiTerminSoba) {
							postojiSoba = true;
							//break;
						}
					}
				}
				
			}
			
			if(!postojiSoba) {
				result.remove(hotel);
				i--;
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
						System.out.println("[HotelController: pretraga]: iteratorSoba.next() nije null.");
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
	
	public boolean isBrzaSoba(Soba soba, Date check_in, Date check_out) {
		
		boolean result = false;
		
		System.out.println("[HotelController: isBrzaSoba]: postoje popusti za sobu; ima ih " + soba.getPopusti().size() + ".");
		for(Iterator<Popust> iteratorPopusta = soba.getPopusti().iterator(); iteratorPopusta.hasNext();) {
			Popust popust = iteratorPopusta.next();

			if(check_in.equals(popust.getPocetak_vazenja()) || check_in.equals(popust.getKraj_vazenja()) || check_out.equals(popust.getPocetak_vazenja()) || ((popust.getPocetak_vazenja()).after(check_in) && (popust.getPocetak_vazenja()).before(check_out)) || (check_in.after(popust.getPocetak_vazenja()) && check_in.before(popust.getKraj_vazenja())) || (check_out.after(popust.getPocetak_vazenja()) && check_out.before(popust.getKraj_vazenja()))) {

				System.out.println("[HotelController: isBrzaSoba]: soba je na popustu u trazenom periodu.");
				result = true;
				break;
			}else {
				System.out.println("[HotelController: isBrzaSoba]: soba nije na popustu u trazenom periodu.");
			}
		}
		
		return result;
	}
}
