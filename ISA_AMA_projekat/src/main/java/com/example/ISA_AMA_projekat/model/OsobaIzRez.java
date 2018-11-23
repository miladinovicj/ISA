package com.example.ISA_AMA_projekat.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity
public class OsobaIzRez implements Serializable{

	private static final long serialVersionUID = 7632373016632740844L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(unique=true, nullable=false)
	private Integer id;
	
	
	@Column(nullable = false)
	private String email;
	
	@Column(nullable = false)
	private String ime;
	
	@Column(nullable = false)
	private String prezime;
	
	@Column(nullable = false)
	private String brojPasosa;
	
	@Column(nullable = false)
	private int sediste;
	
	@Column(nullable = false)
	private boolean potvrdjeno;
	
	
	//SLOZENI ATRIBUTI:
	
	
	//FOREIGN KEY:
	
	@ManyToOne
	@JoinColumn(name="rezervacija_id", referencedColumnName="id", nullable=false)
	private Rezervacija rezervacija;
	
	@ManyToOne
	@JoinColumn(name="korisnik_id", referencedColumnName="id", nullable=true)
	private Korisnik korisnik;
	
	//GET & SET:
	

}
