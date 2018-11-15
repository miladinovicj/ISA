package com.example.ISA_AMA_projekat.model;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;


@Entity
public class Hotel {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	
	@Column(unique = true, nullable = false)
	private String naziv;
	
	@Column(nullable = false)
	private String adresa;
	
	@Column
	private String promotivni_opis;
	
	@Column
	private double prosecna_ocena;
	
	@ManyToMany(mappedBy = "usluge")
	private Set<Usluga> usluge = new HashSet<Usluga>();
	
	public Hotel() {
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

	public String getAdresa() {
		return adresa;
	}

	public void setAdresa(String adresa) {
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
	
	@Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Hotel h = (Hotel) o;
        if(h.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, h.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

	@Override
	public String toString() {
		return "Hotel [id=" + id + ", naziv=" + naziv + ", adresa="
				+ adresa + ", promotivni opis=" + promotivni_opis + "]";
	}
}
