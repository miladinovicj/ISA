package com.example.ISA_AMA_projekat.repository;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.ISA_AMA_projekat.model.Rezervacija;

public interface RezervacijaRepository extends JpaRepository<Rezervacija, Integer>
{
	public Rezervacija save(Rezervacija rez);
	
	public Optional<Rezervacija> findById(Integer id);
}
