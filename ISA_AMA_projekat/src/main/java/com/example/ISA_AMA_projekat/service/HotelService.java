package com.example.ISA_AMA_projekat.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.ISA_AMA_projekat.model.Hotel;
import com.example.ISA_AMA_projekat.repository.HotelRepository;

@Service
public class HotelService {

	@Autowired
	private HotelRepository hotelRepository;
	
	public Hotel findByEmail(String naziv) {
		return hotelRepository.findOneByNaziv(naziv);
	}

	public List<Hotel> findAll() {
		return hotelRepository.findAll();
	}
	
	public Optional<Hotel> findById(Long id){
		return hotelRepository.findById(id);
	}
	
	public Hotel save(Hotel hotel){
		return hotelRepository.save(hotel);
	}
	
}
