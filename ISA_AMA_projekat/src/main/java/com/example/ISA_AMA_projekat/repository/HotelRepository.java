package com.example.ISA_AMA_projekat.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.ISA_AMA_projekat.model.Hotel;

public interface HotelRepository extends JpaRepository<Hotel, Long>{

	Hotel findOneByNaziv(String naziv);
}
