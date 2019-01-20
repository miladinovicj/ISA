package com.example.ISA_AMA_projekat.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.ISA_AMA_projekat.model.Filijala;
import com.example.ISA_AMA_projekat.repository.FilijalaRepository;

@Service
public class FilijalaService {

	@Autowired
	private FilijalaRepository filijalaRepository;
	
	public Optional<Filijala> findById(Long id)
	{
		return filijalaRepository.findById(id);
	}
}
