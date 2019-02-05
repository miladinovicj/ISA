package com.example.ISA_AMA_projekat.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.ISA_AMA_projekat.model.Bonus;
import com.example.ISA_AMA_projekat.repository.BonusRepository;

@Service
public class BonusService {
	
	@Autowired
	private BonusRepository bonusRepository;
	
	public Bonus save(Bonus bonus) {
		return bonusRepository.save(bonus);
	}
	
	public Bonus findByPoeni(int poeni)
	{
		return bonusRepository.findOneByBonusPoeni(poeni);
	}

}
