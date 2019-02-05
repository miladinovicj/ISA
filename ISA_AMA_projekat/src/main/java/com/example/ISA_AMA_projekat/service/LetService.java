package com.example.ISA_AMA_projekat.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.example.ISA_AMA_projekat.model.Let;

import com.example.ISA_AMA_projekat.repository.LetRepository;

@Service
public class LetService 
{
	@Autowired
	LetRepository letRepository;
	
	public Optional<Let> findById(Integer id)
	{
		return letRepository.findById(id);
	}
	
	public Let save(Let let)
	{
		return letRepository.save(let);
	}
	
	public void deleteZauzetoSediste(Integer let_id, int sediste)
	{
		letRepository.deleteZauzetoSediste(let_id, sediste);
	}
	
	public void updateProsecna(double prosecna_ocena, Integer let_id)
	{
		letRepository.updateProsecna(prosecna_ocena, let_id);
	}
}
