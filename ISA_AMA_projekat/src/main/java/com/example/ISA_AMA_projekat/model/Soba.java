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
public class Soba {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
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
	
}
