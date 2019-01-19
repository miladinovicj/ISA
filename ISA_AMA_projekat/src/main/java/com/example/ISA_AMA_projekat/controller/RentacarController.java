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

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

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

import com.example.ISA_AMA_projekat.model.Filijala;
import com.example.ISA_AMA_projekat.model.RentacarServis;
import com.example.ISA_AMA_projekat.model.RezervacijaVozila;
import com.example.ISA_AMA_projekat.model.Vozilo;
import com.example.ISA_AMA_projekat.service.RentacarService;
import com.example.ISA_AMA_projekat.service.RezervacijaVozilaService;
import com.example.ISA_AMA_projekat.service.VoziloService;

@RestController
@RequestMapping(value="api/rents")
public class RentacarController {

	@Autowired
	private RentacarService rentService;
	
	@Autowired
	private VoziloService voziloService;
	
	@Autowired
	private RezervacijaVozilaService rezervacijaVozilaService;
	
	@RequestMapping(
			value = "/all",
			method = RequestMethod.GET,
			produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Collection<RentacarServis>> getRentacarServis(){
		
		Collection<RentacarServis> rents = rentService.findAll();
		for (RentacarServis rs : rents)
		{
			System.out.println("IMA LI FILIJALA: " + rs.getFilijale().size());
		}
		return new ResponseEntity<Collection<RentacarServis>>(rents, HttpStatus.OK);
	}
	
	@RequestMapping(
			value = "/{id}",
			method = RequestMethod.GET,
			produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<RentacarServis> getRentacarServis(@PathVariable("id") Long id)
	{
		try
		{
			RentacarServis found = rentService.findById(id).get();
			return new ResponseEntity<RentacarServis>(found, HttpStatus.OK);
		}
		catch(NoSuchElementException e)
		{
			System.out.println("Ne postoji servis sa id: " + id);
			return null; 	
		}
	}
	
	@RequestMapping(
			value = "/save",
			method = RequestMethod.POST,
			consumes=MediaType.APPLICATION_JSON_VALUE,
			produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<RentacarServis> saveRentacarServis(@RequestBody RentacarServis rentacarServis) throws Exception{
		
		RentacarServis postoji = rentService.findByNaziv(rentacarServis.getNaziv());
		
		if(postoji != null) {
			System.out.println("RentacarServis sa ovim nazivom vec postoji u bazi");
			return new ResponseEntity<RentacarServis>(postoji, HttpStatus.CONFLICT);
		}else {
			RentacarServis saved = rentService.save(rentacarServis);
			return new ResponseEntity<RentacarServis>(saved, HttpStatus.CREATED);
		}
	}
	
	@RequestMapping(
			value = "/search/{name_location}/{check_in}/{check_out}/{check_in_town}/{check_out_town}/{passengers}",
			method = RequestMethod.GET,
			produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<RentacarServis>> getRentacarSearch(HttpSession session, HttpServletRequest request, @PathVariable("name_location") String name_location, @PathVariable("check_in") String check_in, @PathVariable("check_out") String check_out, @PathVariable("check_in_town") String check_in_town, @PathVariable("check_out_town") String check_out_town, @PathVariable("passengers") int passengers) throws ParseException{
		
		System.out.println("[RentacarController: getRentacarSearch]: name_location-" + name_location + ", check_in-" + check_in + ", check_out-" + check_out + ", in_town-" + check_in_town + ", out_town-" + check_out_town + ", passengers-" + passengers);
		name_location = name_location.toLowerCase();
		String[] search = name_location.split(" ");
		
		DateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		Date date_check_in = format.parse(check_in);
		Date date_check_out = format.parse(check_out);
		
		List<RentacarServis> rents = rentService.search("%" + search[0] + "%");
		System.out.println("Search 0: " +search[0]);
		System.out.println("RENTS: " + rents.size());
		
		for(int i=0; i<rents.size(); i++) {
			System.out.println("[RentacarController: getRentacarSearch]: rentacar name: " + rents.get(i).getNaziv() + ", rentacar address: " + rents.get(i).getAdresa());
		}
		
		
		List<RentacarServis> servisi = pretragaServisa(rents, search, date_check_in, date_check_out, check_in_town, check_out_town, passengers);
			System.out.println("SERVISI VRACENI: " + servisi.size());
			
			request.getSession().setAttribute("servisiPretraga", servisi);
		return new ResponseEntity<List<RentacarServis>>(servisi, HttpStatus.OK);
		
	}
	
	public List<RentacarServis> pretragaServisa(List<RentacarServis> rents, String[] search, Date check_in, Date check_out, String check_in_town, String check_out_town, int passengers){
		check_in_town=check_in_town.toLowerCase();
		check_out_town=check_out_town.toLowerCase();
		
		List<RentacarServis> result = new ArrayList<RentacarServis>();
		
		for(int i=0; i<rents.size(); i++) {
			result.add(rents.get(i));
		}
		
		for(int j=0; j<rents.size(); j++) {
			System.out.println("[RentacarController: pretraga]: naziv servisa: " + rents.get(j).getNaziv() + ", adresa servisa: " + rents.get(j).getAdresa());
			for(int i=0; i<search.length; i++) {
				if(!(rents.get(j).getAdresa().getGrad().getNaziv().toLowerCase().contains(search[i]) || rents.get(j).getAdresa().getUlica().toLowerCase().contains(search[i]) || rents.get(j).getAdresa().getBroj().toLowerCase().contains(search[i]) || rents.get(j).getNaziv().toLowerCase().contains(search[i]))) {
					result.remove(rents.get(j));
					System.out.println("[RentacarController: pretraga]: obrisan rentacar sa nazivom - " + rents.get(j).getNaziv());
				}
			}
		}
		boolean adresaPreuzimanja = false;
		boolean adresaVracanja = false;
		for (int i=0; i<result.size(); i++) {
			RentacarServis rentacar = result.get(i);
			for (Iterator<Filijala> iteratorFilijala = rentacar.getFilijale().iterator(); iteratorFilijala.hasNext();)
			{
				Filijala filijala = (Filijala) iteratorFilijala.next();
				System.out.println("[RentacarController: pretraga] MESTO FILIJALE: " + filijala.getAdresa().getGrad().getNaziv());
				if(filijala.getAdresa().getGrad().getNaziv().toLowerCase().equals(check_in_town) ||
						rentacar.getAdresa().getGrad().getNaziv().toLowerCase().equals(check_in_town)  )
				{
					adresaPreuzimanja=true;
					break;
				}
			}
		}
		
		for (int i=0; i<result.size(); i++) {
			RentacarServis rentacar = result.get(i);
			for (Iterator<Filijala> iteratorFilijala = rentacar.getFilijale().iterator(); iteratorFilijala.hasNext();)
			{
				Filijala filijala = (Filijala) iteratorFilijala.next();
				System.out.println("[RentacarController: pretraga] MESTO FILIJALE: " + filijala.getAdresa().getGrad().getNaziv());
				if(filijala.getAdresa().getGrad().getNaziv().toLowerCase().equals(check_out_town) ||
						rentacar.getAdresa().getGrad().getNaziv().toLowerCase().equals(check_out_town) )
				{
					adresaVracanja=true;
					break;
				}
			}
		}
		
		System.out.println("[RentacarController: pretraga] mesto preuzimanja: " + adresaPreuzimanja + " mesto vracanja: " + adresaVracanja);
		boolean postojiTerminVozilo;
		boolean postojiVozilo;
		
		
	 if(adresaPreuzimanja==true && adresaVracanja== true)	{
		for (int i=0; i<result.size(); i++) {
			RentacarServis rentacar = result.get(i);
			postojiVozilo = false;
			System.out.println("[RentacarController: pretraga]: iteracija pomocu iteratora; naziv rentacara: " + rentacar.getNaziv());
		for (Iterator<Filijala> iteratorFilijala = rentacar.getFilijale().iterator(); iteratorFilijala.hasNext();)
		{
				Filijala filijala = (Filijala) iteratorFilijala.next();
			
					for(Iterator<Vozilo> iteratorVozilo = filijala.getVozila().iterator(); iteratorVozilo.hasNext();) {
						System.out.println("[RentacarController: pretraga]: usao u for za prolazak kroz vozila; ima " + filijala.getVozila().size() + " vozila u filijali: " + filijala.getAdresa());
						Vozilo vozilo = (Vozilo) iteratorVozilo.next();
						postojiTerminVozilo = true;
						
						if(vozilo.getRezervacije().isEmpty()) {
							System.out.println("[RentacarController: pretraga]: vozilo nema rezervacija.");
							postojiTerminVozilo = true;
							postojiVozilo = true;
							//break;
						}else {
							for(Iterator<RezervacijaVozila> iteratorRezervacija = vozilo.getRezervacije().iterator(); iteratorRezervacija.hasNext();) {
								System.out.println("[HotelController: pretraga]: postoje rezervacije za sobu; ima ih " + vozilo.getRezervacije().size() + ".");
								RezervacijaVozila rezervacijaVozilo = iteratorRezervacija.next();
								System.out.println("[RentacarController: pretraga]: zahtev:  datum preuzimanja: " + check_in + ", datum vracanja: " + check_out + ".");
								System.out.println("[RentacarController: pretraga]: rezervacija: " + rezervacijaVozilo.getId() + ", datum preuzimanja: " + rezervacijaVozilo.getDatum_preuzimanja() + ", datum vracanja: " + rezervacijaVozilo.getDatum_vracanja() + ".");
								System.out.println("[RentacarController: pretraga]: " + check_in.equals(rezervacijaVozilo.getDatum_preuzimanja()));
								System.out.println("[RentacarController: pretraga]: " + check_in.after(rezervacijaVozilo.getDatum_vracanja()));
								System.out.println("[RentacarController: pretraga]: " + check_in.before(rezervacijaVozilo.getDatum_vracanja()));
								if(rezervacijaVozilo.isAktivirana() && (check_in.equals(rezervacijaVozilo.getDatum_preuzimanja()) || check_in.equals(rezervacijaVozilo.getDatum_vracanja())
										|| check_out.equals(rezervacijaVozilo.getDatum_preuzimanja()) || 
										((rezervacijaVozilo.getDatum_preuzimanja()).after(check_in) && (rezervacijaVozilo.getDatum_preuzimanja()).before(check_out)) || 
										(check_in.after(rezervacijaVozilo.getDatum_preuzimanja()) && check_in.before(rezervacijaVozilo.getDatum_vracanja())) || 
										(check_out.after(rezervacijaVozilo.getDatum_preuzimanja()) && check_out.before(rezervacijaVozilo.getDatum_vracanja())))) {
									System.out.println("[RentacarController: pretraga]: vozilo je zauzeto u trazenom periodu.");
									iteratorVozilo.remove();
									postojiTerminVozilo = false;
									//break;
								}else {
									System.out.println("[RentacarController: pretraga]: vozilo je slobodno u trazenom periodu.");
								}
							}
							
							if(postojiTerminVozilo) {
								postojiVozilo = true;
								//break;
							}
						}
					
			}
			
		}
			
			if(!postojiVozilo) {
				result.remove(rentacar);
				i--;
			}
		}
		
		if(passengers != 0) {
			boolean postoji;
			
			for(int i=0; i<result.size(); i++) {
				postoji = false;
				RentacarServis rent = result.get(i);
				for (Iterator<Filijala> iteratorFilijala = rent.getFilijale().iterator(); iteratorFilijala.hasNext();)
				{
						Filijala filijala = (Filijala) iteratorFilijala.next();
						System.out.println("[RentacarController: pretraga] MESTO FILIJALE: " + filijala.getAdresa().getGrad().getNaziv());
					if(filijala.getAdresa().getGrad().getNaziv().toLowerCase().equals(check_in_town) || filijala.getAdresa().getGrad().getNaziv().toLowerCase().equals(check_out_town))
					{
				for(Iterator<Vozilo> iteratorVozilo = filijala.getVozila().iterator(); iteratorVozilo.hasNext();) {
					Vozilo vozilo = (Vozilo) iteratorVozilo.next();
						
						if(vozilo.getBroj_sedista() >= passengers) {
							postoji = true;
							System.out.println("[RentacarController: pretraga]:vozilo.getBroj_sedista() <= passengers ; vozilo.getBroj_sedista(): " + vozilo.getBroj_sedista() + ".");
							//break;
						}else {
							iteratorVozilo.remove();
							System.out.println("[RentacarController: pretraga]: uklanjanje vozila.");
							System.out.println("[RentacarController: pretraga]:vozilo.getBroj_sedista() < passengers ; vozilo.getBroj_sedista(): " + vozilo.getBroj_sedista() + ".");
						}
				}
					}
				}
				if(!postoji) {
					result.remove(rent);
					i--;
				}
			}
		}
	}
	 else
	 {
		result.clear(); 
	 }
		
		
		return result;
	}
	
	@RequestMapping(
			value = "/{id}/{check_in}/{check_out}",
			method = RequestMethod.GET,
			produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<RentacarServis> getHotel(@PathVariable("id") Long id, @PathVariable("check_in") String check_in, @PathVariable("check_out") String check_out) throws ParseException
	{
		System.out.println("usao u metodu api/rents/{id}/checkin/checkout");
		
		DateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		Date date_check_in = format.parse(check_in);
		Date date_check_out = format.parse(check_out);
		
		RentacarServis result = null;
		
		try{
			result = rentService.findById(id).get();
			System.out.println("Nasao rentacar sa id:" + result.getId());
		}catch(NoSuchElementException e)
		{
			System.out.println("Ne postoji hotel sa id: " + id);
			return null; 	
		}
		
		List<RentacarServis> servisi = new ArrayList<RentacarServis>();
		servisi.add(result);
		String[] search = new String[1];
		search[0] = "";
		
		
		List<RentacarServis> rents = pretragaServisa(servisi, search, date_check_in, date_check_out, result.getAdresa().getGrad().getNaziv(), result.getAdresa().getGrad().getNaziv(), 0);
		result = rents.get(0);
		
		System.out.println("[HotelController: getHotel]: id pronadjenog hotela: " + result.getId());
		
		return new ResponseEntity<RentacarServis>(result, HttpStatus.OK);
	}
	
	@RequestMapping(
			value = "/get_number_of_days/{check_in}/{check_out}",
			method = RequestMethod.POST,
			produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Integer> getNumberOfDays(@PathVariable("check_in") String check_in, @PathVariable("check_out") String check_out) throws ParseException
	{
		System.out.println("RentacarController: getNumberOfDays]: usao u metodu getnumberOfDays");
		//RezervacijaHotel rezervacijaHotel = (RezervacijaHotel) request.getSession().getAttribute("rezervacijaHotel");
		int result;
		
		if(check_in.equals("0001-01-01") || check_out.equals("0001-01-01")) {
			result = 0;
		}else {
			DateFormat format = new SimpleDateFormat("yyyy-MM-dd");
			Date date_check_in = format.parse(check_in);
			Date date_check_out = format.parse(check_out);
			result = (int) Math.round((date_check_out.getTime() - date_check_in.getTime()) / (double) 86400000) + 1;
		}
		System.out.println("[RentacarController: getNumberOfDays]: numberOfDays: " + result);
		
		return new ResponseEntity<Integer>(result, HttpStatus.OK);
		
	}
	
	@RequestMapping(
			value = "/book_car/{vozilo_id}",
			method = RequestMethod.PUT,
			produces = MediaType.APPLICATION_JSON_VALUE,
			consumes = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public ResponseEntity<Vozilo> bookCar(@PathVariable("vozilo_id") Long vozilo_id, @RequestBody RezervacijaVozila rezervacijaVozila){
		
		//RezervacijaHotel rezervacijaHotel = (RezervacijaHotel) request.getSession().getAttribute("rezervacijaHotel");
		Vozilo vozilo =  voziloService.findById(vozilo_id).get();
		rezervacijaVozila.setVozilo(vozilo);;
		rezervacijaVozila.setAktivirana(true);
		
		Date date_check_in = rezervacijaVozila.getDatum_preuzimanja();
		Date date_check_out = rezervacijaVozila.getDatum_vracanja();
		int broj_dana = (int) Math.round((date_check_out.getTime() - date_check_in.getTime()) / (double) 86400000) + 1;
		
		double cena_rez = broj_dana * vozilo.getCena_dan();
		
		rezervacijaVozila.setUkupna_cena(cena_rez);
		//request.getSession().setAttribute("rezervacijaHotel", rezervacijaHotel);
		
		//rezervacijaHotelService.updateSoba(soba.getId(), true, cena_rez, rezervacijaHotel.getId());
		
		//request.getSession().removeAttribute("rezervacijaHotel");
		rezervacijaVozila = rezervacijaVozilaService.save(rezervacijaVozila);
		
		return new ResponseEntity<Vozilo>(vozilo, HttpStatus.OK);
	}
	
}
