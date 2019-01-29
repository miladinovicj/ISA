package com.example.ISA_AMA_projekat.repository;

import org.springframework.data.jpa.repository.JpaRepository;



import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import com.example.ISA_AMA_projekat.model.Korisnik;


public interface KorisnikRepository extends JpaRepository<Korisnik, Integer>{
	
	Korisnik findOneByEmail(String email);
	
	@Modifying
	@Query("update Korisnik u set u.aktiviran = ?1 where u.id = ?2")
	public void updateAktiviran(boolean aktiviran,  Integer id);
	
}
