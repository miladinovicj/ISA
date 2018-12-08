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
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;


@Entity
@Table(uniqueConstraints={@UniqueConstraint(columnNames={"naziv"})})
public class Hotel {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	
	@Column(nullable = false)
	private String naziv;
	
	@Column(nullable = false)
	private String adresa;
	
	@Column
	private String promotivni_opis;
	
	@Column
	private double prosecna_ocena;
	
	@ManyToMany
	private Set<Usluga> usluge = new HashSet<Usluga>();
	
	@OneToMany(mappedBy = "hotel", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	private Set<Soba> sobe = new HashSet<Soba>();
	
	@OneToMany(mappedBy = "hotel", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	private Set<RezervacijaHotel> rezervacije_hotela = new HashSet<RezervacijaHotel>();
	
	@OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	private Set<Rating> ocene = new HashSet<Rating>();
	
	public Hotel() {
		super();
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
	
	public Set<Soba> getSobe() {
		return sobe;
	}

	public void setSobe(Set<Soba> sobe) {
		this.sobe = sobe;
	}

	public Set<RezervacijaHotel> getRezervacije_hotela() {
		return rezervacije_hotela;
	}

	public void setRezervacije_hotela(Set<RezervacijaHotel> rezervacije_hotela) {
		this.rezervacije_hotela = rezervacije_hotela;
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
