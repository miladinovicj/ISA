package com.example.ISA_AMA_projekat.service;

import org.springframework.stereotype.Service;

import com.example.ISA_AMA_projekat.model.RezervacijaVozila;
import com.example.ISA_AMA_projekat.repository.RezervacijaVozilaRepository;

@Service
public class RezervacijaVozilaService {
	
	private RezervacijaVozilaRepository rezervacijaVozilaRepository;

	public RezervacijaVozila save(RezervacijaVozila rezervacijaVozila) {
		return rezervacijaVozilaRepository.save(rezervacijaVozila);
	}
	
}
