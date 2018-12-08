package com.example.ISA_AMA_projekat.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.ISA_AMA_projekat.model.Aviokompanija;
import com.example.ISA_AMA_projekat.model.Korisnik;
import com.example.ISA_AMA_projekat.repository.AviokompanijaRepository;

@Service
public class AviokompanijaService 
{
	@Autowired
	private AviokompanijaRepository avioRepo;
	
	public Optional<Aviokompanija> findById(Integer id)
	{
		return avioRepo.findById(id);
	}
	
	public List<Aviokompanija> findAll() {
		return avioRepo.findAll();
	}
}
