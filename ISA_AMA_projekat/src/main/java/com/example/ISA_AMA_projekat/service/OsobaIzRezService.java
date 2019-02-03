package com.example.ISA_AMA_projekat.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.ISA_AMA_projekat.model.OsobaIzRez;
import com.example.ISA_AMA_projekat.repository.OsobaIzRezRepository;

@Service
public class OsobaIzRezService 
{
	@Autowired
	private OsobaIzRezRepository osobaIzRezRepository;
	
	public OsobaIzRez save(OsobaIzRez o)
	{
		return osobaIzRezRepository.save(o);
	}
}
