package com.example.ISA_AMA_projekat.model;

import static javax.persistence.CascadeType.ALL;
import static javax.persistence.FetchType.LAZY;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

@Entity
public class Korisnik implements Serializable 
{

	private static final long serialVersionUID = 7284235902041908178L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(unique=true, nullable=false)
	private Integer id;
	
	@Column(nullable = false)
	private String email;
	
	@Column(nullable = false)
	private String username;
	
	@Column(nullable = false)
	private String lozinka;
	
	@Column(nullable = false)
	private String ime;
	
	@Column(nullable = false)
	private String prezime;
	
	@Column(nullable = false)
	private String grad;
	
	@Column(nullable = false)
	private String telefon;
	
	//SLOZENI ATRIBUTI:

	@OneToMany(cascade={ALL}, fetch=LAZY)
	private List<Korisnik> listaPrijatelja = new ArrayList<Korisnik>();

	@OneToMany(cascade={ALL}, fetch=LAZY, mappedBy="korisnik")
	private List<Rezervacija> rezervacije = new ArrayList<Rezervacija>();

	@OneToMany(cascade={ALL}, fetch=LAZY, mappedBy="korisnik")
	private List<OsobaIzRez> rezervacijeUcestvovanja = new ArrayList<OsobaIzRez>();
	
	
	/*
	@OneToMany(cascade={ALL}, fetch=LAZY)
	private List<Zahtev> listaPrijatelja = new ArrayList<Zahtev>();

	@OneToMany(cascade={ALL}, fetch=LAZY)
	private List<Poziv> listaPrijatelja = new ArrayList<Poziv>();
	*/
	
	//FOREIGN KEY:
	
	@ManyToOne
	@JoinColumn(name="prijatelj_id", referencedColumnName="id", nullable=false)
	private Korisnik prijatelj;
	
	//GET & SET:
	
	
	
}
