package com.example.ISA_AMA_projekat.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.example.ISA_AMA_projekat.model.Adresa;
import com.example.ISA_AMA_projekat.model.Grad;
import com.example.ISA_AMA_projekat.model.Usluga;
import com.example.ISA_AMA_projekat.service.UslugaService;

@RestController
@RequestMapping(value="api/usluge")
public class UslugaController {
	
	@Autowired
	private UslugaService uslugaService;

	@PreAuthorize("hasRole('RENTADMIN')")
	@RequestMapping(
			value = "/admin/izmenaUsluge/{izabrano}/{cena}",
			method = RequestMethod.POST,
			consumes=MediaType.APPLICATION_JSON_VALUE,
			produces = MediaType.APPLICATION_JSON_VALUE)
	public Usluga editUsluga(@PathVariable("izabrano") Integer izabrano,
			@PathVariable("cena") double cena)
	{
	
		Usluga u = uslugaService.findById(izabrano).get();
		System.out.println("[UslugaController]: usluga_id: " + u.getId());
		
		uslugaService.updateCena(cena, u.getId());
		return u;
	}
	
}
