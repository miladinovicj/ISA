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

import com.example.ISA_AMA_projekat.model.Filijala;
import com.example.ISA_AMA_projekat.model.RentacarServis;
import com.example.ISA_AMA_projekat.model.RezervacijaVozila;
import com.example.ISA_AMA_projekat.model.Vozilo;
import com.example.ISA_AMA_projekat.service.RentacarService;

@RestController
@RequestMapping(value="api/rents")
public class RentacarController {

	@Autowired
	private RentacarService rentService;
	
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
			value = "/search/{name_location}/{check_in}/{check_out}",
			method = RequestMethod.GET,
			produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<RentacarServis>> getRentacarSearch(@PathVariable("name_location") String name_location, @PathVariable("check_in") String check_in, @PathVariable("check_out") String check_out) throws ParseException{
		
		System.out.println("[RentacarController: getRentacarSearch]: name_location-" + name_location + ", check_in-" + check_in + ", check_out-" + check_out);
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
		
		
		List<RentacarServis> servisi = pretragaServisa(rents, search, date_check_in, date_check_out);
			System.out.println("SERVISI VRACENI: " + servisi.size());
		return new ResponseEntity<List<RentacarServis>>(servisi, HttpStatus.OK);
		
	}
	
	public List<RentacarServis> pretragaServisa(List<RentacarServis> rents, String[] search, Date check_in, Date check_out){
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
		
		boolean postojiTerminVozilo;
		boolean postojiVozilo;
		
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
					break;
				}else {
					for(Iterator<RezervacijaVozila> iteratorRezervacija = vozilo.getRezervacije().iterator(); iteratorRezervacija.hasNext();) {
						System.out.println("[HotelController: pretraga]: postoje rezervacije za sobu; ima ih " + vozilo.getRezervacije().size() + ".");
						RezervacijaVozila rezervacijaVozilo = iteratorRezervacija.next();
						System.out.println("[RentacarController: pretraga]: zahtev:  datum preuzimanja: " + check_in + ", datum vracanja: " + check_out + ".");
						System.out.println("[RentacarController: pretraga]: rezervacija: " + rezervacijaVozilo.getId() + ", datum preuzimanja: " + rezervacijaVozilo.getDatum_preuzimanja() + ", datum vracanja: " + rezervacijaVozilo.getDatum_vracanja() + ".");
						System.out.println("[RentacarController: pretraga]: " + check_in.equals(rezervacijaVozilo.getDatum_preuzimanja()));
						System.out.println("[RentacarController: pretraga]: " + check_in.after(rezervacijaVozilo.getDatum_vracanja()));
						System.out.println("[RentacarController: pretraga]: " + check_in.before(rezervacijaVozilo.getDatum_vracanja()));
						if( check_in.equals(rezervacijaVozilo.getDatum_preuzimanja()) || check_in.equals(rezervacijaVozilo.getDatum_vracanja())
								|| check_out.equals(rezervacijaVozilo.getDatum_preuzimanja()) || 
								((rezervacijaVozilo.getDatum_preuzimanja()).after(check_in) && (rezervacijaVozilo.getDatum_preuzimanja()).before(check_out)) || 
								(check_in.after(rezervacijaVozilo.getDatum_preuzimanja()) && check_in.before(rezervacijaVozilo.getDatum_vracanja())) || 
								(check_out.after(rezervacijaVozilo.getDatum_preuzimanja()) && check_out.before(rezervacijaVozilo.getDatum_vracanja()))) {
							System.out.println("[RentacarController: pretraga]: vozilo je zauzeto u trazenom periodu.");
							postojiTerminVozilo = false;
							break;
						}else {
							System.out.println("[RentacarController: pretraga]: vozilo je slobodno u trazenom periodu.");
						}
					}
					
					if(postojiTerminVozilo) {
						postojiVozilo = true;
						break;
					}
				}
			}
			
			
			}
			
			if(!postojiVozilo) {
				result.remove(rentacar);
				i--;
			}
		}
		
		return result;
	}
}
