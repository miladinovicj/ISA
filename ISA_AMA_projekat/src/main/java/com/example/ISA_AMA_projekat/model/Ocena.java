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
	@GeneratedValue(strategy = GenerationType.AUTO)
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
}
