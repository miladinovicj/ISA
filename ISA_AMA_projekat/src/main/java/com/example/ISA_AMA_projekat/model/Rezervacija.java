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
import javax.persistence.Entity;

@Entity
public class Rezervacija implements Serializable{

	
	private static final long serialVersionUID = -2326182133956304612L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(unique=true, nullable=false)
	private Integer id;


	@Column(nullable = false)
	private boolean brza;
	
	@Column(nullable = false)
	private Date datumRezervacije;
	
	@Column(nullable = false)
	private double cena;
	
	
	//SLOZENI ATRIBUTI:
	
	@OneToMany(cascade={ALL}, fetch=LAZY, mappedBy="rezervacija")
	private List<OsobaIzRez> osobe = new ArrayList<OsobaIzRez>();	
	
	@OneToMany(cascade={ALL}, fetch=LAZY, mappedBy="rezervacija")
	private List<Poziv> pozivi = new ArrayList<Poziv>();	
	
	
	//FOREIGN KEY:
	
	@ManyToOne
	@JoinColumn(name="let_id", referencedColumnName="id", nullable=false)
	private Let let;
	
	@ManyToOne
	@JoinColumn(name="korisnik_id", referencedColumnName="id", nullable=false)
	private Korisnik korisnik;

	/*
	@ManyToOne
	@JoinColumn(name="korisnik_id", referencedColumnName="id", nullable=false)
	private Korisnik korisnik;
	
	@ManyToOne
	@JoinColumn(name="hotel_rez_id", referencedColumnName="id", nullable=false)
	private Hotel_rez asd;

	@ManyToOne
	@JoinColumn(name="auto_rez_id", referencedColumnName="id", nullable=false)
	private Auto_rez asd;
	*/
	
	
	//GET & SET:
}
