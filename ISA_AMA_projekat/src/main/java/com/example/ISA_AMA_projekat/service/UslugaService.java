package com.example.ISA_AMA_projekat.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.ISA_AMA_projekat.model.Usluga;
import com.example.ISA_AMA_projekat.repository.UslugaRepository;

@Service
public class UslugaService {

	@Autowired
	private UslugaRepository uslugaRepository;
	
	public List<Usluga> findAll(){
		return uslugaRepository.findAll();
	}
	
	public Optional<Usluga> findById(Integer id) {
		return uslugaRepository.findById(id);
	}
	
	public void updateCena(double cena, Integer id)
	{
		uslugaRepository.updateUsluga(cena, id);
	}
}
