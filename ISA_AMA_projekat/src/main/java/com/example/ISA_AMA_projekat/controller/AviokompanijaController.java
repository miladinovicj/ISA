package com.example.ISA_AMA_projekat.controller;

import java.util.ArrayList;
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

import com.example.ISA_AMA_projekat.model.Aviokompanija;
import com.example.ISA_AMA_projekat.service.AviokompanijaService;

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
			value = "all",
			method = RequestMethod.GET,
			produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Collection<Aviokompanija>> getAviokompanije(){
		
		Collection<Aviokompanija> los_avios = avioServis.findAll();
		
		return new ResponseEntity<Collection<Aviokompanija>>(los_avios, HttpStatus.OK);
	}
	
	@RequestMapping(
			value = "all_admin",
			method = RequestMethod.GET,
			produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Collection<Aviokompanija>> getAviokompanijeForAdmin(){
		
		Collection<Aviokompanija> los_avios = avioServis.findAll();
		Collection<Aviokompanija> result = new ArrayList<Aviokompanija>();
		
		for(Aviokompanija avio: los_avios) {
			if(avio.getId_admin() == null) {
				result.add(avio);
			}
		}
		
		return new ResponseEntity<Collection<Aviokompanija>>(result, HttpStatus.OK);
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
