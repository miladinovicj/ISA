package com.example.ISA_AMA_projekat.model;

import static javax.persistence.CascadeType.ALL;
import static javax.persistence.FetchType.LAZY;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CollectionTable;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.PrimaryKeyJoinColumn;

import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;

import javax.persistence.Entity;

@Entity
public class Let implements Serializable
{
	private static final long serialVersionUID = 2941904332108472041L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(unique=true, nullable=false)
	private Integer id;
	
	@Column(nullable = false)
	private double cena;
	
	@Column(nullable = false)
	private String odakle;
	
	@Column(nullable = false)
	private String dokle;
	
	@Column(nullable = false)
	private int vremePoletanja;
	
	@Column(nullable = false)
	private int trajanje;
	
	@Column(nullable = false)
	private int vremeSletanja;
	
	@Column(nullable = false)
	private double udaljenost; //u km
	
	@Column(nullable = true)
	private int popust; //u %
	

	
	
	//SLOZENI ATRIBUTI:

	@ElementCollection
	@CollectionTable(name ="presedanja")
	private List<String> presedanja = new ArrayList<String>();
	
	@OneToMany(cascade={ALL}, fetch=LAZY, mappedBy="let")
	private List<Rating> rejtinzi = new ArrayList<Rating>();

	@OneToMany(cascade={ALL}, fetch=LAZY, mappedBy="let")
	private List<Rezervacija> rezervacije = new ArrayList<Rezervacija>();

	//FOREIGN KEYS:
	
	@ManyToOne
	@JoinColumn(name="aviokompanija_id", referencedColumnName="id", nullable=false)
	private Aviokompanija aviokompanija;
	
	//GET & SET:
	
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public double getCena() {
		return cena;
	}

	public void setCena(double cena) {
		this.cena = cena;
	}

	public String getOdakle() {
		return odakle;
	}

	public void setOdakle(String odakle) {
		this.odakle = odakle;
	}

	public String getDokle() {
		return dokle;
	}

	public void setDokle(String dokle) {
		this.dokle = dokle;
	}

	public int getVremePoletanja() {
		return vremePoletanja;
	}

	public void setVremePoletanja(int vremePoletanja) {
		this.vremePoletanja = vremePoletanja;
	}

	public int getTrajanje() {
		return trajanje;
	}

	public void setTrajanje(int trajanje) {
		this.trajanje = trajanje;
	}

	public int getVremeSletanja() {
		return vremeSletanja;
	}

	public void setVremeSletanja(int vremeSletanja) {
		this.vremeSletanja = vremeSletanja;
	}

	public double getUdaljenost() {
		return udaljenost;
	}

	public void setUdaljenost(double udaljenost) {
		this.udaljenost = udaljenost;
	}

	public Aviokompanija getAviokompanija() {
		return aviokompanija;
	}

	public void setAviokompanija(Aviokompanija aviokompanija) {
		this.aviokompanija = aviokompanija;
	}

	public List<String> getPresedanja() {
		return presedanja;
	}

	public void setPresedanja(List<String> presedanja) {
		this.presedanja = presedanja;
	}

	public List<Rating> getRejtinzi() {
		return rejtinzi;
	}

	public void setRejtinzi(List<Rating> rejtinzi) {
		this.rejtinzi = rejtinzi;
	}
	
	
	public int getPopust() {
		return popust;
	}

	public void setPopust(int popust) {
		this.popust = popust;
	}

	
	
		
}
