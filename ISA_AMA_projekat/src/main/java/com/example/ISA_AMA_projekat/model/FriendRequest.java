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
public class FriendRequest implements Serializable 
{

	private enum Stanje {POTVRDJENO, CEKANJE} //mozda odbijeno..
	
	private static final long serialVersionUID = -2334626659068316911L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(unique=true, nullable=false)
	private Integer id;
	
	@Column(nullable = false)
	private Stanje stanje;
	
	
	//FOREGIN KEYS:
	
	@ManyToOne
	@JoinColumn(referencedColumnName="id", nullable=false)
	private Korisnik salje;
	
	@ManyToOne
	@JoinColumn(referencedColumnName="id", nullable=false)
	private Korisnik prima;

	
	
	
	public FriendRequest() {
		super();
	}

	//GET & SET:

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Stanje getStanje() {
		return stanje;
	}

	public void setStanje(Stanje stanje) {
		this.stanje = stanje;
	}

	public Korisnik getSalje() {
		return salje;
	}

	public void setSalje(Korisnik salje) {
		this.salje = salje;
	}

	public Korisnik getPrima() {
		return prima;
	}

	public void setPrima(Korisnik prima) {
		this.prima = prima;
	}
	

}

