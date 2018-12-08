package com.example.ISA_AMA_projekat.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.ISA_AMA_projekat.model.Aviokompanija;
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
}
