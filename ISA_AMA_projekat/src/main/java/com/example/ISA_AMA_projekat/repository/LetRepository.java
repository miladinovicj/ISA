package com.example.ISA_AMA_projekat.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.ISA_AMA_projekat.model.Let;

public interface LetRepository extends JpaRepository<Let,Integer>
{
	Optional<Let> findById(Integer id);
}
