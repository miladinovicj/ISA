package com.example.ISA_AMA_projekat.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.ISA_AMA_projekat.model.Rezervacija;
import com.example.ISA_AMA_projekat.repository.RezervacijaRepository;

@Service
public class RezervacijaService 
{
	@Autowired
	private RezervacijaRepository rezervacijaRepository;
	
	
	public Rezervacija save(Rezervacija rez)
	{
		return rezervacijaRepository.save(rez);
	}
	
	public Optional<Rezervacija> findById(Integer id)
	{
		Optional<Rezervacija> rez = rezervacijaRepository.findById(id);
		
		try
		{
			Rezervacija r = rez.get();
			System.out.println("ID rez: " + r.getId());
		}
		catch(Exception e)
		{
			System.err.println("* * * * * * * * Erorcina * * * * * * * ");
		}
		
		return rez;
	}
	
	public void updateRezVozila(Integer id_rezVozila, Integer id_rez)
	{
		rezervacijaRepository.updateRezVozila(id_rezVozila, id_rez);
	}
	
	public void updateRezHotel(Integer id_rezHotel, Integer id_rez){
		rezervacijaRepository.updateRezHotel(id_rezHotel, id_rez);
	}
	
	public void updateCenaRez(double cena, Integer id_rez){
		rezervacijaRepository.updateCenaRez(cena, id_rez);
	}
	
	public void deleteRez(Rezervacija rez)
	{
		rezervacijaRepository.delete(rez);
	}
	
	public void updateVoziloId(Integer id_rez)
	{
		rezervacijaRepository.updateVoziloId(id_rez);
	}
	
	
	public void updateHotelId(Integer id_rez)
	{
		rezervacijaRepository.updateHotelId(id_rez);
	}
	
	public void zavrsiRez(Integer id_rez)
	{
		rezervacijaRepository.zavrsiRez(id_rez);
	}
	
	
	public void obrisiOsobu(Integer osoba_id, Integer rez_id)
	{
		rezervacijaRepository.obrisiOsobu(osoba_id, rez_id);
	}
	
	public void potvrdiRez(Integer osoba_id, Integer rez_id)
	{
		rezervacijaRepository.potvrdiRez(osoba_id, rez_id);
	}
	
	public void updateCena(double cena, Integer id)
	{
		rezervacijaRepository.updateCena(cena, id);
	}
	
	
	
}
