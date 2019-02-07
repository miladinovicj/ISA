package com.example.ISA_AMA_projekat.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.ISA_AMA_projekat.model.Vozilo;
import com.example.ISA_AMA_projekat.repository.VoziloRepository;

@Service
public class VoziloService {
	
	@Autowired
	VoziloRepository voziloRepository;
	
	public Optional<Vozilo> findById(Integer id){
		return voziloRepository.findById(id);
	}
	
	public void updateVozilo(String naziv, String marka, String model, int godina_proizvodnje, int broj_sedista, String tip, double cena_dan,  Integer id)
	{
		 voziloRepository.updateVozilo(naziv, marka, model, godina_proizvodnje, broj_sedista, tip, cena_dan, id);
	}
	
	public Vozilo save(Vozilo vozilo){
		return voziloRepository.save(vozilo);
	}
	
	public void updateFil(Integer filijala, Integer id)
	{
		voziloRepository.updateFilVozilo(filijala, id);
	}
	
	public void deleteVozilo(Vozilo vozilo)
	{
		voziloRepository.delete(vozilo);
	}
	
	
	public void updateProsecnaVozilo(double prosecna_ocena, Integer vozilo_id)
	{
		voziloRepository.updateProsecnaVozilo(prosecna_ocena, vozilo_id);
	}
	
	public Vozilo postoji(String marka, String naziv, String model, int godina_proizvodnje, Integer filijala)
	{
		return voziloRepository.postoji(marka, naziv, model, godina_proizvodnje, filijala);
	}

}
