package com.example.ISA_AMA_projekat.service;

import java.util.List;

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
	
	public void insertRezervacijaVozila(Integer rezervacija_id, Integer vozilo_id) {
		rezervacijaVozilaRepository.insertRezervacijaVozila(rezervacija_id, vozilo_id);
	}
	
	public List<RezervacijaVozila> getAll()
	{
		return rezervacijaVozilaRepository.findAll();
	}
}
