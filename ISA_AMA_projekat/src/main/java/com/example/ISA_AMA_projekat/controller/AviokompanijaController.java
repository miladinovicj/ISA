package com.example.ISA_AMA_projekat.controller;

import org.springframework.http.MediaType;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Locale;
import java.util.NoSuchElementException;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import javax.websocket.server.PathParam;

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
import com.example.ISA_AMA_projekat.model.Korisnik;

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
}
