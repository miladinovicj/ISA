package com.example.ISA_AMA_projekat.controller;

import java.io.IOException;
import java.security.Principal;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.example.ISA_AMA_projekat.model.Authority;
import com.example.ISA_AMA_projekat.model.Aviokompanija;
import com.example.ISA_AMA_projekat.model.Grad;
import com.example.ISA_AMA_projekat.model.Hotel;
import com.example.ISA_AMA_projekat.model.Korisnik;
import com.example.ISA_AMA_projekat.model.RentacarServis;
import com.example.ISA_AMA_projekat.service.AuthorityService;
import com.example.ISA_AMA_projekat.service.AviokompanijaService;
import com.example.ISA_AMA_projekat.service.EmailService;
import com.example.ISA_AMA_projekat.service.GradService;
import com.example.ISA_AMA_projekat.service.HotelService;
import com.example.ISA_AMA_projekat.service.KorisnikService;
import com.example.ISA_AMA_projekat.service.RentacarService;


@RestController
@RequestMapping(value="api/users")
public class KorisnikController {
	
	@Autowired
	private KorisnikService korisnikService; 
	
	@Autowired
	private AuthorityService authorityService; 

	@Autowired
	private GradService gradService; 
	
	@Autowired
	private EmailService emailService;
	
	@Autowired
	private AviokompanijaService aviokompanijaService;
	
	@Autowired
	private HotelService hotelService;
	
	@Autowired
	private RentacarService rentacarService;
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	@RequestMapping(
			value = "/registruj",
			method = RequestMethod.POST,
			consumes = MediaType.APPLICATION_JSON_VALUE,
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
			
			Grad grad = gradService.findByNaziv(korisnik.getGrad().getNaziv());
			System.out.println("KORISNIK " + korisnik.getEmail() + " " + korisnik.getLozinka() + " " + korisnik.getIme() + " " + korisnik.getPrezime() + " " +
				korisnik.getGrad().getNaziv() + " " + korisnik.getTelefon());
		
		String poslatMejl= signUpAsync(korisnik);
		
		if(poslatMejl.equals("success"))
		{
				Korisnik novi_korisnik = new Korisnik();
				novi_korisnik.setEmail(korisnik.getEmail());
			
				
				novi_korisnik.setLozinka(passwordEncoder.encode(korisnik.getLozinka()));
				
				novi_korisnik.setIme(korisnik.getIme());
				novi_korisnik.setPrezime(korisnik.getPrezime());
				if(grad!=null)
				{
					novi_korisnik.setGrad(grad);
				}
				else
				{
					Grad novi_gr = new Grad();
					novi_gr.setNaziv(korisnik.getGrad().getNaziv());
					gradService.save(novi_gr);
					novi_korisnik.setGrad(novi_gr);
				}
				novi_korisnik.setTelefon(korisnik.getTelefon());
				novi_korisnik.setBonus_poeni(0);
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
			Authority uloga = null;
			uloga = authorityService.findByName("ROLE_USER");
			
			if(uloga == null) {
				uloga = new Authority();
				uloga.setName("ROLE_USER");
				authorityService.save(uloga);
			}
			
			potvrda.setAuthority(uloga);
			//authorityService.updateUserAuthority(potvrda.getId(), uloga.getId());
			korisnikService.updateAkt(true, potvrda.getId());
			response.sendRedirect("http://localhost:8080/prijava.html");
			System.out.println("USPESNO AKTIVIRAN");
			
		}
	}

	
	@RequestMapping("/whoami")
	@PreAuthorize("hasRole('USER')")
	public Korisnik user(Principal user) {
		System.out.println("USER NAME: " + user.getName());
		return this.korisnikService.findByEmail(user.getName());
	}
	
