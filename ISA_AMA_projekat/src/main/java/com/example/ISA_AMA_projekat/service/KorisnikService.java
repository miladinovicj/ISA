package com.example.ISA_AMA_projekat.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.ISA_AMA_projekat.model.FriendRequest;
import com.example.ISA_AMA_projekat.model.Grad;
import com.example.ISA_AMA_projekat.model.Korisnik;
import com.example.ISA_AMA_projekat.repository.FriendRequestRepository;
import com.example.ISA_AMA_projekat.repository.KorisnikRepository;


@Service
public class KorisnikService implements UserDetailsService{
	
	protected final Log LOGGER = LogFactory.getLog(getClass());
	
	@Autowired
	private KorisnikRepository korisnikRepository;
	
	@Autowired
	private PasswordEncoder passwordEncoder;

	@Autowired
	private AuthenticationManager authenticationManager;
	
	@Autowired 
	private FriendRequestRepository requestRepository;

	
	public Korisnik findByEmail(String email) 
	{
		return korisnikRepository.findOneByEmail(email);
	}

	public List<Korisnik> findAll() {
		return korisnikRepository.findAll();
	}
	
	public Optional<Korisnik> findById(Integer id)
	{
		return korisnikRepository.findById(id);
	}
	
	
	public List<Korisnik> getAllFriendsOfUser(int id)
	{
		List<FriendRequest> requestList = requestRepository.findBySaljeOrPrima(id);
		List<Korisnik> retVala = new ArrayList<Korisnik>();
		
		for (int i = 0 ; i < requestList.size() ; i ++ )
		{
			FriendRequest fq = requestList.get(i);
			
			Korisnik k = null;
			
			if(fq.getPrima().getId() == id)
			{
				k = fq.getSalje();
			}
			if(fq.getSalje().getId() == id)
			{
				k = fq.getPrima();
			}
			retVala.add(k);
		}
		
		return retVala;
	}
	
	
	
	public Korisnik save(Korisnik korisnik)
	{
		return korisnikRepository.save(korisnik);
	}
	
	@Transactional
	public void updateAkt(boolean aktiviran, Integer id)
	{
		korisnikRepository.updateAktiviran(aktiviran, id);
	}
	
	public void updateAktPass(boolean aktiviran, String new_pass, Integer id){
		korisnikRepository.updateAktiviranPass(aktiviran, new_pass, id);
	}
	
	public void updateKorisnik(Integer id, String ime, String prezime, String email, String telefon, Grad grad) {
		korisnikRepository.updateKorisnik(id, ime, prezime, email, telefon, grad);
	}

	@Override
	public UserDetails loadUserByUsername(String email)
			throws UsernameNotFoundException {
		Korisnik user = korisnikRepository.findOneByEmail(email);
		if (user == null) {
			throw new UsernameNotFoundException(String.format("No user found with email '%s'.", email));
		} else {
			return user;
		}
	}
	
	public void changePassword(String oldPassword, String newPassword) 
	{
		Authentication currentUser = SecurityContextHolder.getContext().getAuthentication();
		String username = currentUser.getName(); //ovde treba da bude email, ne znam sta ce vratiti
		System.out.println("[KorisnikService: changePassword] username: " + username);

		if (authenticationManager != null) {
			LOGGER.debug("Re-authenticating user '" + username + "' for password change request.");

			authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, oldPassword));
		} else {
			LOGGER.debug("No authentication manager set. can't change Password!");

			return;
		}

		LOGGER.debug("Changing password for user '" + username + "'");

		Korisnik user = (Korisnik) findByEmail(username);

		// pre nego sto u bazu upisemo novu lozinku, potrebno ju je hesirati
		// ne zelimo da u bazi cuvamo lozinke u plain text formatu
		//user.setPassword(passwordEncoder.encode(newPassword));
		user.setLozinka(passwordEncoder.encode(newPassword));
		korisnikRepository.save(user);
	}
	

}
