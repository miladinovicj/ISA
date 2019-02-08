package com.example.ISA_AMA_projekat.service;

import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.ISA_AMA_projekat.model.RezervacijaHotel;
import com.example.ISA_AMA_projekat.repository.RezervacijaHotelRepository;

@Service
public class RezervacijaHotelService {

	@Autowired
	private RezervacijaHotelRepository rezervacijaHotelRepository;
	
	public RezervacijaHotel save(RezervacijaHotel rezervacijaHotel) {
		return rezervacijaHotelRepository.save(rezervacijaHotel);
	}
	
	public void updateUsluga(Integer id_usluga, Integer id_rezervacijaHotel) {
		rezervacijaHotelRepository.updateUsluga(id_usluga, id_rezervacijaHotel);
	}
	
	public void removeUsluga(Integer id_usluga, Integer id_rezervacijaHotel) {
		rezervacijaHotelRepository.removeUsluga(id_usluga, id_rezervacijaHotel);
	}
	
	public void updateSoba(Integer id_soba, boolean aktivirana, double cena_rez, Integer id_rezervacijaHotel) {
		rezervacijaHotelRepository.updateSoba(id_soba, aktivirana, cena_rez, id_rezervacijaHotel);
	}
	
	public Optional<RezervacijaHotel> findById(Integer id) {
		return rezervacijaHotelRepository.findById(id);
	}
	
	public Collection<RezervacijaHotel> findBrzeRez(Integer id, boolean brza) {
		return rezervacijaHotelRepository.findBrzeRez(id, brza);
	}
	
	public void updateAktivirana(Integer id, boolean aktivirana) {
		rezervacijaHotelRepository.updateAktivirana(id, aktivirana);
	}
	
	public void insertRezervacijaSoba(Integer rezervacija_id, Integer soba_id) {
		rezervacijaHotelRepository.insertRezervacijaSoba(rezervacija_id, soba_id);
	}
	
	public List<RezervacijaHotel> getAll(){
		return rezervacijaHotelRepository.findAll();
	}
	
	public void updateDatumRez(Date datum_rez, Integer id){
		rezervacijaHotelRepository.updateDatumRez(datum_rez, id);
	}
	
	public void deleteRezH(RezervacijaHotel rh)
	{
		rezervacijaHotelRepository.delete(rh);
	}
	
	public void deleteRezH2(Integer id)
	{
		rezervacijaHotelRepository.deleteRezH(id);
	}
	
	public void deleteSobaRez(Integer id)
	{
		rezervacijaHotelRepository.deleteSobaRez(id);
	}
	
	public void deleteRezHotelUsluge(Integer id)
	{
		rezervacijaHotelRepository.deleteRezHotelUsluge(id);
	}
}
