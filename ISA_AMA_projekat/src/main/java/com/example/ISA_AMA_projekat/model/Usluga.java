package com.example.ISA_AMA_projekat.model;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;


@Entity
public class Usluga {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	
	@Column(nullable = false)
	private String naziv;
	
	@Column(nullable = false)
	private double cena;
	
	@ManyToMany
	@JoinTable(name = "usluge_hotela",
    joinColumns = @JoinColumn(name="usluga_id", referencedColumnName="id"),
    inverseJoinColumns = @JoinColumn(name="hotel_id", referencedColumnName="id"))
	private Set<Hotel> hoteli = new HashSet<Hotel>();
	
	public Usluga() {
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

	public double getCena() {
		return cena;
	}

	public void setCena(double cena) {
		this.cena = cena;
	}

	public Set<Hotel> getHoteli() {
		return hoteli;
	}

	public void setHoteli(Set<Hotel> hoteli) {
		this.hoteli = hoteli;
	}
	
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
