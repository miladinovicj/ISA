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

import com.example.ISA_AMA_projekat.model.Let;
import com.example.ISA_AMA_projekat.service.LetService;

@RestController
@RequestMapping(value="api/let")
public class LetController 
{
	
	@Autowired
	private LetService letService;
	
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
	
	
}
