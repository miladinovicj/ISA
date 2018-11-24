package com.example.ISA_AMA_projekat.model;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;



@Entity
public class Vozilo {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	
	@Column
	private String naziv;
	
	@Column
	private String marka;
	
	@Column
	private String model;
	
	@Column
	private int godina_proizvodnje;
	
	@Column
	private int broj_sedista;
	
	@Column
	private String tip;
	
	@Column
	private double cena_dan;
	
	@Column
	private double prosecna_ocena;
	
	@ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	private RentacarServis rentacar;
	
	@OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "rezervacija_id", referencedColumnName="id")
    private RezervacijaVozila rezervacija;

	public Vozilo() {
		super();
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getNaziv() {
		return naziv;
	}

	public void setNaziv(String naziv) {
		this.naziv = naziv;
	}

	public String getMarka() {
		return marka;
	}

	public void setMarka(String marka) {
		this.marka = marka;
	}

	public String getModel() {
		return model;
	}

	public void setModel(String model) {
		this.model = model;
	}

	public int getGodina_proizvodnje() {
		return godina_proizvodnje;
	}

	public void setGodina_proizvodnje(int godina_proizvodnje) {
		this.godina_proizvodnje = godina_proizvodnje;
	}

	public int getBroj_sedista() {
		return broj_sedista;
	}

	public void setBroj_sedista(int broj_sedista) {
		this.broj_sedista = broj_sedista;
	}

	public String getTip() {
		return tip;
	}

	public void setTip(String tip) {
		this.tip = tip;
	}

	public double getCena_dan() {
		return cena_dan;
	}

	public void setCena_dan(double cena_dan) {
		this.cena_dan = cena_dan;
	}

	public double getProsecna_ocena() {
		return prosecna_ocena;
	}

	public void setProsecna_ocena(double prosecna_ocena) {
		this.prosecna_ocena = prosecna_ocena;
	}

	public RentacarServis getRentacar() {
		return rentacar;
	}

	public void setRentacar(RentacarServis rentacar) {
		this.rentacar = rentacar;
	}
	
	
	
	

}
