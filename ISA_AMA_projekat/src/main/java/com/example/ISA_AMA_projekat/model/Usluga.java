package com.example.ISA_AMA_projekat.model;

import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;


@Entity
public class Usluga {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	
	@Column(nullable = false)
	private String naziv;
	
	@Column(nullable = false)
	private double cena;
	
	/*
	@ManyToMany
	@JoinTable(name = "usluge_hotela",
    joinColumns = @JoinColumn(name="usluga_id", referencedColumnName="id"),
    inverseJoinColumns = @JoinColumn(name="hotel_id", referencedColumnName="id"))
	private Set<Hotel> hoteli = new HashSet<Hotel>();
	
	@ManyToMany
	@JoinTable(name = "usluge_servis",
    joinColumns = @JoinColumn(name="usluga_id", referencedColumnName="id"),
    inverseJoinColumns = @JoinColumn(name="servis_id", referencedColumnName="id"))
	private Set<RentacarServis> servisi = new HashSet<RentacarServis>();
	*/
	
	public Usluga() {
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

	public double getCena() {
		return cena;
	}

	public void setCena(double cena) {
		this.cena = cena;
	}

	/*
	public Set<Hotel> getHoteli() {
		return hoteli;
	}

	public void setHoteli(Set<Hotel> hoteli) {
		this.hoteli = hoteli;
	}
	
	public Set<RentacarServis> getServisi() {
		return servisi;
	}

	public void setServisi(Set<RentacarServis> servisi) {
		this.servisi = servisi;
	}
	*/

	@Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Usluga u = (Usluga) o;
        if(u.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, u.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

	@Override
	public String toString() {
		return "Usluga [id=" + id + ", naziv=" + naziv + ", cena="
				+ cena + "]";
	}
}
