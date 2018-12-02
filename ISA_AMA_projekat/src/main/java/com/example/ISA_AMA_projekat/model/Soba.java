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
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

@Entity
public class Soba {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	
	@Column
	private double prosecna_ocena;
	
	@Column
	private double cena_nocenja;
	
	@Column
	private int broj_kreveta;
	
	@Column
	private boolean brza_soba;
	
	@Column
	private double popust;
	
	@ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	private Hotel hotel;
	
	@ManyToMany
	@JoinTable(name = "rezervacije_sobe",
    joinColumns = @JoinColumn(name="soba_id", referencedColumnName="id"),
    inverseJoinColumns = @JoinColumn(name="rezervacijaHotel_id", referencedColumnName="id"))
	private Set<RezervacijaHotel> rezervacije = new HashSet<RezervacijaHotel>();
	
	@OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	private Set<Ocena> ocene = new HashSet<Ocena>();
	
	public Soba() {
		super();
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public double getProsecna_ocena() {
		return prosecna_ocena;
	}

	public void setProsecna_ocena(double prosecna_ocena) {
		this.prosecna_ocena = prosecna_ocena;
	}

	public double getCena_nocenja() {
		return cena_nocenja;
	}

	public void setCena_nocenja(double cena_nocenja) {
		this.cena_nocenja = cena_nocenja;
	}

	public int getBroj_kreveta() {
		return broj_kreveta;
	}

	public void setBroj_kreveta(int broj_kreveta) {
		this.broj_kreveta = broj_kreveta;
	}

	public boolean isBrza_soba() {
		return brza_soba;
	}

	public void setBrza_soba(boolean brza_soba) {
		this.brza_soba = brza_soba;
	}

	public double getPopust() {
		return popust;
	}

	public void setPopust(double popust) {
		this.popust = popust;
	}

	public Hotel getHotel() {
		return hotel;
	}

	public void setHotel(Hotel hotel) {
		this.hotel = hotel;
	}

	public Set<RezervacijaHotel> getRezervacije() {
		return rezervacije;
	}

	public void setRezervacije(Set<RezervacijaHotel> rezervacije) {
		this.rezervacije = rezervacije;
	}

	public Set<Ocena> getOcene() {
		return ocene;
	}

	public void setOcene(Set<Ocena> ocene) {
		this.ocene = ocene;
	}
	
}
