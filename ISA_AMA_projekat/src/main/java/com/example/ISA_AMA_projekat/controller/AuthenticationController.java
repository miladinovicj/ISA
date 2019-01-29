package com.example.ISA_AMA_projekat.controller;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.mobile.device.Device;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.example.ISA_AMA_projekat.security.TokenUtils;
import com.example.ISA_AMA_projekat.common.DeviceProvider;
import com.example.ISA_AMA_projekat.model.Korisnik;
import com.example.ISA_AMA_projekat.model.UserTokenState;
import com.example.ISA_AMA_projekat.service.KorisnikService;


@RestController
@RequestMapping(value = "/auth", produces = MediaType.APPLICATION_JSON_VALUE)
public class AuthenticationController {

	@Autowired
	TokenUtils tokenUtils;

	@Autowired
	private PasswordEncoder passwordEncoder;
	
	@Autowired
	private AuthenticationManager authenticationManager;

	@Autowired
	private KorisnikService userDetailsService;

	
	@Autowired
	private DeviceProvider deviceProvider;
	
	
	@RequestMapping(value = "/login", method = RequestMethod.POST)
	public ResponseEntity<?> createAuthenticationToken(@RequestBody Korisnik korisnik,HttpServletResponse response, Device device) throws AuthenticationException, IOException {
		Korisnik postoji = userDetailsService.findByEmail(korisnik.getEmail());
		if(postoji==null)
		{
			System.out.println("KORISNIK SA OVIM EMAIL-OM NE POSTOJI");
			return null;
		}
		else
		{
			
		 if(!passwordEncoder.matches(korisnik.getLozinka(), postoji.getLozinka()))
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
			 {
				 final Authentication authentication = authenticationManager
							.authenticate(new UsernamePasswordAuthenticationToken(
									postoji.getEmail(),
									korisnik.getLozinka()));

					// Ubaci username + password u kontext
					SecurityContextHolder.getContext().setAuthentication(authentication);

					// Kreiraj token
					Korisnik user = (Korisnik) authentication.getPrincipal();
					System.out.println("DA LI JE AKRIVAN: " + user.getAktiviran() + "DA LI JE ENABLED: " + user.isEnabled());
					String jwt = tokenUtils.generateToken(user.getEmail(), device);
					int expiresIn = tokenUtils.getExpiredIn(device);

					// Vrati token kao odgovor na uspesno autentifikaciju
					return ResponseEntity.ok(new UserTokenState(jwt, expiresIn));
			 }
		 }
		
		}
		
	}
	
	@RequestMapping(value = "/refresh", method = RequestMethod.POST)
	public ResponseEntity<?> refreshAuthenticationToken(HttpServletRequest request) {

		String token = tokenUtils.getToken(request);
		String username = this.tokenUtils.getUsernameFromToken(token);
	    Korisnik user = (Korisnik) this.userDetailsService.loadUserByUsername(username);

		Device device = deviceProvider.getCurrentDevice(request);

		if (this.tokenUtils.canTokenBeRefreshed(token, user.getLastPasswordResetDate())) {
			String refreshedToken = tokenUtils.refreshToken(token, device);
			int expiresIn = tokenUtils.getExpiredIn(device);

			return ResponseEntity.ok(new UserTokenState(refreshedToken, expiresIn));
		} else {
			UserTokenState userTokenState = new UserTokenState();
			return ResponseEntity.badRequest().body(userTokenState);
		}
	}

	@RequestMapping(value = "/change-password", method = RequestMethod.POST)
	@PreAuthorize("hasRole('USER')")
	public ResponseEntity<?> changePassword(@RequestBody PasswordChanger passwordChanger) {
		userDetailsService.changePassword(passwordChanger.oldPassword, passwordChanger.newPassword);
		
		Map<String, String> result = new HashMap<>();
		result.put("result", "success");
		return ResponseEntity.accepted().body(result);
	}

	static class PasswordChanger {
		public String oldPassword;
		public String newPassword;
	}
	
	@RequestMapping(value = "/userprofile", method = RequestMethod.POST)
	public ResponseEntity<?> getProfile(@RequestBody String token) {

		System.out.println("IMA TOKEN: " + token);
		String email = tokenUtils.getUsernameFromToken(token);
		
		System.out.println("USERNAME: " + email);
	    Korisnik user = (Korisnik) this.userDetailsService.loadUserByUsername(email);
	    
	    System.out.println("Korisnik: " + user.getEmail());
		
			return  new ResponseEntity<Korisnik>(user, HttpStatus.OK);
	}

}
