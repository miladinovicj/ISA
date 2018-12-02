package com.example.ISA_AMA_projekat.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;



import com.example.ISA_AMA_projekat.model.Korisnik;
import com.example.ISA_AMA_projekat.repository.KorisnikRepository;

@Service
public class KorisnikService {
	
	@Autowired
	private KorisnikRepository korisnikRepository;
	
	public Korisnik findByEmail(String email) {
		return korisnikRepository.findOneByEmail(email);
	}

	public List<Korisnik> findAll() {
		return korisnikRepository.findAll();
	}
	
	public Optional<Korisnik> findById(Long id)
	{
		return korisnikRepository.findById(id);
	}
	
	public Korisnik save(Korisnik korisnik)
	{
		return korisnikRepository.save(korisnik);
	}
	
	
	

}
