package com.example.ISA_AMA_projekat.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.ISA_AMA_projekat.model.Soba;
import com.example.ISA_AMA_projekat.repository.SobaRepository;

@Service
public class SobaService {

	@Autowired
	SobaRepository sobaRepository;
	
	public Optional<Soba> findById(Integer id){
		return sobaRepository.findById(id);
	}
}