package com.example.ISA_AMA_projekat.model;

import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;

@Entity
public class RezervacijaVozila {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	
	@ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	@JoinColumn(name = "servis_id")
	private RentacarServis servis; //ovde bi trebalo samo id da bude u tabeli?
	
	@OneToOne(mappedBy = "rezervacija", cascade = CascadeType.ALL, 
            fetch = FetchType.LAZY)
	@JoinColumn(name = "vozilo_id")
	private Vozilo vozilo; //nisam sigurna da li je ovo dobro
	
	@ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	@JoinColumn(name = "korisnik_id")
	private Korisnik korisnik;
	
	@Column
	private Date datum_preuzimanja;
	
	@Column
	private Date datum_vracanja;
	
	@Column
	private String mesto_preuzimanja;
	
	@Column
	private String mesto_vracanja;
	
	@Column
	private double ukupna_cena;
	
	@Column
	private int broj_putnika;
	
	@Column
	private boolean brza;

	public RezervacijaVozila() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	

}
