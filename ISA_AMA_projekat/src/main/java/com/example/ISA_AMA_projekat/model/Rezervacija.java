package com.example.ISA_AMA_projekat.model;

import static javax.persistence.CascadeType.ALL;
import static javax.persistence.FetchType.LAZY;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import javax.persistence.Entity;

@Entity
public class Rezervacija implements Serializable{

	
	private static final long serialVersionUID = -2326182133956304612L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(unique=true, nullable=false)
	private Integer id;


	@Column(nullable = false)
	private boolean brza;
	
	@Column(nullable = false)
	private Date datumRezervacije;
	
	@Column(nullable = false)
	private double cena;
	
	
	//SLOZENI ATRIBUTI:
	@JsonManagedReference
	@OneToMany(cascade={ALL}, fetch=LAZY, mappedBy="rezervacija")
	private List<OsobaIzRez> osobe = new ArrayList<OsobaIzRez>();	
	
	//FOREIGN KEY:
	
	
	@ManyToOne
	@JoinColumn(referencedColumnName="id", nullable=false)
	private Let let;
	
	@ManyToOne
	@JoinColumn(referencedColumnName="id", nullable=false)
	private Korisnik korisnik;

	
	@ManyToOne
	@JoinColumn( referencedColumnName="id", nullable=true)
	private RezervacijaHotel rezevacijaHotel;

	@ManyToOne
	@JoinColumn(referencedColumnName="id", nullable=true)
	private RezervacijaVozila rezervacijaVozila;
	
	
	
	public Rezervacija() {
		super();
	}
	
	//GET & SET:


	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public boolean isBrza() {
		return brza;
	}

	public void setBrza(boolean brza) {
		this.brza = brza;
	}

	public Date getDatumRezervacije() {
		return datumRezervacije;
	}

	public void setDatumRezervacije(Date datumRezervacije) {
		this.datumRezervacije = datumRezervacije;
	}

	public double getCena() {
		return cena;
	}

	public void setCena(double cena) {
		this.cena = cena;
	}

	public List<OsobaIzRez> getOsobe() {
		return osobe;
	}

	public void setOsobe(List<OsobaIzRez> osobe) {
		this.osobe = osobe;
	}

	public Let getLet() {
		return let;
	}

	public void setLet(Let let) {
		this.let = let;
	}

	public Korisnik getKorisnik() {
		return korisnik;
	}

	public void setKorisnik(Korisnik korisnik) {
		this.korisnik = korisnik;
	}

	public RezervacijaHotel getRezevacijaHotel() {
		return rezevacijaHotel;
	}

	public void setRezevacijaHotel(RezervacijaHotel rezevacijaHotel) {
		this.rezevacijaHotel = rezevacijaHotel;
	}

	public RezervacijaVozila getRezervacijaVozila() {
		return rezervacijaVozila;
	}

	public void setRezervacijaVozila(RezervacijaVozila rezervacijaVozila) {
		this.rezervacijaVozila = rezervacijaVozila;
	}
	
	
	
}
