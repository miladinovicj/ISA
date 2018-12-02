package com.example.ISA_AMA_projekat.model;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

@Entity
public class Ocena {
	
	enum Ocenjen {
		AVIOKOMPANIJA, LET, HOTEL, SOBA, RENTACARSERVIS, VOZILO 
	}

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	
	@Column
	private int ocena;
	
	@Column
	private Ocenjen ocenjen;
	
	@Column
	private Integer id_ocenjen;
	
	@ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	private Korisnik korisnik;
	
	public Ocena() {
		super();
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public int getOcena() {
		return ocena;
	}

	public void setOcena(int ocena) {
		this.ocena = ocena;
	}

	public Ocenjen getOcenjen() {
		return ocenjen;
	}

	public void setOcenjen(Ocenjen ocenjen) {
		this.ocenjen = ocenjen;
	}

	public Integer getId_ocenjen() {
		return id_ocenjen;
	}

	public void setId_ocenjen(Integer id_ocenjen) {
		this.id_ocenjen = id_ocenjen;
	}

	public Korisnik getKorisnik() {
		return korisnik;
	}

	public void setKorisnik(Korisnik korisnik) {
		this.korisnik = korisnik;
	}
	
}
