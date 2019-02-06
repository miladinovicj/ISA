package com.example.ISA_AMA_projekat.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.ISA_AMA_projekat.model.Popust;
import com.example.ISA_AMA_projekat.repository.PopustRepository;

@Service
public class PopustService {

	@Autowired
	private PopustRepository popustRepozitory;
	
	public Popust save(Popust popust) {
		return popustRepozitory.save(popust);
	}
	
	public void updateSoba(Integer soba_id, Integer popust_id) {
		popustRepozitory.updateSoba(soba_id, popust_id);
	}
	
	public void updateUsluga(Integer popust_id, Integer usluga_id) {
		popustRepozitory.updateUsluga(popust_id, usluga_id);
	}
}
