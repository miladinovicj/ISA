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
	
	@Column
	private int bonus_poeni;
	
	/*
	@OneToMany(cascade={ALL}, fetch=LAZY)
	private List<Zahtev> listaPrijatelja = new ArrayList<Zahtev>();

	@OneToMany(cascade={ALL}, fetch=LAZY)
	private List<Poziv> listaPrijatelja = new ArrayList<Poziv>();
	*/
	
	//FOREIGN KEY:
	
	
	public List<Poziv> getPoziviZaRezervacije() {
		return poziviZaRezervacije;
	}



	public void setPoziviZaRezervacije(List<Poziv> poziviZaRezervacije) {
		this.poziviZaRezervacije = poziviZaRezervacije;
	}



	public Set<Ocena> getOcene() {
		return ocene;
	}



	public void setOcene(Set<Ocena> ocene) {
		this.ocene = ocene;
	}



	public List<Korisnik> getPrijatelji() {
		return prijatelji;
	}



	public void setPrijatelji(List<Korisnik> prijatelji) {
		this.prijatelji = prijatelji;
	}



	public List<Korisnik> getZahteviPrijateljstva() {
		return zahteviPrijateljstva;
	}



	public void setZahteviPrijateljstva(List<Korisnik> zahteviPrijateljstva) {
		this.zahteviPrijateljstva = zahteviPrijateljstva;
	}



	public int getBonus_poeni() {
		return bonus_poeni;
	}



	public void setBonus_poeni(int bonus_poeni) {
		this.bonus_poeni = bonus_poeni;
	}



	public List<Korisnik> getPrijatelj_od() {
		return prijatelj_od;
	}



	public void setPrijatelj_od(List<Korisnik> prijatelj_od) {
		this.prijatelj_od = prijatelj_od;
	}



	public List<Korisnik> getPrimljeniZahteviPrijateljstva() {
		return primljeniZahteviPrijateljstva;
	}



	public void setPrimljeniZahteviPrijateljstva(
			List<Korisnik> primljeniZahteviPrijateljstva) {
		this.primljeniZahteviPrijateljstva = primljeniZahteviPrijateljstva;
	}



	@ManyToMany
	@JoinTable(joinColumns=@JoinColumn(name="friendId"), inverseJoinColumns=@JoinColumn(name="personId"))
	private List<Korisnik> prijatelj_od;
	
	//lista korisnika koji cije friend req imam 
	@ManyToMany
	@JoinTable(joinColumns=@JoinColumn(name="zaId"),inverseJoinColumns=@JoinColumn(name="odId"))
	private List<Korisnik> primljeniZahteviPrijateljstva = new ArrayList<Korisnik>();
	
	//GET & SET:
	@ManyToOne
	@JoinColumn(name="prijatelj_id", referencedColumnName="id", nullable=false)
	private Korisnik prijatelj;



	public Korisnik() {
		super();
		// TODO Auto-generated constructor stub
	}



	public Integer getId() {
		return id;
	}



	public void setId(Integer id) {
		this.id = id;
	}



	public String getEmail() {
		return email;
	}



	public void setEmail(String email) {
		this.email = email;
	}



	public String getUsername() {
		return username;
	}



	public void setUsername(String username) {
		this.username = username;
	}



	public String getLozinka() {
		return lozinka;
	}



	public void setLozinka(String lozinka) {
		this.lozinka = lozinka;
	}



	public String getIme() {
		return ime;
	}



	public void setIme(String ime) {
		this.ime = ime;
	}



	public String getPrezime() {
		return prezime;
	}



	public void setPrezime(String prezime) {
		this.prezime = prezime;
	}



	public String getGrad() {
		return grad;
	}



	public void setGrad(String grad) {
		this.grad = grad;
	}



	public String getTelefon() {
		return telefon;
	}



	public void setTelefon(String telefon) {
		this.telefon = telefon;
	}


	public List<Rezervacija> getRezervacije() {
		return rezervacije;
	}



	public void setRezervacije(List<Rezervacija> rezervacije) {
		this.rezervacije = rezervacije;
	}



	public List<OsobaIzRez> getRezervacijeUcestvovanja() {
		return rezervacijeUcestvovanja;
	}



	public void setRezervacijeUcestvovanja(List<OsobaIzRez> rezervacijeUcestvovanja) {
		this.rezervacijeUcestvovanja = rezervacijeUcestvovanja;
	}



	public Set<RezervacijaVozila> getRezervacije_vozila() {
		return rezervacije_vozila;
	}



	public void setRezervacije_vozila(Set<RezervacijaVozila> rezervacije_vozila) {
		this.rezervacije_vozila = rezervacije_vozila;
	}



	public Korisnik getPrijatelj() {
		return prijatelj;
	}



	public void setPrijatelj(Korisnik prijatelj) {
		this.prijatelj = prijatelj;
	}



	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	
	//GET & SET:
	
		
	
	
}
