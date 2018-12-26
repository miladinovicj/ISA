package com.example.ISA_AMA_projekat.model;

import static javax.persistence.CascadeType.ALL;
import static javax.persistence.FetchType.LAZY;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Objects;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import org.joda.time.DateTime;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(uniqueConstraints={@UniqueConstraint(columnNames={"email"})})
public class Korisnik implements Serializable, UserDetails 
{

	private static final long serialVersionUID = 7284235902041908178L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(unique = true, nullable = false)
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
	
	@Column(nullable = true)
	private int bonuspoeni;
	
	@Column(nullable = true)
	private boolean aktiviran;
	
	
	@Column(name = "last_password_reset_date")
	private Timestamp lastPasswordResetDate;
	
	@ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinTable(name = "user_authority",
            joinColumns = @JoinColumn(name = "user_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "authority_id", referencedColumnName = "id"))
    private List<Authority> authorities;
	
	//SLOZENI ATRIBUTI:

	@OneToMany(cascade={ALL}, fetch=LAZY, mappedBy="prima")
	private List<FriendRequest> prijateljstva = new ArrayList<FriendRequest>();
	
	
	@OneToMany(cascade={ALL}, fetch=LAZY, mappedBy="korisnik")
	private List<Poziv> pozivi = new ArrayList<Poziv>();

	
	
	
	public Korisnik() {
		super();
		this.prijateljstva=new ArrayList<FriendRequest>();
		this.pozivi=new ArrayList<Poziv>();
		this.authorities = new ArrayList<Authority>();
		this.bonuspoeni=0;
	}
	
	public Korisnik(String email, String lozinka, String ime, String prezime, String grad, String telefon)
	{
		this.email=email;
		this.lozinka=lozinka;
		this.ime=ime;
		this.prezime=prezime;
		this.grad=grad;
		this.telefon=telefon;
		this.prijateljstva=new ArrayList<FriendRequest>();
		this.pozivi=new ArrayList<Poziv>();
		this.authorities = new ArrayList<Authority>();
		this.bonuspoeni=0;
	}
	
	public Korisnik(String email, String lozinka)
	{
		this.email=email;
		this.lozinka=lozinka;
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
		Timestamp now = new Timestamp(DateTime.now().getMillis());
	    this.setLastPasswordResetDate( now );
		this.lozinka = lozinka;
	}
	
	 public Timestamp getLastPasswordResetDate() {
	        return lastPasswordResetDate;
	    }

	    public void setLastPasswordResetDate(Timestamp lastPasswordResetDate) {
	        this.lastPasswordResetDate = lastPasswordResetDate;
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
		return bonuspoeni;
	}


	public void setBonus_poeni(int bonus_poeni) {
		this.bonuspoeni = bonus_poeni;
	}


	public List<FriendRequest> getPrijateljstva() {
		return prijateljstva;
	}


	public void setPrijateljstva(List<FriendRequest> prijateljstva) {
		this.prijateljstva = prijateljstva;
	}


	public List<Poziv> getPozivi() {
		return pozivi;
	}


	public void setPozivi(List<Poziv> poziviZaRezervacije) {
		this.pozivi = poziviZaRezervacije;
	}
	
	 public void setAuthorities(List<Authority> authorities) {
	        this.authorities = authorities;
	    }

	    @Override
	    public Collection<? extends GrantedAuthority> getAuthorities() {
	        return this.authorities;
	    }
	    
	    @JsonIgnore
	    @Override
	    public boolean isAccountNonExpired() {
	        return true;
	    }

	    @JsonIgnore
	    @Override
	    public boolean isAccountNonLocked() {
	        return true;
	    }

	    @JsonIgnore
	    @Override
	    public boolean isCredentialsNonExpired() {
	        return true;
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

	public int getBonuspoeni() {
		return bonuspoeni;
	}

	public void setBonuspoeni(int bonuspoeni) {
		this.bonuspoeni = bonuspoeni;
	}

	public boolean getAktiviran() {
		return aktiviran;
	}

	public void setAktiviran(boolean aktiviran) {
		this.aktiviran = aktiviran;
	}

	@Override
	public String getPassword() {
		// TODO Auto-generated method stub
		return lozinka;
	}

	@Override
	public String getUsername() {
		// TODO Auto-generated method stub
		return email;
	}

	@Override
	public boolean isEnabled() {
		// TODO Auto-generated method stub
		return aktiviran;
	}

    
}
