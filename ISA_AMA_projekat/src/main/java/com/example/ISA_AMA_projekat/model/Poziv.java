package com.example.ISA_AMA_projekat.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity
public class Poziv implements Serializable {

	private static final long serialVersionUID = 6901581624741089050L;

	@Column(nullable = false)
	private Date datum; 
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(unique=true, nullable=false)
	private Integer id;
	
	@ManyToOne
	@JoinColumn(name="rezervacija_id", referencedColumnName="id", nullable=false)
	private Rezervacija rezervacija;
	
	@ManyToOne
	@JoinColumn(name="osoba_id", referencedColumnName="id", nullable=false)
	private Korisnik korisnik;
	
	
}
