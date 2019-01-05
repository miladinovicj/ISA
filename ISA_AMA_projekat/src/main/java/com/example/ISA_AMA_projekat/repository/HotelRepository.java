package com.example.ISA_AMA_projekat.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import com.example.ISA_AMA_projekat.model.Hotel;

public interface HotelRepository extends JpaRepository<Hotel, Integer>{

	Hotel findOneByNaziv(String naziv);
	
	@Modifying
	@Query("select hotel from Hotel hotel where hotel.adresa.grad.naziv like ?1 or hotel.adresa.ulica like ?1 or hotel.adresa.broj like ?1 or hotel.naziv like ?1")
	List<Hotel> search(String name_location);
}
