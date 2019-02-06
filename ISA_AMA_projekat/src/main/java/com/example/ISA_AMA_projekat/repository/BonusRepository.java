package com.example.ISA_AMA_projekat.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.ISA_AMA_projekat.model.Bonus;

public interface BonusRepository extends JpaRepository<Bonus, Integer> {

	
	Bonus findOneByBonusPoeni(int poeni);
}
