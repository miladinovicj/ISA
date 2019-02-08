package com.example.ISA_AMA_projekat.controller;

import java.util.NoSuchElementException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.ISA_AMA_projekat.model.Aviokompanija;
import com.example.ISA_AMA_projekat.model.Hotel;
import com.example.ISA_AMA_projekat.model.Korisnik;
import com.example.ISA_AMA_projekat.model.Let;
import com.example.ISA_AMA_projekat.model.Rezervacija;
import com.example.ISA_AMA_projekat.security.TokenUtils;
import com.example.ISA_AMA_projekat.service.AviokompanijaService;
import com.example.ISA_AMA_projekat.service.KorisnikService;
import com.example.ISA_AMA_projekat.service.LetService;

@RestController
@RequestMapping(value="api/let")
public class LetController 
{
	
	@Autowired
	private LetService letService;
	
	@Autowired
	private TokenUtils tokenUtils;
	
	@Autowired
	private KorisnikService userDetailsService;
	
	@Autowired
	private AviokompanijaService avioServis;
	
	@RequestMapping(
			value = "/{id}",
			method = RequestMethod.GET,
			produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Let> getLet(@PathVariable("id") Integer id)
	{
		try
		{
			Let let = letService.findById(id).get();
			return new ResponseEntity<Let>(let, HttpStatus.OK);
		}
		catch(NoSuchElementException e)
		{
			System.out.println("NE POSTOJI LET SA ID: " + id);
			return null;
		}
	}
	
	
	
	
	@PreAuthorize("hasRole('AVIOADMIN')")
	@RequestMapping(
			value = "/addFlight/{token}/{id}",
			method = RequestMethod.POST,
			consumes = MediaType.APPLICATION_JSON_VALUE,
			produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public ResponseEntity<Let> addFlight(@PathVariable("token") String token, @PathVariable("id") Integer aviokompanijaID, @RequestBody Let letRequest)
	{
		String email = tokenUtils.getUsernameFromToken(token);
		Korisnik korisnik = (Korisnik) this.userDetailsService.loadUserByUsername(email);
		
		if(korisnik.getAdmin_id() != aviokompanijaID)
		{
			System.err.println("This admin is not allowed to add flights to this airline.");
			return null;
		}
		
		if(letRequest.getAviokompanija().getId() != aviokompanijaID)
		{
			System.err.println("Cudna neka greska");
			return null;
		}
		
		//***bekend validacija
		if(letRequest.getMaxKapacitet()%4 != 0)
		{
			return null;
		}
		if(letRequest.getPopust() > 100 || letRequest.getPopust() < 0)
		{
			return null;
		}
		if(letRequest.getVremePoletanja().compareTo(letRequest.getVremeSletanja()) > 0)
		{
			return null;
		}
		
		Aviokompanija airline = avioServis.findById(aviokompanijaID).get();
		letRequest.setAviokompanija(airline);
		Let retVal = letService.save(letRequest);
		
		
		return new ResponseEntity<Let>(retVal,HttpStatus.CREATED);
	}
	
	
	
	
	@PreAuthorize("hasRole('AVIOADMIN')")
	@RequestMapping(
			value = "/editFlight/{token}",
			method = RequestMethod.PUT,
			consumes = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public String editFlight(@PathVariable("token") String token, @RequestBody Let letRequest)
	{
		Let oldie = null;
		try 
		{
			oldie = letService.findById(letRequest.getId()).get();
		}
		catch(Exception e)
		{
			return null;
		}
		
		Aviokompanija airline = oldie.getAviokompanija();
		
		
		String email = tokenUtils.getUsernameFromToken(token);
		Korisnik korisnik = (Korisnik) this.userDetailsService.loadUserByUsername(email);
			
		if(korisnik.getAdmin_id() != airline.getId())
		{
			System.err.println("This admin is not allowed to add flights to this airline.");
			return null;
		}
		
		
		
		
		//***bekend validacija
		if(letRequest.getMaxKapacitet()%4 != 0)
		{
			return null;
		}
		if(letRequest.getPopust() > 100 || letRequest.getPopust() < 0)
		{
			return null;
		}
		if(letRequest.getVremePoletanja().compareTo(letRequest.getVremeSletanja()) > 0)
		{
			return null;
		}
		
		
		//***zamena zamenjivih obelejza svezim
		letService.updateFlight(letRequest);
		
		
		return "success";
	}
	
	
	@RequestMapping(
			value = "/delete/{token}/{flightID}",
			method = RequestMethod.DELETE,
			produces = MediaType.APPLICATION_JSON_VALUE)
	public String deleteFlight(@PathVariable("token") String token, @PathVariable("flightID") Integer flightID)
	{
		Let oldie = null;
		try 
		{
			oldie = letService.findById(flightID).get();
		}
		catch(Exception e)
		{
			return null;
		}
		Aviokompanija airline = oldie.getAviokompanija();
		
		
		String email = tokenUtils.getUsernameFromToken(token);
		Korisnik korisnik = (Korisnik) this.userDetailsService.loadUserByUsername(email);
			
		if(korisnik.getAdmin_id() != airline.getId())
		{
			System.err.println("This admin is not allowed to add flights to this airline.");
			return null;
		}
		
		
		if(airline.getBrziLetovi().contains(oldie))
		{
			letService.deleteFlightAction(oldie);
		}		
		letService.deleteFlight(oldie);
		
		return "success";
	}

	
	@RequestMapping(
			value = "/addToActions/{token}/{flightID}",
			method = RequestMethod.POST,
			produces = MediaType.APPLICATION_JSON_VALUE)
	public String addToActions(@PathVariable("token") String token, @PathVariable("flightID") Integer flightID)
	{
		Let oldie = null;
		try 
		{
			oldie = letService.findById(flightID).get();
		}
		catch(Exception e)
		{
			return null;
		}
		Aviokompanija airline = oldie.getAviokompanija();
		
		
		String email = tokenUtils.getUsernameFromToken(token);
		Korisnik korisnik = (Korisnik) this.userDetailsService.loadUserByUsername(email);
			
		if(korisnik.getAdmin_id() != airline.getId())
		{
			System.err.println("This admin is not allowed to add flights to this airline.");
			return null;
		}
		
		if(airline.getBrziLetovi().contains(oldie))
		{
			return null;
		}
		
		if(oldie.getPopust() == 0)
		{
			return null;
		}
		
		letService.addToActions(airline.getId(), flightID);
		
		return "success";
	}
	
	
	@RequestMapping(
			value = "/removeFromSpecials/{token}/{flightID}",
			method = RequestMethod.DELETE,
			produces = MediaType.APPLICATION_JSON_VALUE)
	public String removeFromSpecial(@PathVariable("token") String token, @PathVariable("flightID") Integer flightID)
	{
		Let oldie = null;
		try 
		{
			oldie = letService.findById(flightID).get();
		}
		catch(Exception e)
		{
			return null;
		}
		Aviokompanija airline = oldie.getAviokompanija();
		
		
		String email = tokenUtils.getUsernameFromToken(token);
		Korisnik korisnik = (Korisnik) this.userDetailsService.loadUserByUsername(email);
			
		if(korisnik.getAdmin_id() != airline.getId())
		{
			System.err.println("This admin is not allowed to add flights to this airline.");
			return null;
		}
		
		
		if(airline.getBrziLetovi().contains(oldie))
		{
			letService.deleteFlightAction(oldie);
		}		
		
		return null;
	}
	

	
	
	
}
