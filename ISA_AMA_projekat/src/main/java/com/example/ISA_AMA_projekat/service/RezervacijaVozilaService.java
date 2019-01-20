package com.example.ISA_AMA_projekat.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.ISA_AMA_projekat.model.RezervacijaVozila;
import com.example.ISA_AMA_projekat.repository.RezervacijaVozilaRepository;

@Service
public class RezervacijaVozilaService {
	
	@Autowired
	private RezervacijaVozilaRepository rezervacijaVozilaRepository;

	public RezervacijaVozila save(RezervacijaVozila rezervacijaVozila) {
		return rezervacijaVozilaRepository.save(rezervacijaVozila);
	}
	
	public void insertRezervacijaVozila(Long rezervacija_id, Long vozilo_id) {
		rezervacijaVozilaRepository.insertRezervacijaVozila(rezervacija_id, vozilo_id);
	}
}
