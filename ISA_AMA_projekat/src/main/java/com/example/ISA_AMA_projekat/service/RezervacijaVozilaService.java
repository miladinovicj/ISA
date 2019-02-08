package com.example.ISA_AMA_projekat.service;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.ISA_AMA_projekat.model.Hotel;
import com.example.ISA_AMA_projekat.model.RezervacijaVozila;
import com.example.ISA_AMA_projekat.repository.RezervacijaVozilaRepository;

@Service
public class RezervacijaVozilaService {
	
	@Autowired
	private RezervacijaVozilaRepository rezervacijaVozilaRepository;

	@Transactional
	public RezervacijaVozila save(RezervacijaVozila rezervacijaVozila) {
		return rezervacijaVozilaRepository.save(rezervacijaVozila);
	}
	
	@Transactional
	public void insertRezervacijaVozila(Integer rezervacija_id, Integer vozilo_id) {
		rezervacijaVozilaRepository.insertRezervacijaVozila(rezervacija_id, vozilo_id);
	}
	
	public List<RezervacijaVozila> getAll()
	{
		return rezervacijaVozilaRepository.findAll();
	}
	
	@Transactional
	public void updateDatumRez(Date datum_rez, Integer id)
	{
		rezervacijaVozilaRepository.updateDatumRez(datum_rez, id);
	}
	
	public Optional<RezervacijaVozila> findById(Integer id){
		return rezervacijaVozilaRepository.findById(id);
	}
	
	@Transactional
	public void deleteRezV(RezervacijaVozila rv)
	{
		rezervacijaVozilaRepository.delete(rv);
	}
	
	@Transactional
	public void deleteRezV2(Integer id)
	{
		rezervacijaVozilaRepository.deleteRezV(id);
	}
	
	@Transactional
	public void deleteVoziloRez(Integer id)
	{
		rezervacijaVozilaRepository.deleteVoziloRez(id);
	}
	
	@Transactional
	public void updateAktivirana(Integer id, boolean aktivirana)
	{
		rezervacijaVozilaRepository.updateAktivirana(id, aktivirana);
	}
}
