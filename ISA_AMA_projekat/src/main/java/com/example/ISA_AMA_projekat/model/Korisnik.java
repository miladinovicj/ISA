package com.example.ISA_AMA_projekat.model;

import static javax.persistence.CascadeType.ALL;
import static javax.persistence.FetchType.LAZY;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
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


	@OneToMany(cascade={ALL}, fetch=LAZY, mappedBy="korisnik")
	private List<Rezervacija> rezervacije = new ArrayList<Rezervacija>();

	@OneToMany(cascade={ALL}, fetch=LAZY, mappedBy="korisnik")
	private List<OsobaIzRez> rezervacijeUcestvovanja = new ArrayList<OsobaIzRez>();
	
	@OneToMany(cascade={ALL}, fetch=LAZY, mappedBy="korisnik")
	private List<Poziv> poziviZaRezervacije = new ArrayList<Poziv>();
	
	@OneToMany(mappedBy="korisnik", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	private Set<Ocena> ocene = new HashSet<Ocena>();
	
	@OneToMany(mappedBy = "korisnik", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	private Set<RezervacijaVozila> rezervacije_vozila = new HashSet<RezervacijaVozila>();	//ovo isto msm da mozda ne treba(Milica)
	
	//lista prijatelja
	@ManyToMany
	@JoinTable(joinColumns=@JoinColumn(name="personId"),inverseJoinColumns=@JoinColumn(name="friendId"))
	private List<Korisnik> prijatelji = new ArrayList<Korisnik>();

	//lista korisnika kojima sam poslao zahtev za prijateljstvo
	@ManyToMany
	@JoinTable(joinColumns=@JoinColumn(name="odId"),inverseJoinColumns=@JoinColumn(name="zaId"))
	private List<Korisnik> zahteviPrijateljstva = new ArrayList<Korisnik>();
	
	/*
	@OneToMany(cascade={ALL}, fetch=LAZY)
	private List<Zahtev> listaPrijatelja = new ArrayList<Zahtev>();

	@OneToMany(cascade={ALL}, fetch=LAZY)
	private List<Poziv> listaPrijatelja = new ArrayList<Poziv>();
	*/
	
	//FOREIGN KEY:
	
	
	@ManyToMany
	@JoinTable(joinColumns=@JoinColumn(name="friendId"), inverseJoinColumns=@JoinColumn(name="personId"))
	private List<Korisnik> prijatelj_od;
	
	//lista korisnika koji cije friend req imam 
	@ManyToMany
	@JoinTable(joinColumns=@JoinColumn(name="zaId"),inverseJoinColumns=@JoinColumn(name="odId"))
	private List<Korisnik> primljeniZahteviPrijateljstva = new ArrayList<Korisnik>();
	
	//GET & SET:
	
	
	
}
