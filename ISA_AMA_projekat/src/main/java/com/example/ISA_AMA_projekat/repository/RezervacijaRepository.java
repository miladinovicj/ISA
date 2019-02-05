package com.example.ISA_AMA_projekat.repository;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import com.example.ISA_AMA_projekat.model.Rezervacija;

public interface RezervacijaRepository extends JpaRepository<Rezervacija, Integer>
{
	public Rezervacija save(Rezervacija rez);
	
	public Optional<Rezervacija> findById(Integer id);
	
	@Modifying
	@Transactional
	@Query(value = "update Rezervacija r set r.rezervacija_vozila_id = ?1 where r.id = ?2", nativeQuery = true)
	public void updateRezVozila(Integer id_rezVozila, Integer id_rez);
	
	@Modifying
	@Transactional
	@Query(value = "update Rezervacija r set r.rezevacija_hotel_id = ?1 where r.id = ?2", nativeQuery = true)
	public void updateRezHotel(Integer id_rezHotel, Integer id_rez);
	
	@Modifying
	@Transactional
	@Query(value = "update Rezervacija r set r.cena = ?1 where r.id = ?2", nativeQuery = true)
	public void updateCenaRez(double cena, Integer id_rez);
}
