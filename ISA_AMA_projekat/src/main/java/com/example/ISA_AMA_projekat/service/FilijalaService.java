package com.example.ISA_AMA_projekat.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.ISA_AMA_projekat.model.Filijala;
import com.example.ISA_AMA_projekat.model.RentacarServis;
import com.example.ISA_AMA_projekat.repository.FilijalaRepository;

@Service
public class FilijalaService {

	@Autowired
	private FilijalaRepository filijalaRepository;
	
	public Optional<Filijala> findById(Integer id)
	{
		return filijalaRepository.findById(id);
	}
	
	public void updateFilijala(Integer adresa_id, Integer id)
	{
		filijalaRepository.updateFilijala(adresa_id, id);
	}
	
	public Filijala save(Filijala fil){
		return filijalaRepository.save(fil);
	}
	
	public void updateRent(Integer idr, Integer id)
	{
		filijalaRepository.updateRentacarFil(idr, id);
	}
}
