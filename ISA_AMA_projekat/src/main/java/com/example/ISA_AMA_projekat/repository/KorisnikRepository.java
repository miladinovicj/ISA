package com.example.ISA_AMA_projekat.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;


import com.example.ISA_AMA_projekat.model.Korisnik;


public interface KorisnikRepository extends JpaRepository<Korisnik, Long>{
	
	Korisnik findOneByEmail(String email);
	
	Page<Korisnik> findAll(Pageable pageable);

}
