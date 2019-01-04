package com.example.ISA_AMA_projekat.model;

import static javax.persistence.CascadeType.ALL;
import static javax.persistence.FetchType.LAZY;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

import com.fasterxml.jackson.annotation.JsonManagedReference;

@Entity
public class Aviokompanija implements Serializable {
	
	private static final long serialVersionUID = 1L;

	
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(unique=true, nullable=false)
	private Integer id;
	
	@Column(unique = true, nullable = false)
	private String naziv;
	
	@Column(nullable = false)
	private String opis;
	
	@Column(nullable = true)
	private Double ocena;
	
	@Column(nullable = false)
	private String info;
	
	@Column(nullable = false)
	private int maxKapacitet;
	
	@OneToOne
	private Adresa adresa;
	
	//SLOZENI ATRIBUTI:
	@OneToMany(cascade={ALL}, fetch=LAZY, mappedBy="aviokompanija")
    @JsonManagedReference
	private List<Let> letovi = new ArrayList<Let>();
	 
	
	@OneToMany(cascade={ALL}, fetch=LAZY)
	private List<Rating> rejtinzi = new ArrayList<Rating>();
	
	
	@OneToMany(cascade={ALL}, fetch=LAZY)
	private List<Let> brziLetovi = new ArrayList<Let>();

	
	
	
	
	public Aviokompanija() {
		super();
	}


	//GET & SET:
	
	public List<Let> getBrziLetovi() {
		return brziLetovi;
	}


	public void setBrziLetovi(List<Let> brziLetovi) {
		this.brziLetovi = brziLetovi;
	}


	public Integer getId() {
		return id;
	}


	public void setId(Integer id) {
		this.id = id;
	}


	public String getNaziv() {
		return naziv;
	}


	public void setNaziv(String naziv) {
		this.naziv = naziv;
	}


	public Adresa getAdresa() {
		return adresa;
	}


	public void setAdresa(Adresa adresa) {
		this.adresa = adresa;
	}


	public String getOpis() {
		return opis;
	}


	public void setOpis(String opis) {
		this.opis = opis;
	}


	public Double getOcena() {
		return ocena;
	}


	public void setOcena(Double ocena) {
		this.ocena = ocena;
	}


	public String getInfo() {
		return info;
	}


	public void setInfo(String info) {
		this.info = info;
	}


	public int getMaxKapacitet() {
		return maxKapacitet;
	}


	public void setMaxKapacitet(int maxKapacitet) {
		this.maxKapacitet = maxKapacitet;
	}

	
	public List<Let> getLetovi() {
		return letovi;
	}


	public void setLetovi(List<Let> letovi) {
		this.letovi = letovi;
	}
	
	
	public List<Rating> getRejtinzi() {
		return rejtinzi;
	}


	public void setRejtinzi(List<Rating> rejtinzi) {
		this.rejtinzi = rejtinzi;
	}
	
	
}
