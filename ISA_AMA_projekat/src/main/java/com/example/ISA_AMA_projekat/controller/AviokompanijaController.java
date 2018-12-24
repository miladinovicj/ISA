package com.example.ISA_AMA_projekat.controller;

import org.springframework.http.MediaType;
import java.util.List;
import java.util.NoSuchElementException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.example.ISA_AMA_projekat.service.AviokompanijaService;
import com.example.ISA_AMA_projekat.model.Aviokompanija;

@RestController
@RequestMapping(value="rest/airline")
public class AviokompanijaController 
{
	@Autowired
	private AviokompanijaService avioServis;
	
	@RequestMapping(
			value = "/{id}",
			method = RequestMethod.GET,
			produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Aviokompanija> getAviokompanija(@PathVariable("id") Integer id)
	{
		
		try
		{
			System.out.println("Nasao:" + avioServis.findById(id).get().getId());
			return new ResponseEntity<Aviokompanija>(avioServis.findById(id).get(), HttpStatus.OK);
		}
		catch(NoSuchElementException e)
		{
			System.out.println("ispao");
			return null; 	
		}
	}
	
	@RequestMapping(
			value = "",
			method = RequestMethod.GET,
			produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<Aviokompanija>> getAviokompanije(){
		
		List<Aviokompanija> los_avios = avioServis.findAll();
		
		return new ResponseEntity<List<Aviokompanija>>(los_avios, HttpStatus.OK);
	}
	
	@RequestMapping(
			value = "/save",
			method = RequestMethod.POST,
			consumes=MediaType.APPLICATION_JSON_VALUE,
			produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Aviokompanija> saveAviokompanija(@RequestBody Aviokompanija aviokompanija){
		
		Aviokompanija postoji = avioServis.findByNaziv(aviokompanija.getNaziv());
		
		if(postoji != null) {
			System.out.println("Aviokompanija sa ovim nazivom vec postoji u bazi");
			return null;
		}else {
			Aviokompanija saved = avioServis.save(aviokompanija);
			return new ResponseEntity<Aviokompanija>(saved, HttpStatus.CREATED);
		}
		
	}
}
