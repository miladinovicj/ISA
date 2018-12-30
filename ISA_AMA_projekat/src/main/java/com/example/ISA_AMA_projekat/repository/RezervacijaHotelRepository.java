package com.example.ISA_AMA_projekat.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import com.example.ISA_AMA_projekat.model.RezervacijaHotel;

public interface RezervacijaHotelRepository extends JpaRepository<RezervacijaHotel, Integer>{

	@Modifying
	@Transactional
	@Query(value = "insert into rezervacija_hotel_usluge (rezervacija_hotel_id, usluge_id) VALUES (?2, ?1)", nativeQuery = true)
	void updateUsluga(Integer id_usluga, Integer id_rezervacijaHotel);
	
	@Modifying
	@Transactional
	@Query(value = "delete from rezervacija_hotel_usluge where rezervacija_hotel_id = ?2 and usluge_id = ?1", nativeQuery = true)
	void removeUsluga(Integer id_usluga, Integer id_rezervacijaHotel);
	
	@Modifying
	@Transactional
	@Query(value = "update rezervacija_hotel rez set rez.soba_id = ?1, rez.aktivirana = ?2, rez.ukupna_cena = ?3 where rez.id = ?4", nativeQuery = true)
	void updateSoba(Integer id_soba, boolean aktivirana, double cena_rez, Integer id_rezervacijaHotel);
}
