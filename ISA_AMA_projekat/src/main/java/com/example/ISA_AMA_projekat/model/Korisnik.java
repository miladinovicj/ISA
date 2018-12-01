package com.example.ISA_AMA_projekat.model;

import static javax.persistence.CascadeType.ALL;
import static javax.persistence.FetchType.LAZY;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

@Entity
public class Korisnik implements Serializable 
{

	private static final long serialVersionUID = 7284235902041908178L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(nullable = false)
	private Long id;
	
	@Column(nullable = false)
	private String email;
	
	@Column(nullable = false)
	private String lozinka;
	
	@Column(nullable = false)
	private String ime;
	
	@Column(nullable = false)
	private String prezime;
	
	@Column(nullable = false)
	private String grad;
	
	@Column(nullable = false)
	private String telefon;
	
	@Column(nullable = false)
	private int bonus_poeni;
	

	
	//SLOZENI ATRIBUTI:

	@OneToMany(cascade={ALL}, fetch=LAZY, mappedBy="prima")
	private List<FriendRequest> prijateljstva = new ArrayList<FriendRequest>();
	
	
	@OneToMany(cascade={ALL}, fetch=LAZY, mappedBy="korisnik")
	private List<Poziv> poziviZaRezervacije = new ArrayList<Poziv>();

	
	
	
	public Korisnik() {
		super();
	}


	//GET & SET:

	public Long getId() {
		return id;
	}


	public void setId(Long id) {
		this.id = id;
	}


	public String getEmail() {
		return email;
	}


	public void setEmail(String email) {
		this.email = email;
	}


	public String getLozinka() {
		return lozinka;
	}


	public void setLozinka(String lozinka) {
		this.lozinka = lozinka;
	}


	public String getIme() {
		return ime;
	}


	public void setIme(String ime) {
		this.ime = ime;
	}


	public String getPrezime() {
		return prezime;
	}


	public void setPrezime(String prezime) {
		this.prezime = prezime;
	}


	public String getGrad() {
		return grad;
	}


	public void setGrad(String grad) {
		this.grad = grad;
	}


	public String getTelefon() {
		return telefon;
	}


	public void setTelefon(String telefon) {
		this.telefon = telefon;
	}


	public int getBonus_poeni() {
		return bonus_poeni;
	}


	public void setBonus_poeni(int bonus_poeni) {
		this.bonus_poeni = bonus_poeni;
	}


	public List<FriendRequest> getPrijateljstva() {
		return prijateljstva;
	}


	public void setPrijateljstva(List<FriendRequest> prijateljstva) {
		this.prijateljstva = prijateljstva;
	}


	public List<Poziv> getPoziviZaRezervacije() {
		return poziviZaRezervacije;
	}


	public void setPoziviZaRezervacije(List<Poziv> poziviZaRezervacije) {
		this.poziviZaRezervacije = poziviZaRezervacije;
	}
	
	@Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Korisnik k = (Korisnik) o;
        if(k.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, k.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

}
