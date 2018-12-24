package com.example.ISA_AMA_projekat.controller;

import java.util.Collection;
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

import com.example.ISA_AMA_projekat.model.RentacarServis;
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
			System.out.println("Ne postoji hotel sa id: " + id);
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
}
