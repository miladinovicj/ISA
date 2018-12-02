package com.example.ISA_AMA_projekat.controller;

import java.util.ArrayList;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;




import com.example.ISA_AMA_projekat.model.FriendRequest;
import com.example.ISA_AMA_projekat.model.Korisnik;
import com.example.ISA_AMA_projekat.model.Poziv;
import com.example.ISA_AMA_projekat.service.KorisnikService;

@RestController
@RequestMapping(value="api/users")
public class KorisnikController {
	
	@Autowired
	private KorisnikService korisnikService; 
	
	@RequestMapping(
			value = "/registruj",
			method = RequestMethod.POST,
			consumes = MediaType.APPLICATION_JSON_VALUE,
			produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Korisnik> saveKorisnik(@RequestBody Korisnik korisnik){
		
		if(korisnik==null)
		{
			System.out.println("KORISNIK JE NULL");
			return null;
		}
		else
		{
		System.out.println("KORISNIK " + korisnik.getEmail() + " " + korisnik.getLozinka() + " " + korisnik.getIme() + " " + korisnik.getPrezime() + " " +
				korisnik.getGrad() + " " + korisnik.getTelefon());
		
		Korisnik novi_korisnik = new Korisnik();
		novi_korisnik.setEmail(korisnik.getEmail());
		novi_korisnik.setLozinka(korisnik.getLozinka());
		novi_korisnik.setIme(korisnik.getIme());
		novi_korisnik.setPrezime(korisnik.getPrezime());
		novi_korisnik.setGrad(korisnik.getGrad());
		novi_korisnik.setTelefon(korisnik.getTelefon());
		novi_korisnik.setBonus_poeni(0);
		novi_korisnik.setPoziviZaRezervacije(new ArrayList<Poziv>());
		novi_korisnik.setPrijateljstva(new ArrayList<FriendRequest>());
		
	
		
		novi_korisnik = korisnikService.save(novi_korisnik);
		return new ResponseEntity<Korisnik>(novi_korisnik, HttpStatus.CREATED);
		}
	}

}
