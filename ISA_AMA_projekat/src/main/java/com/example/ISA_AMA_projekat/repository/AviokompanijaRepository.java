package com.example.ISA_AMA_projekat.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import com.example.ISA_AMA_projekat.model.Aviokompanija;

public interface AviokompanijaRepository extends JpaRepository<Aviokompanija, Integer>
{
	Aviokompanija findOneByNaziv(String naziv);
	
	@Modifying
	@Transactional
	@Query(value = "update aviokompanija avio set avio.id_admin = ?2 where avio.id = ?1", nativeQuery = true)
	public void updateAdmin(Integer avioID, Integer adminID);
}
