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
import com.example.ISA_AMA_projekat.model.Grad;
import com.example.ISA_AMA_projekat.model.Popust;
import com.example.ISA_AMA_projekat.model.RentacarServis;
import com.example.ISA_AMA_projekat.model.RezervacijaVozila;
import com.example.ISA_AMA_projekat.model.Vozilo;
import com.example.ISA_AMA_projekat.service.GradService;
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
	private GradService gradService; 
	
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
	public ResponseEntity<RentacarServis> getRentacarServis(@PathVariable("id") Integer id)
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
			value = "/{id}/{check_in}/{check_in_town}/{check_out}/{check_out_town}/{passengers}",
			method = RequestMethod.GET,
			produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<RentacarServis> getHotel(@PathVariable("id") Integer id, @PathVariable("check_in") String check_in, @PathVariable("check_in_town") String check_in_town,  @PathVariable("check_out") String check_out, @PathVariable("check_out_town") String check_out_town, @PathVariable("passengers") int passengers) throws ParseException
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
			System.out.println("Ne postoji rentacar sa id: " + id);
			return null; 	
		}
		
		
		if(check_in_town.equals("prazan") && check_out_town.equals("prazan"))
		{
			check_in_town=result.getAdresa().getGrad().getNaziv();
			check_out_town=result.getAdresa().getGrad().getNaziv();
		}
		result = pretraga_servisa(result, date_check_in, date_check_out,check_in_town, check_out_town, passengers);
		
		
		System.out.println("[RentacarController: getRentacar]: id pronadjenog servisa: " + result.getId());
		
		return new ResponseEntity<RentacarServis>(result, HttpStatus.OK);
	}
	
	public RentacarServis pretraga_servisa(RentacarServis servis,
			Date check_in, Date check_out, String check_in_town,
			String check_out_town, int passengers) {
		RentacarServis result = servis;
		check_in_town=check_in_town.toLowerCase();
		check_out_town=check_out_town.toLowerCase();
		
		System.out.println("[RentacarController: pretraga_servisa]: iteracija pomocu iteratora; naziv servisa: " + servis.getNaziv());
		boolean adresaPreuzimanja = false;
		boolean adresaVracanja = false;
		
			for (Iterator<Filijala> iteratorFilijala = servis.getFilijale().iterator(); iteratorFilijala.hasNext();)
			{
				Filijala filijala = (Filijala) iteratorFilijala.next();
				System.out.println("[RentacarController: pretraga] MESTO FILIJALE: " + filijala.getAdresa().getGrad().getNaziv());
				if(filijala.getAdresa().getGrad().getNaziv().toLowerCase().equals(check_in_town) ||
						servis.getAdresa().getGrad().getNaziv().toLowerCase().equals(check_in_town)  )
				{
					adresaPreuzimanja=true;
					break;
				}
			}
		
		
		
			for (Iterator<Filijala> iteratorFilijala = servis.getFilijale().iterator(); iteratorFilijala.hasNext();)
			{
				Filijala filijala = (Filijala) iteratorFilijala.next();
				System.out.println("[RentacarController: pretraga] MESTO FILIJALE: " + filijala.getAdresa().getGrad().getNaziv());
				if(filijala.getAdresa().getGrad().getNaziv().toLowerCase().equals(check_out_town) ||
						servis.getAdresa().getGrad().getNaziv().toLowerCase().equals(check_out_town) )
				{
					adresaVracanja=true;
					break;
				}
			}
			
	if(adresaPreuzimanja==true && adresaVracanja==true)
	{
		for(Iterator<Filijala> iteratorFil = servis.getFilijale().iterator(); iteratorFil.hasNext();)
		{
			Filijala filijala = iteratorFil.next();
		for(Iterator<Vozilo> iteratorVozilo = filijala.getVozila().iterator(); iteratorVozilo.hasNext();) {
			System.out.println("[RentacarController: pretraga_servisa]: usao u for za prolazak kroz vozila; ima " + filijala.getVozila().size() + " vozila.");
			Vozilo vozilo = iteratorVozilo.next();
			
			if(isBrzaVozilo(vozilo, check_in, check_out)) {
				System.out.println("[RentacarController: pretraga_servisa]: vozilo sa id: " + vozilo.getId() + " je brza rez.");
				iteratorVozilo.remove();
			}else {
				if(vozilo.getRezervacije().isEmpty()) {
					System.out.println("[RentacarController: pretraga_servisa]: vozilo nema rezervacija.");
				}else {
					System.out.println("[RentacarController: pretraga_servisa]: postoje rezervacije za vozilo; ima ih " + vozilo.getRezervacije().size() + ".");
					for(Iterator<RezervacijaVozila> iteratorRezervacija = vozilo.getRezervacije().iterator(); iteratorRezervacija.hasNext();) {
						RezervacijaVozila rezervacijaVozila = iteratorRezervacija.next();
						System.out.println("RentacarController: pretraga_servisa]: zahtev:  datum dolaska: " + check_in + ", datum odlaska: " + check_out + ".");
						
						if(rezervacijaVozila.isAktivirana() && (check_in.equals(rezervacijaVozila.getDatum_preuzimanja()) || check_in.equals(rezervacijaVozila.getDatum_vracanja()) || check_out.equals(rezervacijaVozila.getDatum_preuzimanja()) || ((rezervacijaVozila.getDatum_preuzimanja()).after(check_in) && (rezervacijaVozila.getDatum_preuzimanja()).before(check_out)) || (check_in.after(rezervacijaVozila.getDatum_preuzimanja()) && check_in.before(rezervacijaVozila.getDatum_vracanja())) || (check_out.after(rezervacijaVozila.getDatum_preuzimanja()) && check_out.before(rezervacijaVozila.getDatum_vracanja())))) {
							System.out.println("[RentacarController: pretraga_servisa]: vozilo je zauzeto u trazenom periodu.");
							iteratorVozilo.remove();
						}else {
							System.out.println("[RentacarController: pretraga_servisa]: vozilo je slobodno u trazenom periodu.");
						}
					}
				}
			}
		}
		
		if(passengers != 0) {
			
			for(Iterator<Vozilo> iteratorVozilo = filijala.getVozila().iterator(); iteratorVozilo.hasNext();) {
				Vozilo vozilo = (Vozilo) iteratorVozilo.next();
				if(vozilo.getBroj_sedista() >= passengers) {
					System.out.println("[RentacarController: pretraga_servisa]: broj sedista: " + vozilo.getBroj_sedista() + ".");
				}else {
					iteratorVozilo.remove();
					System.out.println("[RentacarController: pretraga_servisa]: uklanjanje vozila.");
					}
			}
		}
		}
		
	}	
		
		return result;
	}

	@RequestMapping(
			value = "/get_number_of_days/{check_in}/{check_out}",
			method = RequestMethod.POST,
			produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Integer> getNumberOfDaysCar(@PathVariable("check_in") String check_in, @PathVariable("check_out") String check_out) throws ParseException
	{
		System.out.println("RentacarController: getNumberOfDays]: usao u metodu getnumberOfDays");
		//RezervacijaHotel rezervacijaHotel = (RezervacijaHotel) request.getSession().getAttribute("rezervacijaHotel");
		int result;
		
		if(check_in.equals("0001-01-01") || check_out.equals("0001-01-01")) {
			System.out.println("REZULTAT JE NULA");
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
	public ResponseEntity<Vozilo> bookCar(@PathVariable("vozilo_id") Integer vozilo_id, @RequestBody RezervacijaVozila rezervacijaVozila) throws ParseException{
		
		//RezervacijaHotel rezervacijaHotel = (RezervacijaHotel) request.getSession().getAttribute("rezervacijaHotel");
		Vozilo vozilo =  voziloService.findById(vozilo_id).get();
		rezervacijaVozila.setVozilo(vozilo);
		
		rezervacijaVozila.setAktivirana(true);
		
		Date date_check_in = rezervacijaVozila.getDatum_preuzimanja();
		Date date_check_out = rezervacijaVozila.getDatum_vracanja();
		int broj_dana = (int) Math.round((date_check_out.getTime() - date_check_in.getTime()) / (double) 86400000) + 1;
		double cena_rez = broj_dana * vozilo.getCena_dan();
		
		rezervacijaVozila.setUkupna_cena(cena_rez);
		System.out.println("REZ: " + rezervacijaVozila.getId() + " " + rezervacijaVozila.getBroj_putnika() + " " + rezervacijaVozila.getUkupna_cena()
				+ " " + rezervacijaVozila.getDatum_preuzimanja() + " " + rezervacijaVozila.getDatum_rezervacije() + 
				" " + rezervacijaVozila.getDatum_vracanja() + " " + rezervacijaVozila.getMesto_preuzimanja().getNaziv() + " "
				+ rezervacijaVozila.getMesto_vracanja().getNaziv() + " " + rezervacijaVozila.getVozilo().getModel());
		
		rezervacijaVozila = rezervacijaVozilaService.save(rezervacijaVozila);
		rezervacijaVozilaService.insertRezervacijaVozila(rezervacijaVozila.getId(), vozilo.getId());
		return new ResponseEntity<Vozilo>(vozilo, HttpStatus.OK);
	}
	
	@RequestMapping(
			value = "/rezervacija/{check_in}/{check_in_town}/{check_out}/{check_out_town}/{passengers}",
			method = RequestMethod.GET,
			produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<RezervacijaVozila> getRezervacija(@PathVariable("check_in") String check_in, @PathVariable("check_in_town") String check_in_town,@PathVariable("check_out") String check_out, @PathVariable("check_out_town") String check_out_town, @PathVariable("passengers") int passengers) throws ParseException{
		if(passengers <= 0)
			passengers=1;
		DateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		Date date_check_in = format.parse(check_in);
		Date date_check_out = format.parse(check_out);
		
		RezervacijaVozila rezervacijavozila = new RezervacijaVozila();
		rezervacijavozila.setDatum_preuzimanja(date_check_in);;
		rezervacijavozila.setDatum_vracanja(date_check_out);;
		rezervacijavozila.setBrza(false);
		Grad grad_preuzimanja = gradService.findByNaziv(check_in_town);
		Grad grad_vracanja = gradService.findByNaziv(check_out_town);
		
		rezervacijavozila.setMesto_preuzimanja(grad_preuzimanja);
		rezervacijavozila.setMesto_vracanja(grad_vracanja);
		rezervacijavozila.setBroj_putnika(passengers);
		
		rezervacijavozila.setAktivirana(false);
		
		//rezervacijaHotel = rezervacijaHotelService.save(rezervacijaHotel);
			
		return new ResponseEntity<RezervacijaVozila>(rezervacijavozila, HttpStatus.OK);
	}
	
	@RequestMapping(
			value = "specialPrice/{id}/{check_in}/{check_in_town}/{check_out}/{check_out_town}/{passengers}",
			method = RequestMethod.GET,
			produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Collection<Vozilo>> getVoziloSpecialPrice(@PathVariable("id") Integer id, @PathVariable("check_in") String check_in, @PathVariable("check_in_town") String check_in_town, @PathVariable("check_out") String check_out, @PathVariable("check_out_town") String check_out_town, @PathVariable("passengers") int passengers) throws ParseException
	{
		System.out.println("usao u metodu api/rents/specialPrice/{id}, id: " + id + "mesto : " + check_in_town + " " + check_out_town );
		if(passengers <= 0)
			passengers=1;
		
		DateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		Date date_check_in = format.parse(check_in);
		Date date_check_out = format.parse(check_out);
		
		RentacarServis servis = null;
		
		try{
			servis = rentService.findById(id).get();
			System.out.println("[RentacarController: getVoziloSpecialPrice]: Nasao servis sa id:" + servis.getId());
		}catch(NoSuchElementException e)
		{
			System.out.println("Ne postoji hotel sa id: " + id);
			return null; 	
		}
		
		check_in_town=check_in_town.toLowerCase();
		check_out_town=check_out_town.toLowerCase();
		
		boolean adresaPreuzimanja = false;
		boolean adresaVracanja = false;
		
			for (Iterator<Filijala> iteratorFilijala = servis.getFilijale().iterator(); iteratorFilijala.hasNext();)
			{
				Filijala filijala = (Filijala) iteratorFilijala.next();
				System.out.println("[RentacarController: pretraga] MESTO FILIJALE: " + filijala.getAdresa().getGrad().getNaziv());
				if(filijala.getAdresa().getGrad().getNaziv().toLowerCase().equals(check_in_town) ||
						servis.getAdresa().getGrad().getNaziv().toLowerCase().equals(check_in_town)  )
				{
					adresaPreuzimanja=true;
					break;
				}
			}
		
		
		
			for (Iterator<Filijala> iteratorFilijala = servis.getFilijale().iterator(); iteratorFilijala.hasNext();)
			{
				Filijala filijala = (Filijala) iteratorFilijala.next();
				System.out.println("[RentacarController: pretraga] MESTO FILIJALE: " + filijala.getAdresa().getGrad().getNaziv());
				if(filijala.getAdresa().getGrad().getNaziv().toLowerCase().equals(check_out_town) ||
						servis.getAdresa().getGrad().getNaziv().toLowerCase().equals(check_out_town) )
				{
					adresaVracanja=true;
					break;
				}
			}
		
		
		System.out.println("[RentacarController: pretraga] mesto preuzimanja: " + adresaPreuzimanja + " mesto vracanja: " + adresaVracanja);
		
		Collection<Vozilo> result = new ArrayList<Vozilo>();
		boolean postojiPopust, postojiTerminVozilo;
		if(adresaPreuzimanja==true && adresaVracanja== true)
		{
			
				for(Iterator<Filijala> iteratorFil = servis.getFilijale().iterator(); iteratorFil.hasNext();)
				{
					Filijala filijala = iteratorFil.next();
					for(Iterator<Vozilo> iteratorVozilo = filijala.getVozila().iterator(); iteratorVozilo.hasNext();) {
					postojiPopust = false;
					postojiTerminVozilo = true;
					Vozilo vozilo = iteratorVozilo.next();
					for(Iterator<Popust> iteratorPopust = vozilo.getPopusti().iterator(); iteratorPopust.hasNext();) {
						Popust popust = iteratorPopust.next();
						if((date_check_in.equals(popust.getPocetak_vazenja()) || date_check_in.after(popust.getPocetak_vazenja())) && (date_check_out.equals(popust.getKraj_vazenja()) || date_check_out.before(popust.getKraj_vazenja()))) {
							System.out.println("[RentacarController: getVoziloSpecialPrice]: vozilo sa id: " + vozilo.getId() + " je na popustu u trazenom periodu.");
							postojiPopust = true;
							break;
						}else {
							System.out.println("[RentacarController: getVoziloSpecialPrice]: vozilo sa id: " + vozilo.getId() + " nije na popustu u trazenom periodu.");
						}
					}
					if(postojiPopust) {
						for(Iterator<RezervacijaVozila> iteratorRezervacija = vozilo.getRezervacije().iterator(); iteratorRezervacija.hasNext();) {
							RezervacijaVozila rezervacijaVozila = iteratorRezervacija.next();
							System.out.println("[RentacarController: getVoziloSpecialPrice]: zahtev:  datum dolaska: " + check_in + ", datum odlaska: " + check_out + ".");
							
							if(rezervacijaVozila.isAktivirana() && (date_check_in.equals(rezervacijaVozila.getDatum_preuzimanja()) || date_check_in.equals(rezervacijaVozila.getDatum_vracanja()) || date_check_out.equals(rezervacijaVozila.getDatum_preuzimanja()) || ((rezervacijaVozila.getDatum_preuzimanja()).after(date_check_in) && (rezervacijaVozila.getDatum_preuzimanja()).before(date_check_out)) || (date_check_in.after(rezervacijaVozila.getDatum_preuzimanja()) && date_check_in.before(rezervacijaVozila.getDatum_vracanja())) || (date_check_out.after(rezervacijaVozila.getDatum_preuzimanja()) && date_check_out.before(rezervacijaVozila.getDatum_vracanja())))) {
								System.out.println("[RentacarController: getVoziloSpecialPrice]: vozilo je zauzeto u trazenom periodu.");
								postojiTerminVozilo = false;
								break;
							}else {
								System.out.println("[RentacarController: getVoziloSpecialPrice]: vozilo je slobodno u trazenom periodu.");
							}
						}
						
						if(postojiTerminVozilo) {
							if(vozilo.getBroj_sedista() >= passengers) {
								System.out.println("[RentacarController: getVoziloSpecialPrice]: odgovara zbog broja osoba; dodato vozilo sa id:" + vozilo.getId());
								result.add(vozilo);
							}else {
								System.out.println("[RentacarController: getVoziloSpecialPrice]: vozilo sa id:" + vozilo.getId() + " ne odgovara zbog broja osoba");
							}
							
						}
					}
				}
				
				}
		}
		
		return new ResponseEntity<Collection<Vozilo>>(result, HttpStatus.OK);
	}
	
	@RequestMapping(
			value = "/popust/{vozilo_id}/{check_in}/{check_out}",
			method = RequestMethod.GET,
			produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Popust> getPopustVozilo(@PathVariable("vozilo_id") Integer vozilo_id, @PathVariable("check_in") String check_in_string, @PathVariable("check_out") String check_out_string) throws ParseException {
		System.out.println("[RentacarController: getPopust]: usao u metodu getPopust");
		
		Vozilo vozilo =  voziloService.findById(vozilo_id).get();
		Popust result = null;
		
		DateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		Date check_in = format.parse(check_in_string);
		Date check_out = format.parse(check_out_string);
		
		for(Iterator<Popust> iteratorPopusta = vozilo.getPopusti().iterator(); iteratorPopusta.hasNext();) {
			Popust popust = iteratorPopusta.next();
			if((check_in.equals(popust.getPocetak_vazenja()) || check_in.after(popust.getPocetak_vazenja())) && (check_out.equals(popust.getKraj_vazenja()) || check_out.before(popust.getKraj_vazenja()))) {
			//if(check_in.equals(popust.getPocetak_vazenja()) || check_in.equals(popust.getKraj_vazenja()) || check_out.equals(popust.getPocetak_vazenja()) || ((popust.getPocetak_vazenja()).after(check_in) && (popust.getPocetak_vazenja()).before(check_out)) || (check_in.after(popust.getPocetak_vazenja()) && check_in.before(popust.getKraj_vazenja())) || (check_out.after(popust.getPocetak_vazenja()) && check_out.before(popust.getKraj_vazenja()))) {
				result = popust;
				System.out.println("[RentacarController: getPopust]: vozilo je na popustu u trazenom periodu, popust id: " + popust.getId());
				break;
			}else {
				System.out.println("[RentacarController: isBrzaVozilo]: vozilo nije na popustu u trazenom periodu.");
			}
		}

		
		return new ResponseEntity<Popust>(result, HttpStatus.OK);
		
	}
	
	@RequestMapping(
			value = "/book_car_special/{vozilo_id}/{check_in}/{check_in_town}/{check_out}/{check_out_town}/{passengers}",
			method = RequestMethod.PUT,
			produces = MediaType.APPLICATION_JSON_VALUE,
			consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Vozilo> bookRoomSpecial(@PathVariable("vozilo_id") Integer vozilo_id, @PathVariable("check_in") String check_in_string, @PathVariable("check_in_town") String check_in_town, @PathVariable("check_out") String check_out_string,@PathVariable("check_out_town") String check_out_town, @PathVariable("passengers") int passengers, @RequestBody Popust popust) throws ParseException {
		
		
		Vozilo vozilo =  voziloService.findById(vozilo_id).get();
		
		System.out.println("[RentacarController: bookCarSpecial]: popust id: " + popust.getId());
		
		DateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		Date check_in = format.parse(check_in_string);
		Date check_out = format.parse(check_out_string);
		
		RezervacijaVozila rezervacijaVozila = new RezervacijaVozila();
		rezervacijaVozila.setDatum_preuzimanja(check_in);;
		rezervacijaVozila.setDatum_vracanja(check_out);;
		int broj_dana = (int) Math.round((check_out.getTime() - check_in.getTime()) / (double) 86400000) + 1;
		Grad grad_preuzimanja = gradService.findByNaziv(check_in_town);
		Grad grad_vracanja = gradService.findByNaziv(check_out_town);
		
		rezervacijaVozila.setMesto_preuzimanja(grad_preuzimanja);
		rezervacijaVozila.setMesto_vracanja(grad_vracanja);
		rezervacijaVozila.setBroj_putnika(passengers);
		rezervacijaVozila.setBrza(true);
		rezervacijaVozila.setPopust(popust.getPopust());
		rezervacijaVozila.setAktivirana(true);
		rezervacijaVozila.setVozilo(vozilo);
		double cena_rez = broj_dana * (vozilo.getCena_dan() * 0.01 * (100 - popust.getPopust()));
		
		System.out.println("[HotelController: bookRoomSpecial]: U popustu se nalaze sledece usluge: ");
		
		
		rezervacijaVozila.setUkupna_cena(cena_rez);
		rezervacijaVozila = rezervacijaVozilaService.save(rezervacijaVozila);
		rezervacijaVozilaService.insertRezervacijaVozila(rezervacijaVozila.getId(), vozilo.getId());
		
		
		
		return new ResponseEntity<Vozilo>(vozilo, HttpStatus.OK);
	}

	public boolean isBrzaVozilo(Vozilo vozilo, Date check_in, Date check_out) {
		
		boolean result = false;
		
		System.out.println("[RentacarController: isBrzaVozilo]: postoje popusti za vozilo; ima ih " + vozilo.getPopusti().size() + ".");
		for(Iterator<Popust> iteratorPopusta = vozilo.getPopusti().iterator(); iteratorPopusta.hasNext();) {
			Popust popust = iteratorPopusta.next();

			if(check_in.equals(popust.getPocetak_vazenja()) || check_in.equals(popust.getKraj_vazenja()) || check_out.equals(popust.getPocetak_vazenja()) || ((popust.getPocetak_vazenja()).after(check_in) && (popust.getPocetak_vazenja()).before(check_out)) || (check_in.after(popust.getPocetak_vazenja()) && check_in.before(popust.getKraj_vazenja())) || (check_out.after(popust.getPocetak_vazenja()) && check_out.before(popust.getKraj_vazenja()))) {

				System.out.println("[RentacarController: isBrzaVozilo]: vozilo je na popustu u trazenom periodu.");
				result = true;
				break;
			}else {
				System.out.println("[RentacarController: isBrzaVozilo]: vozilo nije na popustu u trazenom periodu.");
			}
		}
		
		return result;
	}
	
	@RequestMapping(
			value = "/admin/izmenaRent",
			method = RequestMethod.POST,
			consumes=MediaType.APPLICATION_JSON_VALUE,
			produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<RentacarServis> editServis(@RequestBody RentacarServis servis)
	{
		RentacarServis ser = rentService.findById(servis.getId()).get();
		Grad grad = null;
		grad = gradService.findByNaziv(servis.getAdresa().getGrad().getNaziv());
		if(grad==null)
		{
			grad=new Grad();
			grad.setNaziv(servis.getAdresa().getGrad().getNaziv());
			gradService.save(grad);
		}
		return new ResponseEntity<RentacarServis>(ser, HttpStatus.OK);
	}
}
