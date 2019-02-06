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
	
	public Hotel findByNaziv(String naziv) {
		return hotelRepository.findOneByNaziv(naziv);
	}

	public List<Hotel> findAll() {
		return hotelRepository.findAll();
	}
	
	public Optional<Hotel> findById(Integer id){
		return hotelRepository.findById(id);
	}
	
	public Hotel save(Hotel hotel){
		return hotelRepository.save(hotel);
	}
	
	public List<Hotel> search(String name_location) {
		return hotelRepository.search(name_location);
	}
	
	public void updateAdmin(Integer hotelID, Integer adminID) {
		hotelRepository.updateAdmin(hotelID, adminID);
	}
	
	public void updateHotel(Integer id, String naziv, String opis, Integer adresa) {
		hotelRepository.updateHotel(id, naziv, opis, adresa);
	}
	
	public void updateProsecnaHotel(double prosecna, Integer hotel_id)
	{
		hotelRepository.updateProsecnaHotel(prosecna, hotel_id);
	}
}
