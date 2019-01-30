package com.example.ISA_AMA_projekat.model;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;

@Entity
public class RentacarServis {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column
	private Integer id;
	
	@Column(nullable = false)
	private String naziv;
	
	@OneToOne
	private Adresa adresa;
	
	@Column
	private String promotivni_opis;
	
	@OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	@JoinColumn(name = "rentacar_id", referencedColumnName = "id")
	@JsonManagedReference
	private Set<Filijala> filijale;
	
	
	@ManyToMany
	private Set<Usluga> usluge = new HashSet<Usluga>();
	
	@Column
	private double prosecna_ocena;
	
	@OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	private Set<Rating> ocene = new HashSet<Rating>();
	
	
	
	public RentacarServis() {
		super();
	}
	
	public RentacarServis(Integer id, String naziv, String adresa, String broj, String grad, String opis) {
	
		this.id=id;
		this.naziv=naziv;
		this.adresa = new Adresa();
		this.adresa.setUlica(adresa);
		this.adresa.setBroj(broj);
		this.adresa.getGrad().setNaziv(grad);
		this.promotivni_opis=opis;
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

	public String getPromotivni_opis() {
		return promotivni_opis;
	}

	public void setPromotivni_opis(String promotivni_opis) {
		this.promotivni_opis = promotivni_opis;
	}

	public double getProsecna_ocena() {
		return prosecna_ocena;
	}

	public void setProsecna_ocena(double prosecna_ocena) {
		this.prosecna_ocena = prosecna_ocena;
	}

	public Set<Usluga> getUsluge() {
		return usluge;
	}

	public void setUsluge(Set<Usluga> usluge) {
		this.usluge = usluge;
	}
	

	public Set<Filijala> getFilijale() {
		return filijale;
	}

	public void setFilijale(Set<Filijala> filijale) {
		this.filijale = filijale;
	}

	public Set<Rating> getOcene() {
		return ocene;
	}

	public void setOcene(Set<Rating> ocene) {
		this.ocene = ocene;
	}
	@Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        RentacarServis s = (RentacarServis) o;
        if(s.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, s.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

	@Override
	public String toString() {
		return "Rent-a-car servis [id=" + id + ", naziv=" + naziv + ", adresa="
				+ adresa + ", promotivni opis=" + promotivni_opis + "]";
	}
}
	
	

