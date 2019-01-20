package com.example.ISA_AMA_projekat.controller;

import java.util.NoSuchElementException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.example.ISA_AMA_projekat.model.Filijala;
import com.example.ISA_AMA_projekat.model.RentacarServis;
import com.example.ISA_AMA_projekat.service.FilijalaService;

@RestController
@RequestMapping(value="api/filijale")
public class FilijalaController {
	
	@Autowired
	private FilijalaService filijalaService;
	
	@RequestMapping(
			value = "/{id}",
			method = RequestMethod.GET,
			produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Filijala> getFilijala(@PathVariable("id") Long id)
	{
		try
		{
			Filijala found = filijalaService.findById(id).get();
			return new ResponseEntity<Filijala>(found, HttpStatus.OK);
		}
		catch(NoSuchElementException e)
		{
			System.out.println("Ne postoji filijala sa id: " + id);
			return null; 	
		}
	}

}