	@PreAuthorize("hasRole('SYSADMIN')")
	@RequestMapping(
			value = "/registruj_admina/{uloga}",
			method = RequestMethod.POST,
			consumes = MediaType.APPLICATION_JSON_VALUE,
			produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Korisnik> registerAdmin(@RequestBody Korisnik korisnik, @PathVariable("uloga") Integer uloga){
		
		Korisnik postoji = korisnikService.findByEmail(korisnik.getEmail());
		
		if(postoji!=null) {
			System.out.println("[KorisnikController: registerAdmin] korisnik sa ovim mejlom vec postoji");
			return null;
			
		}else {
			
			Grad grad = gradService.findByNaziv(korisnik.getGrad().getNaziv());
			
			if(grad == null){
				grad = new Grad();
				grad.setNaziv(korisnik.getGrad().getNaziv());
				grad = gradService.save(grad);
				System.out.println("[KorisnikController: registerAdmin] grad nije postojao; sad ima id: " + grad.getId());
			}else {
				System.out.println("[KorisnikController: registerAdmin] grad je postojao; ima id: " + grad.getId());
			}
			
			System.out.println("[KorisnikController: registerAdmin] korisnik: " + korisnik.getEmail() + " " + korisnik.getLozinka() + " " + korisnik.getIme() + " " + korisnik.getPrezime() + " " +
				korisnik.getGrad().getNaziv() + " " + korisnik.getTelefon());
		
			
			Korisnik novi_korisnik = new Korisnik();
			novi_korisnik.setEmail(korisnik.getEmail());
			novi_korisnik.setLozinka(passwordEncoder.encode(korisnik.getLozinka()));
			
			novi_korisnik.setIme(korisnik.getIme());
			novi_korisnik.setPrezime(korisnik.getPrezime());
			
			novi_korisnik.setTelefon(korisnik.getTelefon());
			novi_korisnik.setGrad(grad);
			novi_korisnik.setBonus_poeni(0);
			
			novi_korisnik.setAdmin_id(korisnik.getAdmin_id());
			novi_korisnik.setAktiviran(false);

			Authority auth = null;
			
			if(uloga == 0) {
				auth = authorityService.findByName("ROLE_SYSADMIN");
				
				if(auth == null) {
					auth = new Authority();
					auth.setName("ROLE_SYSADMIN");
					authorityService.save(auth);
				}
				
				novi_korisnik.setAdmin_id(null);
				novi_korisnik.setAktiviran(false);
				
			}else if(uloga == 1) {
				auth = authorityService.findByName("ROLE_AVIOADMIN");
				
				if(auth == null) {
					auth = new Authority();
					auth.setName("ROLE_AVIOADMIN");
					authorityService.save(auth);
				}
				
			}else if(uloga == 2) {
				auth = authorityService.findByName("ROLE_HOTELADMIN");
				
				if(auth == null) {
					auth = new Authority();
					auth.setName("ROLE_HOTELADMIN");
					authorityService.save(auth);
				}
				
			}else {
				auth = authorityService.findByName("ROLE_RENTADMIN");
				
				if(auth == null) {
					auth = new Authority();
					auth.setName("ROLE_RENTADMIN");
					authorityService.save(auth);
				}
			}
			
			novi_korisnik.setAuthority(auth);
			novi_korisnik = korisnikService.save(novi_korisnik);
			
			if(uloga == 1) {

				Aviokompanija avio = aviokompanijaService.findById(korisnik.getAdmin_id()).get();
				avio.setId_admin(novi_korisnik.getId());
				aviokompanijaService.updateAdmin(avio.getId(), novi_korisnik.getId());
				
			}else if(uloga == 2) {
				
				Hotel hotel = hotelService.findById(korisnik.getAdmin_id()).get();
				hotel.setId_admin(novi_korisnik.getId());
				hotelService.updateAdmin(hotel.getId(), novi_korisnik.getId());
				
			}else if(uloga == 3) {
				
				System.out.println("Ovde usao da registruje admina");
				RentacarServis rental = rentacarService.findById(korisnik.getAdmin_id()).get();
				rental.setId_admin(novi_korisnik.getId());
				rentacarService.updateAdmin(rental.getId(), novi_korisnik.getId());
				
			}
			
			System.out.println("[KorisnikController: registerAdmin] admin uspesno registrovan, id: " + novi_korisnik.getId());
			return new ResponseEntity<Korisnik>(novi_korisnik, HttpStatus.CREATED);
		
		}
	}
	
	@RequestMapping("/changeData/{email}")
	public void changeData(@PathVariable("email") String email, @RequestBody Korisnik korisnik){
		
		System.out.println("[KorisnikController: changeData] email stari: " + email);
		Korisnik stari = korisnikService.findByEmail(email);
		
		stari.setIme(korisnik.getIme());
		stari.setPrezime(korisnik.getPrezime());
		stari.setEmail(korisnik.getEmail());
		stari.setTelefon(korisnik.getTelefon());
		
		Grad grad = gradService.findByNaziv(korisnik.getGrad().getNaziv());
		
		if(grad == null){
			grad = new Grad();
			grad.setNaziv(korisnik.getGrad().getNaziv());
			grad = gradService.save(grad);
		}
		
		stari.setGrad(grad);
		
		System.out.println("[KorisnikController: changeData] id: " + stari.getId() + "; ime: " + stari.getIme() + "; prezime: " + stari.getPrezime() + "; email: "
																	+ stari.getEmail() + "; telefon: " + stari.getTelefon() + "; grad: " + stari.getGrad().getNaziv());
		
		korisnikService.updateKorisnik(stari.getId(), stari.getIme(), stari.getPrezime(), stari.getEmail(), stari.getTelefon(), stari.getGrad());
		
		System.out.println("[KorisnikController: changeData] korisnik uspesno update-ovan.");
	}
	
	
	@RequestMapping(value = "/friendsOf/{id}",method = RequestMethod.GET,produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Collection<Korisnik>> getAllFriendsOf(@PathVariable("id") int userID)
	{
		Collection<Korisnik> list = korisnikService.getAllFriendsOfUser(userID);
		
		return new ResponseEntity<Collection<Korisnik>>(list,HttpStatus.OK);
	}
	
	
	
	@RequestMapping(value = "/withNames/{text}/{id}",method = RequestMethod.GET,produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Collection<Korisnik>> getAllFriendsOf(@PathVariable("text") String namePiece, @PathVariable("id") Integer userID)
	{
		
		if(namePiece.equals(""))
		{
			return null;
		}
		
		ArrayList<Korisnik> retVal = new ArrayList<Korisnik>();
		
		List<Korisnik> sviKorisnici = korisnikService.findAll();
		
		for(int i = 0 ; i < sviKorisnici.size() ; i ++)
		{
			Korisnik k = sviKorisnici.get(i);
			if(korisnikService.areStrangers(userID, k))
			{
				String stringic = k.getIme().toLowerCase() + " " + k.getPrezime().toLowerCase();
				
				if(stringic.contains(namePiece.toLowerCase()))
				{
					retVal.add(k);
				}
			}
		}
		
		
		return new ResponseEntity<Collection<Korisnik>>(retVal,HttpStatus.OK);
	}
	
}
