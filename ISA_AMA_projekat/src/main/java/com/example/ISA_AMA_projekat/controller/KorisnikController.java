package com.example.ISA_AMA_projekat.controller;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.example.ISA_AMA_projekat.model.FriendRequest;
import com.example.ISA_AMA_projekat.model.Korisnik;
import com.example.ISA_AMA_projekat.model.Poziv;
import com.example.ISA_AMA_projekat.service.EmailService;
import com.example.ISA_AMA_projekat.service.KorisnikService;

@RestController
@RequestMapping(value="api/users")
public class KorisnikController {
	
	@Autowired
	private KorisnikService korisnikService; 
	
	@Autowired
	private EmailService emailService;
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	@RequestMapping(
			value = "/registruj",
			method = RequestMethod.POST,
			consumes=MediaType.APPLICATION_JSON_VALUE,
			produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Korisnik> saveKorisnik(@RequestBody Korisnik korisnik)
	{
		System.out.println("KORISNIK: " + korisnik.getLozinka());
		Korisnik postoji = korisnikService.findByEmail(korisnik.getEmail());
		if(postoji!=null)
		{
			System.out.println("KORISNIK SA OVIM EMAIL-OM POSTOJI");
			return null;
		}
		else
		{
		System.out.println("KORISNIK " + korisnik.getEmail() + " " + korisnik.getLozinka() + " " + korisnik.getIme() + " " + korisnik.getPrezime() + " " +
				korisnik.getGrad() + " " + korisnik.getTelefon());
		
		String poslatMejl= signUpAsync(korisnik);
		
		if(poslatMejl.equals("success"))
		{
				Korisnik novi_korisnik = new Korisnik();
				novi_korisnik.setEmail(korisnik.getEmail());
			
				
				novi_korisnik.setLozinka(passwordEncoder.encode(korisnik.getLozinka()));
				
				novi_korisnik.setIme(korisnik.getIme());
				novi_korisnik.setPrezime(korisnik.getPrezime());
				novi_korisnik.setGrad(korisnik.getGrad());
				novi_korisnik.setTelefon(korisnik.getTelefon());
				novi_korisnik.setBonus_poeni(0);
				novi_korisnik.setPozivi(new ArrayList<Poziv>());
				novi_korisnik.setPrijateljstva(new ArrayList<FriendRequest>());
				novi_korisnik.setAktiviran(false);
		
	
		
				novi_korisnik = korisnikService.save(novi_korisnik);
		return new ResponseEntity<Korisnik>(novi_korisnik, HttpStatus.CREATED);
		}
		else
			return null;
		}
	}
	
	
	public String signUpAsync(Korisnik korisnik)
	{

		//slanje emaila
		try {
			emailService.sendNotificaitionAsync(korisnik);
		}catch( Exception e ){
			System.out.println("Greska prilikom slanja emaila: " + e.getMessage());
		}

		return "success";
	}
	

	@RequestMapping("/registrationConfirm/{email}")
	public void confirmation(@PathVariable("email") String email, HttpServletResponse response) throws IOException
	{
		
		System.out.println("EMAIL " + email);
		Korisnik potvrda = korisnikService.findByEmail(email);
		if(potvrda==null || potvrda.getAktiviran()==true)
		{
			System.out.println("NEMA OVOG KORISNIKA ILI JE VEC AKTIVIRAN");
			if(potvrda==null)
				System.out.println("KORISNIK JE NULL");
			else if(potvrda.getAktiviran()==true)
				System.out.println("KORISNIK JE AKTIVIRAN");
		}
		else
		{
			potvrda.setAktiviran(true);
			korisnikService.updateAkt(true, potvrda.getId());
			response.sendRedirect("http://localhost:8080/prijava.html");
			System.out.println("USPESNO AKTIVIRAN");
			
		}
	}

/*	@RequestMapping(
			value = "/login",
			method = RequestMethod.POST,
			consumes=MediaType.APPLICATION_JSON_VALUE,
			produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Korisnik> prijavaKorisnik(@RequestBody Korisnik korisnik)
	{
		
		Korisnik postoji = korisnikService.findByEmail(korisnik.getEmail());
		if(postoji==null)
		{
			System.out.println("KORISNIK SA OVIM EMAIL-OM NE POSTOJI");
			return null;
		}
		else
		{
		 if(!(postoji.getLozinka().equals(korisnik.getLozinka())))
		 {
			 System.out.println("Pogresna lozinka");
				return null;
		 }
		 else
		 {
			 if(postoji.getAktiviran()==false)
			 {
				 System.out.println("Nije aktiviran");
				 return null;
			 }
			 else
				 return new ResponseEntity<Korisnik>(postoji, HttpStatus.OK);
		 }
		
		}
	}*/
}
