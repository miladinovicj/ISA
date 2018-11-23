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
public class Rating implements Serializable
{

	private static final long serialVersionUID = 918061230652109647L;

	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(unique=true, nullable=false)
	private Integer id;
	
	
	@Column(nullable = false)
	private int ocena;
	
	@Column(nullable = false)
	private int id_korisnika;
	
	
	//FOREIGN KEYS:
	@ManyToOne
	@JoinColumn(name="aviokompanija_id", referencedColumnName="id", nullable=true)
	private Aviokompanija aviokompanija;

	@ManyToOne
	@JoinColumn(name="let_id", referencedColumnName="id", nullable=true)
	private Let let;
	
	/* x n
	@ManyToOne
	@JoinColumn(name="nesto_id", referencedColumnName="nesto_id", nullable=true)
	private Nesto nesto;
	*/

	//GET & SET:

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public int getOcena() {
		return ocena;
	}

	public void setOcena(int ocena) {
		this.ocena = ocena;
	}

	public int getId_korisnika() {
		return id_korisnika;
	}

	public void setId_korisnika(int id_korisnika) {
		this.id_korisnika = id_korisnika;
	}

	public Aviokompanija getAviokompanija() {
		return aviokompanija;
	}

	public void setAviokompanija(Aviokompanija aviokompanija) {
		this.aviokompanija = aviokompanija;
	}

	public Let getLet() {
		return let;
	}

	public void setLet(Let let) {
		this.let = let;
	}
	
	
	
	
	
}
