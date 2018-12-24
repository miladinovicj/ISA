package com.example.ISA_AMA_projekat.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.ISA_AMA_projekat.model.RentacarServis;

public interface RentacarRepository extends JpaRepository<RentacarServis, Long>{

		RentacarServis findOneByNaziv(String naziv);
}
