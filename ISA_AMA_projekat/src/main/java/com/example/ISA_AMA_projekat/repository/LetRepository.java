package com.example.ISA_AMA_projekat.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import com.example.ISA_AMA_projekat.model.Let;

public interface LetRepository extends JpaRepository<Let,Integer>
{
	Optional<Let> findById(Integer id);
	
	public Let save(Let let);
	
	@Modifying
	@Transactional
	@Query(value = "delete from zauzeta_sedista where zauzeta_sedista.let_id=?1 and zauzeta_sedista.zauzeta_sedista=?2", nativeQuery = true)
	void deleteZauzetoSediste(Integer let_id, int sediste);
	
	
	@Modifying
	@Transactional
	@Query(value = "update let set let.prosecna_ocena=?1 where let.id=?2", nativeQuery = true)
	public void updateProsecna(double prosecna_ocena, Integer let_id);
	
}
