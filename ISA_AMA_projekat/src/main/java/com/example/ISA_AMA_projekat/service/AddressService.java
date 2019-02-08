package com.example.ISA_AMA_projekat.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.ISA_AMA_projekat.model.Adresa;
import com.example.ISA_AMA_projekat.repository.AddressRepository;

@Service
public class AddressService {
	
	@Autowired
	private AddressRepository addressRepozitory;
	
	public List<Adresa> gimmieAll() {
		return addressRepozitory.findAll();
	}
	
	
	public List<Adresa> checkAddress(Integer city_id, String street, String number) {
		return addressRepozitory.checkAddress(city_id, street, number);
	}
	
	public Adresa save(Adresa adresa) {
		return addressRepozitory.save(adresa);
	}
	
	public void updateAdresa(String ulica, String broj, Integer grad_id, Integer id)
	{
		addressRepozitory.updateAdresa(ulica, broj, grad_id, id);
	}
}
