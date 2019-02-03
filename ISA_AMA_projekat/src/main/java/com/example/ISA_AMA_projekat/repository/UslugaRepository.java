package com.example.ISA_AMA_projekat.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import com.example.ISA_AMA_projekat.model.Usluga;

public interface UslugaRepository extends JpaRepository<Usluga, Integer>{

	@Modifying
	@Transactional
	@Query("update Usluga u set u.cena = ?1 where u.id = ?2")
	public void updateUsluga(double cena,  Integer id);
	
	@Modifying
	@Transactional
	@Query(value = "insert into hotel_usluge (hotel_id, usluge_id) VALUES (?1, ?2)", nativeQuery = true)
	public void addToHotel(Integer id_hotel, Integer id_usluga);
}
