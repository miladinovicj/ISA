package com.example.ISA_AMA_projekat.repository;

import java.util.Date;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import com.example.ISA_AMA_projekat.model.RezervacijaVozila;

public interface RezervacijaVozilaRepository extends JpaRepository<RezervacijaVozila, Integer>{

	@Modifying
	@Transactional
	@Query(value = "insert into vozilo_rezervacije (rezervacije_id, vozilo_id) VALUES (?1, ?2)", nativeQuery = true)
	void insertRezervacijaVozila(Integer rezervacija_id, Integer vozilo_id);
	
	@Modifying
	@Transactional
	@Query("update RezervacijaVozila rv set rv.datum_rezervacije = ?1 where rv.id = ?2")
	public void updateDatumRez(Date datum_rez,  Integer id);
}
