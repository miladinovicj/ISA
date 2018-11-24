package com.example.ISA_AMA_projekat.model;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

//ova klasa opisuje registrovanog korisnika

@Entity
public class Korisnik {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	
	@Column
	private String ime;
	
	@Column
	private String prezime;
	
	@Column
	private String email;
	
	@Column
	private String lozinka;
	
	@Column
	private String grad;
	
	@Column
	private int broj_telefona; // mozda ovo i ne treba da bude int, ali sam za sada ostavila tako
	
	@OneToMany(mappedBy = "korisnik", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	private Set<RezervacijaVozila> rezervacije_vozila = new HashSet<RezervacijaVozila>();

	public Korisnik() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
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

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getLozinka() {
		return lozinka;
	}

	public void setLozinka(String lozinka) {
		this.lozinka = lozinka;
	}

	public String getGrad() {
		return grad;
	}

	public void setGrad(String grad) {
		this.grad = grad;
	}

	public int getBroj_telefona() {
		return broj_telefona;
	}

	public void setBroj_telefona(int broj_telefona) {
		this.broj_telefona = broj_telefona;
	}
	
	
}
